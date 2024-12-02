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

public class PinCodeSetupFragment extends Fragment {
    public PinCodeSetupFragment() {
        // Required empty public constructor
    }

    FragmentPinCodeSetupBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPinCodeSetupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pin Code Setup");
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinCode = binding.editTextPinCode.getText().toString();
                if(pinCode.isEmpty()){
                    Toast.makeText(getActivity(), "Enter pin code", Toast.LENGTH_SHORT).show();
                } else {
                    mListener.onPinCodeSetup(pinCode);
                }
            }
        });
    }

    PinCodeSetupListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (PinCodeSetupListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement PinCodeSetupListener");
        }
    }

    public interface PinCodeSetupListener {
        void onPinCodeSetup(String pinCode);
    }
}