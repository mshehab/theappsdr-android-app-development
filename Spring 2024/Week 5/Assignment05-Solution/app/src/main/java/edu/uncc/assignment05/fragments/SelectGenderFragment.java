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

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentSelectGenderBinding;
import edu.uncc.assignment05.models.Data;

public class SelectGenderFragment extends Fragment {

    public SelectGenderFragment() {
        // Required empty public constructor
    }

    FragmentSelectGenderBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectGenderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    ArrayAdapter<String> adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, Data.genders);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onGenderSelected(adapter.getItem(position));
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectionCanceled();
            }
        });
    }

    GenderListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (GenderListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement GenderListener");
        }
    }

    public interface GenderListener{
        void onSelectionCanceled();
        void onGenderSelected(String gender);
    }
}