package edu.uncc.evaluation02.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.databinding.FragmentTaskDetailsBinding;
import edu.uncc.evaluation02.models.Task;


public class TaskDetailsFragment extends Fragment {
    private static final String ARG_PARAM_TASK = "task";
    private Task mTask;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }

    public static TaskDetailsFragment newInstance(Task task) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTask = (Task) getArguments().getSerializable(ARG_PARAM_TASK);
        }
    }

    FragmentTaskDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewName.setText(mTask.getName());
        binding.textViewCategory.setText(mTask.getCategory());
        binding.textViewPriority.setText(mTask.getPriorityStr());

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelTaskDetails();
            }
        });

        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteTask(mTask);
            }
        });

    }

    TaskDetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TaskDetailsListener) {
            mListener = (TaskDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TaskDetailsListener");
        }
    }

    public interface TaskDetailsListener {
        void deleteTask(Task task);
        void cancelTaskDetails();
    }
}