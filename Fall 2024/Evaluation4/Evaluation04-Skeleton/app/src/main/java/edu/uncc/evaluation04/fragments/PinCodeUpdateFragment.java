package edu.uncc.evaluation04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.evaluation04.R;
import edu.uncc.evaluation04.databinding.FragmentPinCodeSetupBinding;
import edu.uncc.evaluation04.databinding.FragmentPinCodeUpdateBinding;

public class PinCodeUpdateFragment extends Fragment {
    public PinCodeUpdateFragment() {
        // Required empty public constructor
    }

    FragmentPinCodeUpdateBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPinCodeUpdateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pin Code Update");
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinCode = binding.editTextPinCode.getText().toString();
                if(pinCode.isEmpty()){
                    Toast.makeText(getActivity(), "Enter pin code", Toast.LENGTH_SHORT).show();
                } else {
                    mListener.onPinCodeUpdate(pinCode);
                }
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPinCodeUpdateCancel();
            }
        });
    }


    PinCodeUpdateListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (PinCodeUpdateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement PinCodeUpdateListener");
        }
    }

    public interface PinCodeUpdateListener {
        void onPinCodeUpdate(String pinCode);
        void onPinCodeUpdateCancel();
    }
}