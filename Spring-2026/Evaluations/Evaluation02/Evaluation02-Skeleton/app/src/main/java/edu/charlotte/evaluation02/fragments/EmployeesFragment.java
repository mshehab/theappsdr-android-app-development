package edu.charlotte.evaluation02.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.charlotte.evaluation02.R;
import edu.charlotte.evaluation02.models.DataService;
import edu.charlotte.evaluation02.models.Employee;

public class EmployeesFragment extends Fragment {
    ArrayList<Employee> mEmployees = new ArrayList<>();

    public EmployeesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employees, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmployees.clear();
        mEmployees.addAll(DataService.getEmployees());
    }
}