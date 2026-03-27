package edu.charlotte.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.charlotte.evaluation03.R;
import edu.charlotte.evaluation03.databinding.FragmentPollResultsBinding;
import edu.charlotte.evaluation03.databinding.ListItemAnswerStatsBinding;
import edu.charlotte.evaluation03.models.Answer;
import edu.charlotte.evaluation03.models.Poll;

public class PollResultsFragment extends Fragment {
    private static final String ARG_PARAM_POLL = "ARG_PARAM_POLL";
    private Poll mPoll;
    ArrayList<Answer> answers = new ArrayList<>();
    AnswerAdapter adapter;
    public PollResultsFragment() {
        // Required empty public constructor
    }

    public static PollResultsFragment newInstance(Poll poll) {
        PollResultsFragment fragment = new PollResultsFragment();
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

    FragmentPollResultsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPollResultsBinding.inflate(inflater, container, false);
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

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.donePollStats();
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
            itemBinding.textViewCount.setText(String.valueOf(answer.getVotes()));

            return convertView;
        }
    }

    PollStatsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof PollStatsListener) {
            mListener = (PollStatsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement PollStatsListener");
        }
    }

    public interface PollStatsListener {
        void donePollStats();
    }

}