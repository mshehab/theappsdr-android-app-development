package edu.uncc.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.evaluation03.R;
import edu.uncc.evaluation03.databinding.FragmentSelectPriorityBinding;
import edu.uncc.evaluation03.models.Data;
import edu.uncc.evaluation03.models.Priority;


public class SelectPriorityFragment extends Fragment {
    public SelectPriorityFragment() {
        // Required empty public constructor
    }
    ArrayList<Priority> mPriorities = Data.getPriorities();

    FragmentSelectPriorityBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectPriorityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelection();
            }
        });

        //mPriorities has the list of priorities

    }

    SelectPriorityListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectPriorityListener) {
            mListener = (SelectPriorityListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectPriorityListener");
        }
    }

    public interface SelectPriorityListener {
        void onPrioritySelected(Priority priority);
        void onCancelSelection();
    }
}