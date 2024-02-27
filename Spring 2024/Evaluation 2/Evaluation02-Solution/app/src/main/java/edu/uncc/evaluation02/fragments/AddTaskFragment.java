package edu.uncc.evaluation02.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.databinding.FragmentAddTaskBinding;
import edu.uncc.evaluation02.models.Task;

public class AddTaskFragment extends Fragment {
    public AddTaskFragment() {
        // Required empty public constructor
    }

    String category;
    public void setCategory(String category) {
        this.category = category;
    }


    FragmentAddTaskBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(category == null){
            binding.textViewCategory.setText("N/A");
        } else {
            binding.textViewCategory.setText(category);
        }

        binding.buttonSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCategory();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Enter name!", Toast.LENGTH_SHORT).show();
                } else if(category == null){
                    Toast.makeText(getActivity(), "Select category!", Toast.LENGTH_SHORT).show();
                } else {
                    int selectedId = binding.radioGroup.getCheckedRadioButtonId();
                    String priorityStr = "Very Low";
                    int priority = 1;
                    if(selectedId == R.id.radioButtonVeryHigh){
                        priority = 5;
                        priorityStr = "Very High";
                    } else if(selectedId == R.id.radioButtonHigh){
                        priority = 4;
                        priorityStr = "High";
                    } else if(selectedId == R.id.radioButtonMedium){
                        priority = 3;
                        priorityStr = "Medium";
                    } else if(selectedId == R.id.radioButtonLow){
                        priority = 2;
                        priorityStr = "Low";
                    }

                    Task task = new Task(name, category, priorityStr, priority);
                    mListener.sendNewTask(task);
                }
            }
        });
    }

    AddTaskListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (AddTaskListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddTaskListener");
        }
    }

    public interface AddTaskListener {
        void sendNewTask(Task task);
        void gotoSelectCategory();
    }
}