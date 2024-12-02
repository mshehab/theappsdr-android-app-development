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
import edu.uncc.evaluation04.databinding.FragmentSelectSemesterBinding;
import edu.uncc.evaluation04.models.DataSource;
import edu.uncc.evaluation04.models.Semester;

public class SelectSemesterFragment extends Fragment {
    public SelectSemesterFragment() {
        // Required empty public constructor
    }

    FragmentSelectSemesterBinding binding;
    ArrayList<Semester> mSemesters = DataSource.getSemesters();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectSemesterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    ArrayAdapter<Semester> mAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Semester");
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mSemesters);
        binding.listView.setAdapter(mAdapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Semester semester = mSemesters.get(position);
                mListener.onSemesterSelected(semester);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelection();
            }
        });
    }

    SelectSemesterListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectSemesterListener) {
            mListener = (SelectSemesterListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectSemesterListener");
        }
    }

    public interface SelectSemesterListener {
        void onSemesterSelected(Semester semester);
        void onCancelSelection();
    }
}