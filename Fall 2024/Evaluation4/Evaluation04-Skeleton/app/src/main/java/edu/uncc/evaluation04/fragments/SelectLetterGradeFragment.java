package edu.uncc.evaluation04.fragments;

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

import java.util.ArrayList;

import edu.uncc.evaluation04.R;
import edu.uncc.evaluation04.databinding.FragmentSelectLetterGradeBinding;
import edu.uncc.evaluation04.models.DataSource;
import edu.uncc.evaluation04.models.LetterGrade;


public class SelectLetterGradeFragment extends Fragment {
    public SelectLetterGradeFragment() {
        // Required empty public constructor
    }

    FragmentSelectLetterGradeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectLetterGradeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<LetterGrade> mLetterGrades = DataSource.getLetterGrades();
    ArrayAdapter<LetterGrade> mAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Letter Grade");
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mLetterGrades);
        binding.listView.setAdapter(mAdapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LetterGrade letterGrade = mLetterGrades.get(position);
                mListener.onLetterGradeSelected(letterGrade);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelection();
            }
        });
    }

    SelectLetterGradeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectLetterGradeListener) {
            mListener = (SelectLetterGradeListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectLetterGradeListener");
        }
    }

    public interface SelectLetterGradeListener {
        void onLetterGradeSelected(LetterGrade letterGrade);
        void onCancelSelection();
    }
}