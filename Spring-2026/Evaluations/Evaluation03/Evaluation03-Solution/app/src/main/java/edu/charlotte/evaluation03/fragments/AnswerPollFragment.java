package edu.charlotte.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.charlotte.evaluation03.R;
import edu.charlotte.evaluation03.databinding.FragmentAnswerPollBinding;
import edu.charlotte.evaluation03.databinding.ListItemAnswerStatsBinding;
import edu.charlotte.evaluation03.models.Answer;
import edu.charlotte.evaluation03.models.Poll;


public class AnswerPollFragment extends Fragment {
    private static final String ARG_PARAM_POLL = "ARG_PARAM_POLL";
    private Poll mPoll;
    ArrayList<Answer> answers = new ArrayList<>();

    public AnswerPollFragment() {
        // Required empty public constructor
    }

    public static AnswerPollFragment newInstance(Poll poll) {
        AnswerPollFragment fragment = new AnswerPollFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_POLL, poll);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPoll = (Poll) getArguments().getSerializable(ARG_PARAM_POLL);
        }
    }

    FragmentAnswerPollBinding binding;
    AnswerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAnswerPollBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        answers.clear();
        answers.addAll(mPoll.getAnswers());
        binding.textViewName.setText(mPoll.getName());
        binding.textViewQuestion.setText(mPoll.getQuestion());

        adapter = new AnswerAdapter(getContext(), R.layout.list_item_answer_stats, answers);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Answer selectedAnswer = answers.get(position);
                selectedAnswer.setVotes(selectedAnswer.getVotes() + 1);
                HashMap<String, Object> pollData = new HashMap<>();
                pollData.put("answers", mPoll.getAnswers());

                mPoll.getCompletedBy().add(FirebaseAuth.getInstance().getUid());
                pollData.put("completedBy", mPoll.getCompletedBy());

                FirebaseFirestore.getInstance().collection("polls")
                        .document(mPoll.getDocId())
                        .update(pollData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    mListener.doneAnsweringPoll();
                                } else {
                                    Toast.makeText(getContext(), "Failed to submit answer", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.doneAnsweringPoll();
            }
        });
    }

    class AnswerAdapter extends ArrayAdapter<Answer> {
        public AnswerAdapter(@NonNull Context context, int resource, @NonNull List<Answer> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ListItemAnswerStatsBinding itemBinding;
            if (convertView == null) {
                itemBinding = ListItemAnswerStatsBinding.inflate(getLayoutInflater(), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            } else {
                itemBinding = (ListItemAnswerStatsBinding) convertView.getTag();
            }

            Answer answer = getItem(position);
            itemBinding.textViewName.setText(answer.getName());
            itemBinding.textViewCount.setText("");

            return convertView;
        }
    }

    AnswerPollListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AnswerPollListener) {
            mListener = (AnswerPollListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AnswerPollListener");
        }
    }

    public interface AnswerPollListener {
        void doneAnsweringPoll();
    }
}