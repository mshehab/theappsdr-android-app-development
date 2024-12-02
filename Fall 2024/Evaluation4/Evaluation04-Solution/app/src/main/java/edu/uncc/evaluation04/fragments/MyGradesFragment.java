package edu.uncc.evaluation04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.evaluation04.R;
import edu.uncc.evaluation04.databinding.FragmentMyGradesBinding;
import edu.uncc.evaluation04.models.Grade;

public class MyGradesFragment extends Fragment {
    public MyGradesFragment() {
        // Required empty public constructor
    }

    FragmentMyGradesBinding binding;
    ArrayList<Grade> mGrades = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Grades");

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddGrade();
            }
        });

        binding.buttonUpdatePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoUpdatePin();
            }
        });

    }

    private void calculateAndDisplayGPA(){

    }

    MyGradesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MyGradesListener) {
            mListener = (MyGradesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MyGradesListener");
        }
    }

    public interface MyGradesListener {
        void gotoAddGrade();
        void gotoUpdatePin();
        ArrayList<Grade> getAllGrades();
        void deleteGrade(Grade grade);
    }
}