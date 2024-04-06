
package edu.uncc.gradesapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentSelectSemesterBinding;
import edu.uncc.gradesapp.models.Semester;

public class SelectSemesterFragment extends Fragment {

    public SelectSemesterFragment() {
        // Required empty public constructor
    }

    ArrayList<Semester> semesters = new ArrayList<>();
    ArrayAdapter<Semester> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        for (int i = 0; i < 5 ; i++) {
            semesters.add(new Semester(2024 - i, 8));
            semesters.add(new Semester(2024 - i, 1));
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_cancel){
            mListener.onSelectionCanceled();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    FragmentSelectSemesterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectSemesterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Semester");
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, semesters);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Semester semester = semesters.get(position);
                mListener.onSemesterSelected(semester);
            }
        });
    }

    SemestersListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (SemestersListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement SemestersListener");
        }
    }

    public interface SemestersListener{
        void onSemesterSelected(Semester semester);
        void onSelectionCanceled();
    }
}