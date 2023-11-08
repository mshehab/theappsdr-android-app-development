package edu.uncc.hw06;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

import edu.uncc.hw06.databinding.CommentRowItemBinding;
import edu.uncc.hw06.databinding.FragmentForumBinding;
import edu.uncc.hw06.databinding.UserRowItemBinding;
import edu.uncc.hw06.models.Comment;
import edu.uncc.hw06.models.Forum;
import edu.uncc.hw06.models.User;

public class ForumFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Comment> mComments = new ArrayList<>();
    ArrayList<User> mUsers = new ArrayList<>();
    CommentsAdapter adapter;

    private static final String ARG_PARAM_FORUM = "ARG_PARAM_FORUM";

    private Forum mForum;

    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment newInstance(Forum forum) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_FORUM, forum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mForum = (Forum) getArguments().getSerializable(ARG_PARAM_FORUM);
        }
    }

    FragmentForumBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentForumBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ListenerRegistration commentReg, forumReg;
    UsersAdapter usersAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Forum");

        binding.textViewForumCreatedBy.setText(mForum.getOwnerName());
        binding.textViewForumTitle.setText(mForum.getTitle());
        binding.textViewForumText.setText(mForum.getDescription());


        adapter = new CommentsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        commentReg = db.collection("forums").document(mForum.getDocId())
                .collection("comments")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if(error != null){
                                    error.printStackTrace();
                                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    mComments.clear();
                                    for (QueryDocumentSnapshot doc: value) {
                                        Comment comment = doc.toObject(Comment.class);
                                        mComments.add(comment);
                                    }
                                    adapter.notifyDataSetChanged();

                                    String num = "0 Comments";
                                    if(mComments.size() == 1){
                                        num = "1 Comment";
                                    } else if(mComments.size() > 1){
                                        num = mComments.size() + " Comments";
                                    }

                                    binding.textViewCommentsCount.setText(num);
                                }

                            }
                        });

        binding.buttonSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = binding.editTextComment.getText().toString();
                if(desc.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Comment !!", Toast.LENGTH_SHORT).show();
                } else {

                   DocumentReference docRef =  db.collection("forums").document(mForum.getDocId())
                           .collection("comments").document();

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("description", desc);
                    data.put("ownerId", mAuth.getCurrentUser().getUid());
                    data.put("ownerName", mAuth.getCurrentUser().getDisplayName());
                    data.put("createdAt", FieldValue.serverTimestamp());
                    data.put("docId", docRef.getId());
                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                binding.editTextComment.setText("");
                            } else {

                            }
                        }
                    });
                }


            }
        });

        User user = new User(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getUid());
        HashMap<String, Object> data = new HashMap<>();
        data.put("users", FieldValue.arrayUnion(user));
        db.collection("forums").document(mForum.getDocId()).update(data);


        usersAdapter = new UsersAdapter();
        binding.recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewUsers.setAdapter(usersAdapter);


        forumReg =  db.collection("forums").document(mForum.getDocId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){

                } else {
                    mForum = value.toObject(Forum.class);
                    mUsers.clear();
                    if(mForum.getUsers() != null){
                        mUsers.addAll(mForum.getUsers());

                    }
                    usersAdapter.notifyDataSetChanged();

                    Log.d("demo", "onEvent: ");

                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        User user = new User(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getUid());
        HashMap<String, Object> data = new HashMap<>();
        data.put("users", FieldValue.arrayRemove(user));
        db.collection("forums").document(mForum.getDocId()).update(data);

        if(commentReg != null){
            commentReg.remove();
            commentReg = null;
        }

        if(forumReg != null){
            forumReg.remove();
            forumReg = null;
        }
    }

    class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>{
        @NonNull
        @Override
        public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CommentRowItemBinding itemBinding = CommentRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new CommentsViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
            holder.setupUI(mComments.get(position));
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }

        class CommentsViewHolder extends RecyclerView.ViewHolder{
            CommentRowItemBinding itemBinding;
            Comment mComment;

            public CommentsViewHolder(CommentRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(Comment comment){
                this.mComment = comment;
                itemBinding.textViewCommentText.setText(mComment.getDescription());
                itemBinding.textViewCommentCreatedBy.setText(mComment.getOwnerName());

                if(mComment.getCreatedAt() != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    itemBinding.textViewCommentCreatedAt.setText(sdf.format(mComment.getCreatedAt().toDate()));
                } else {
                    itemBinding.textViewCommentCreatedAt.setText("N/A");

                }


                if(mAuth.getCurrentUser().getUid().equals(mComment.getOwnerId())){
                    itemBinding.imageViewDelete.setVisibility(View.VISIBLE);
                    itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db.collection("forums").document(mForum.getDocId())
                                    .collection("comments").document(mComment.getDocId()).delete();
                        }
                    });
                } else {
                    itemBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }

            }
        }
    }

    class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
        @NonNull
        @Override
        public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            UserRowItemBinding itemBinding = UserRowItemBinding.inflate(getLayoutInflater(), parent, false);

            return new UsersViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
            holder.setupUI(mUsers.get(position));
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        class UsersViewHolder extends RecyclerView.ViewHolder{
            UserRowItemBinding itemBinding;
            public UsersViewHolder(UserRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(User user){
                itemBinding.textViewUserName.setText(user.getName());
            }
        }
    }

}