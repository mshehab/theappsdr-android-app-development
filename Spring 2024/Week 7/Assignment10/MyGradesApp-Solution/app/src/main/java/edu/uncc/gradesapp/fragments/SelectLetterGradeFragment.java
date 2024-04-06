
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
import edu.uncc.gradesapp.databinding.FragmentSelectLetterGradeBinding;
import edu.uncc.gradesapp.models.LetterGrade;

public class SelectLetterGradeFragment extends Fragment {
    public SelectLetterGradeFragment() {
        // Required empty public constructor
    }

    FragmentSelectLetterGradeBinding binding;
    ArrayList<LetterGrade> letterGrades = new ArrayList<>();
    ArrayAdapter<LetterGrade> adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        letterGrades.add(new LetterGrade("A", 4.0));
        letterGrades.add(new LetterGrade("B", 3.0));
        letterGrades.add(new LetterGrade("C", 2.0));
        letterGrades.add(new LetterGrade("D", 1.0));
        letterGrades.add(new LetterGrade("F", 0.0));
        setHasOptionsMenu(true);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectLetterGradeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Letter Grade");
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, letterGrades);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LetterGrade letterGrade = letterGrades.get(position);
                mListener.onLetterGradeSelected(letterGrade);
            }
        });
    }

    LetterGradeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (LetterGradeListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement LetterGradeListener");
        }
    }

    public interface LetterGradeListener{
        void onLetterGradeSelected(LetterGrade letterGrade);
        void onSelectionCanceled();
    }
}