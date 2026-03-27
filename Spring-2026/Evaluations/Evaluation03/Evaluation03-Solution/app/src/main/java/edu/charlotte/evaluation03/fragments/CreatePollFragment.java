package edu.charlotte.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import edu.charlotte.evaluation03.R;
import edu.charlotte.evaluation03.databinding.FragmentCreatePollBinding;
import edu.charlotte.evaluation03.databinding.ListItemAnswerAddBinding;

public class CreatePollFragment extends Fragment {
    public CreatePollFragment() {
        // Required empty public constructor
    }
    ArrayList<String> answers = new ArrayList<>();
    FragmentCreatePollBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatePollBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    AnswerAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new AnswerAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonAddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = binding.editTextAnswer.getText().toString();
                if(answer.isEmpty()) {
                    Toast.makeText(getContext(), "Answer cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    answers.add(answer);
                    adapter.notifyDataSetChanged();
                    binding.editTextAnswer.setText("");
                }
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCreatePollDone();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String question = binding.editTextQuestion.getText().toString();
                if(name.isEmpty() || question.isEmpty()){
                    Toast.makeText(getContext(), "Name and Question cannot be empty", Toast.LENGTH_SHORT).show();
                } else if(answers.size() < 2) {
                    Toast.makeText(getContext(), "At least 2 answers are required", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> pollData = new HashMap<>();
                    pollData.put("name", name);
                    pollData.put("question", question);
                    pollData.put("creatorId", mAuth.getUid());
                    pollData.put("creatorName", mAuth.getCurrentUser().getDisplayName());
                    ArrayList<HashMap<String, Object>> answerList = new ArrayList<>();
                    for(String answer : answers) {
                        HashMap<String, Object> answerData = new HashMap<>();
                        answerData.put("name", answer);
                        answerData.put("votes", 0);
                        answerList.add(answerData);
                    }
                    pollData.put("answers", answerList);
                    pollData.put("completedBy", new ArrayList<String>());

                    DocumentReference newPollRef = FirebaseFirestore.getInstance().collection("polls").document();
                    pollData.put("docId", newPollRef.getId());

                    newPollRef.set(pollData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getContext(), "Poll created successfully", Toast.LENGTH_SHORT).show();
                                mListener.onCreatePollDone();
                            } else {
                                Toast.makeText(getContext(), "Failed to create poll", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }

    class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {
        @NonNull
        @Override
        public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemAnswerAddBinding itemBinding = ListItemAnswerAddBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new AnswerViewHolder(itemBinding);
        }

        @Override
        public int getItemCount() {
            return answers.size();
        }

        @Override
        public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
            holder.bind(answers.get(position));
        }

        class AnswerViewHolder extends RecyclerView.ViewHolder {
            ListItemAnswerAddBinding itemBinding;
            String mAnswer;
            public AnswerViewHolder(ListItemAnswerAddBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;

                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        answers.remove(mAnswer);
                        notifyDataSetChanged();
                    }
                });
            }

            public void bind(String answer) {
                mAnswer = answer;
                itemBinding.textViewName.setText(answer);

            }
        }
    }

    CreatePollListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CreatePollListener) {
            mListener = (CreatePollListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CreatePollListener");
        }
    }

    public interface CreatePollListener {
        void onCreatePollDone();
    }
}