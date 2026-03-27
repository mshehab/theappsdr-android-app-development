package edu.charlotte.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.charlotte.evaluation03.R;
import edu.charlotte.evaluation03.databinding.FragmentPollsBinding;
import edu.charlotte.evaluation03.models.Poll;

public class PollsFragment extends Fragment {
    public PollsFragment() {
        // Required empty public constructor
    }

    FragmentPollsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPollsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    public interface PollsListener {
        void logout();
        void gotoAddPoll();
        void gotoAnswerPoll(Poll poll);
        void gotoPollResults(Poll poll);
    }
}