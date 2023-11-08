package edu.uncc.hw06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import edu.uncc.hw06.databinding.ForumRowItemBinding;
import edu.uncc.hw06.databinding.FragmentForumsBinding;
import edu.uncc.hw06.models.Comment;
import edu.uncc.hw06.models.Forum;

public class ForumsFragment extends Fragment {

    ArrayList<Forum> mForums = new ArrayList<>();
    ForumsAdapter mAdapter;

    public ForumsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    FragmentForumsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListenerRegistration regListenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Forums");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ForumsAdapter();
        binding.recyclerView.setAdapter(mAdapter);

        regListenter =  db.collection("forums")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            error.printStackTrace();
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            mForums.clear();
                            for (QueryDocumentSnapshot doc: value) {
                                Forum forum = doc.toObject(Forum.class);
                                mForums.add(forum);
                            }
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(regListenter != null){
            regListenter.remove();
            regListenter = null;
        }
    }

    class ForumsAdapter extends RecyclerView.Adapter<ForumsAdapter.ForumsViewHolder>{

        @NonNull
        @Override
        public ForumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ForumRowItemBinding itemBinding = ForumRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ForumsViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ForumsViewHolder holder, int position) {
            holder.setupUI(mForums.get(position));
        }

        @Override
        public int getItemCount() {
            return mForums.size();
        }

        private void deleteCommentFormList(ArrayList<Comment> comments, String forumId){
            if(comments.size() == 0){
                db.collection("forums").document(forumId).delete();
            } else {
                Comment comment = comments.remove(0);
                db.collection("forums").document(forumId).collection("comments").document(comment.getDocId())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    deleteCommentFormList(comments, forumId);
                                }
                            }
                        });
            }
        }

        class ForumsViewHolder extends RecyclerView.ViewHolder{
            ForumRowItemBinding itemBinding;
            Forum mForum;
            public ForumsViewHolder(ForumRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }



            public void setupUI(Forum forum){
                this.mForum = forum;
                itemBinding.textViewForumCreatedBy.setText(mForum.getOwnerName());
                itemBinding.textViewForumTitle.setText(mForum.getTitle());
                itemBinding.textViewForumText.setText(mForum.getDescription());

                if(mAuth.getCurrentUser().getUid().equals(mForum.getOwnerId())){
                    itemBinding.imageViewDelete.setVisibility(View.VISIBLE);
                    itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /*
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("status", "DELETED");
                            db.collection("forums").document(mForum.getDocId()).update(data);
                             */
                            //step 1 get all the comments
                            //step 2 delete all the comments..
                            //step 3 delete the forum ..

                            db.collection("forums").document(mForum.getDocId()).collection("comments")
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                            if(task.isSuccessful()){
                                                ArrayList<Comment> mComments = new ArrayList<>();
                                                for (QueryDocumentSnapshot doc: task.getResult()) {
                                                    mComments.add(doc.toObject(Comment.class));
                                                }
                                                deleteCommentFormList(mComments, mForum.getDocId());
                                            } else {


                                            }
                                        }
                                    });

                        }
                    });
                } else {
                    itemBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }

                if(mForum.isLiked(mAuth.getCurrentUser().getUid())){
                    itemBinding.imageViewLike.setImageResource(R.drawable.like_favorite);
                } else {
                    itemBinding.imageViewLike.setImageResource(R.drawable.like_not_favorite);
                }

                itemBinding.imageViewLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String, Object> data = new HashMap<>();
                        String uid = mAuth.getCurrentUser().getUid();

                        if(mForum.isLiked(uid)){
                            data.put("likes", FieldValue.arrayRemove(uid));
                        } else {
                            data.put("likes", FieldValue.arrayUnion(uid));
                        }
                        db.collection("forums").document(mForum.getDocId()).update(data);
                    }
                });


                int numLikes = mForum.getLikes().size();
                String num = "";
                if(numLikes == 0){
                    num = "No Likes";
                } else if (numLikes == 1){
                    num = "1 Like";
                } else {
                    num = numLikes + " Likes";
                }

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                itemBinding.textViewForumLikesDate.setText(num + " | "  + sdf.format(mForum.getCreatedAt().toDate()));


                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoForumDetails(mForum);
                    }
                });


            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_new_item){
            mListener.createNewForum();
            return true;
        } else if(item.getItemId() == R.id.logout_item){
            mListener.logout();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    ForumsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ForumsListener) context;
    }

    interface ForumsListener {
        void createNewForum();
        void logout();
        void gotoForumDetails(Forum forum);
    }
}