package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentSelectMaritalStatusBinding;


public class SelectMaritalStatusFragment extends Fragment {

    public SelectMaritalStatusFragment() {
        // Required empty public constructor
    }

    FragmentSelectMaritalStatusBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectMaritalStatusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCancel.setOnClickListener(v -> {
            mListener.cancelSelection();
        });

        binding.buttonSubmit.setOnClickListener(v -> {
            RadioButton radioButton = binding.getRoot().findViewById(binding.radioGroup.getCheckedRadioButtonId());
            mListener.sendMaritalStatus(radioButton.getText().toString());
        });

    }

    MaritalStatusListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MaritalStatusListener) {
            mListener = (MaritalStatusListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MaritalStatusListener");
        }
    }

    public interface MaritalStatusListener {
        void sendMaritalStatus(String maritalStatus);
        void cancelSelection();
    }
}