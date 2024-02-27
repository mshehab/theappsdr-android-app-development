package edu.uncc.assignment05.fragments;

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

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentSelectAgeBinding;


public class SelectAgeFragment extends Fragment {
    public SelectAgeFragment() {
        // Required empty public constructor
    }

    FragmentSelectAgeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectAgeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<Integer> ages = new ArrayList<>();
    ArrayAdapter<Integer> adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 18; i <=100 ; i++) {
            ages.add(i);
        }

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, ages);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onAgeSelected(adapter.getItem(position));
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectionCanceled();
            }
        });
    }


    AgeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (AgeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AgeListener");
        }
    }

    public interface AgeListener{
        void onSelectionCanceled();
        void onAgeSelected(Integer age);
    }
}