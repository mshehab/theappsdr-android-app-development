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
import edu.uncc.assignment05.databinding.FragmentSelectGroupBinding;
import edu.uncc.assignment05.models.Data;


public class SelectGroupFragment extends Fragment {
    public SelectGroupFragment() {
        // Required empty public constructor
    }

    FragmentSelectGroupBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectGroupBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    ArrayAdapter<String> adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, Data.groups);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onGroupSelected(adapter.getItem(position));
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectionCanceled();
            }
        });

    }
    GroupListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (GroupListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement GroupListener");
        }
    }

    public interface GroupListener{
        void onSelectionCanceled();
        void onGroupSelected(String group);
    }

}