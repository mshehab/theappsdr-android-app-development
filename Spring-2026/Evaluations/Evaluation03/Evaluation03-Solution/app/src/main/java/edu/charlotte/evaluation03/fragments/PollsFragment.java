package edu.charlotte.evaluation03.fragments;

import android.content.Context;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.charlotte.evaluation03.R;
import edu.charlotte.evaluation03.databinding.FragmentPollsBinding;
import edu.charlotte.evaluation03.databinding.ListItemPollBinding;
import edu.charlotte.evaluation03.models.Poll;

public class PollsFragment extends Fragment {
    public PollsFragment() {
        // Required empty public constructor
    }

    FragmentPollsBinding binding;
    ListenerRegistration listenerRegistration;
    ArrayList<Poll> polls = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    PollAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPollsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new PollAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.logout();
            }
        });

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddPoll();
            }
        });

        listenerRegistration = FirebaseFirestore.getInstance().collection("polls").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("demo", "onEvent: " + error.getMessage());
                } else {
                    polls.clear();
                    for (QueryDocumentSnapshot doc : value) {
                        Poll poll = doc.toObject(Poll.class);
                        polls.add(poll);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    class PollAdapter extends RecyclerView.Adapter<PollAdapter.PollViewHolder> {
        @NonNull
        @Override
        public PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemPollBinding itemBinding = ListItemPollBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new PollViewHolder(itemBinding);
        }

        @Override
        public int getItemCount() {
            return polls.size();
        }

        @Override
        public void onBindViewHolder(@NonNull PollViewHolder holder, int position) {
            holder.bind(polls.get(position));
        }

        class PollViewHolder extends RecyclerView.ViewHolder {
            ListItemPollBinding itemBinding;
            Poll mPoll;
            public PollViewHolder(ListItemPollBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(Poll poll) {
                mPoll = poll;
                itemBinding.textViewName.setText(poll.getName());
                itemBinding.textViewOwner.setText(poll.getCreatorName());
                itemBinding.textViewSubmissions.setText(String.valueOf(poll.getCompletedBy().size()) + " Submissions");

                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mPoll.getCompletedBy().contains(mAuth.getUid())){
                            mListener.gotoAnswerPoll(mPoll);
                        }
                    }
                });

                itemBinding.imageViewStats.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoPollResults(mPoll);
                    }
                });
            }
        }
    }

    PollsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PollsListener) {
            mListener = (PollsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement PollsListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }

    public interface PollsListener {
        void logout();
        void gotoAddPoll();
        void gotoAnswerPoll(Poll poll);
        void gotoPollResults(Poll poll);
    }
}