package edu.uncc.evaluation04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.evaluation04.R;
import edu.uncc.evaluation04.databinding.FragmentAddGradeBinding;
import edu.uncc.evaluation04.models.Course;
import edu.uncc.evaluation04.models.Grade;
import edu.uncc.evaluation04.models.LetterGrade;
import edu.uncc.evaluation04.models.Semester;

public class AddGradeFragment extends Fragment {
    public AddGradeFragment() {
        // Required empty public constructor
    }

    FragmentAddGradeBinding binding;
    Semester selectedSemester;
    LetterGrade selectedLetterGrade;
    Course selectedCourse;

    public void setSelectedSemester(Semester selectedSemester) {
        this.selectedSemester = selectedSemester;
    }

    public void setSelectedLetterGrade(LetterGrade selectedLetterGrade) {
        this.selectedLetterGrade = selectedLetterGrade;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddGradeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Grade");
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelAddGrade();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedSemester == null){
                    Toast.makeText(getActivity(), "Select a semester", Toast.LENGTH_SHORT).show();
                } else if(selectedCourse == null){
                    Toast.makeText(getActivity(), "Select a course", Toast.LENGTH_SHORT).show();
                } else if(selectedLetterGrade == null){
                    Toast.makeText(getActivity(), "Select a letter grade", Toast.LENGTH_SHORT).show();
                } else {
                    // ready to create a Grade object..
                    //the call mListener.onAddGrade(grade);
                }
            }
        });

        binding.buttonSelectCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCourse();
            }
        });

        binding.buttonSelectGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectLetterGrade();
            }
        });

        binding.buttonSelectSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectSemester();
            }
        });

        if(selectedCourse == null) {
            binding.textViewCourse.setText("N/A");
        } else {
            binding.textViewCourse.setText(selectedCourse.getNumber());
        }

        if(selectedSemester == null) {
            binding.textViewSemester.setText("N/A");
        } else {
            binding.textViewSemester.setText(selectedSemester.getName());
        }

        if(selectedLetterGrade == null) {
            binding.textViewGrade.setText("N/A");
        } else {
            binding.textViewGrade.setText(selectedLetterGrade.getLetterGrade());
        }
    }

    AddGradeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddGradeListener) {
            mListener = (AddGradeListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddGradeListener");
        }
    }

    public interface AddGradeListener {
        void onAddGrade(Grade grade);
        void onCancelAddGrade();
        void gotoSelectLetterGrade();
        void gotoSelectCourse();
        void gotoSelectSemester();
    }
}