
package edu.uncc.gradesapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentAddGradeBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.LetterGrade;
import edu.uncc.gradesapp.models.Semester;

public class AddGradeFragment extends Fragment {
    public AddGradeFragment() {
        // Required empty public constructor
    }

    FragmentAddGradeBinding binding;
    public LetterGrade selectedLetterGrade;
    public Semester selectedSemester;
    public Course selectedCourse;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddGradeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Grade");

        if(selectedCourse != null){
            binding.textViewCourse.setText(selectedCourse.getNumber());
        }

        if(selectedLetterGrade != null){
            binding.textViewGrade.setText(selectedLetterGrade.getLetterGrade());
        }

        if(selectedSemester != null){
            binding.textViewSemester.setText(selectedSemester.getName());
        }

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelAddGrade();
            }
        });

        binding.buttonSelectCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCourse();
            }
        });

        binding.buttonSelectLetterGrade.setOnClickListener(new View.OnClickListener() {
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

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedCourse == null){
                    binding.textViewCourse.setError("Please select a course");
                    return;
                }

                if(selectedLetterGrade == null){
                    binding.textViewGrade.setError("Please select a grade");
                    return;
                }

                if(selectedSemester == null){
                    binding.textViewSemester.setError("Please select a semester");
                    return;
                }

                DocumentReference docRef = db.collection("grades").document();

                HashMap<String, Object> data = new HashMap<>();

                data.put("letterGrade", selectedLetterGrade.getLetterGrade());
                data.put("numericGrade", selectedLetterGrade.getNumericGrade());
                data.put("semester", selectedSemester.getName());
                data.put("courseName", selectedCourse.getName());
                data.put("courseNumber", selectedCourse.getNumber());
                data.put("numberOfCredits", selectedCourse.getHours());
                data.put("uid", mAuth.getUid());
                data.put("docId", docRef.getId());

                docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mListener.completedAddGrade();
                        } else {

                        }
                    }
                });
            }
        });

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
        void cancelAddGrade();
        void completedAddGrade();
        void gotoSelectSemester();
        void gotoSelectCourse();
        void gotoSelectLetterGrade();
    }

}