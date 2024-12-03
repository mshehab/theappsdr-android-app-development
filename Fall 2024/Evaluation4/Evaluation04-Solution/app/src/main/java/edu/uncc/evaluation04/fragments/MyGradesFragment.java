package edu.uncc.evaluation04.fragments;

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

import java.util.ArrayList;

import edu.uncc.evaluation04.R;
import edu.uncc.evaluation04.databinding.FragmentMyGradesBinding;
import edu.uncc.evaluation04.databinding.GradeRowItemBinding;
import edu.uncc.evaluation04.models.Grade;

public class MyGradesFragment extends Fragment {
    public MyGradesFragment() {
        // Required empty public constructor
    }

    FragmentMyGradesBinding binding;
    ArrayList<Grade> mGrades = new ArrayList<>();
    GradesAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Grades");

        mGrades.clear();
        mGrades.addAll(mListener.getAllGrades());
        calculateAndDisplayGPA();
        Log.d("demo", "onViewCreated: " + mGrades);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GradesAdapter();
        binding.recyclerView.setAdapter(adapter);

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
        if(mGrades.size() == 0){
            binding.textViewGPA.setText("GPA: 4.00");
            binding.textViewHours.setText("Hours: 0.0");

        } else {
            double totalHours = 0;
            double totalWeightedPoints = 0;
            for(Grade grade: mGrades){
                totalWeightedPoints = totalWeightedPoints + (grade.numericGrade * grade.courseHours);
                totalHours = totalHours + grade.courseHours;
            }

            if(totalHours > 0){
                double gpa = totalWeightedPoints / totalHours;
                binding.textViewGPA.setText("GPA: " + String.format("%.2f", gpa));
                binding.textViewHours.setText("Hours: " + String.format("%.1f", totalHours));
            } else {
                binding.textViewGPA.setText("GPA: 4.00");
                binding.textViewHours.setText("Hours: 0.0");
            }
        }


    }

    private class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradesViewHolder>{
        @NonNull
        @Override
        public GradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GradeRowItemBinding itemBinding = GradeRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new GradesViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GradesViewHolder holder, int position) {
            holder.bind(mGrades.get(position));

        }

        @Override
        public int getItemCount() {
            return mGrades.size();
        }

        class GradesViewHolder extends RecyclerView.ViewHolder{
            GradeRowItemBinding itemBinding;
            Grade mGrade;
            public GradesViewHolder(GradeRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(Grade grade){
                mGrade = grade;
                itemBinding.textViewCourseName.setText(grade.courseName);
                itemBinding.textViewSemester.setText(grade.semesterName);
                itemBinding.textViewCourseNumber.setText(grade.courseNumber);
                itemBinding.textViewLetterGrade.setText(grade.letterGrade);
                itemBinding.textViewCreditHours.setText(String.valueOf(grade.courseHours) + " Credit Hours");
                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.deleteGrade(mGrade);
                        mGrades.clear();
                        mGrades.addAll(mListener.getAllGrades());
                        calculateAndDisplayGPA();
                        notifyDataSetChanged();
                    }
                });

            }
        }
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