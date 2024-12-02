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
import edu.uncc.evaluation04.databinding.FragmentPinCodeCheckBinding;

public class PinCodeCheckFragment extends Fragment {

    public PinCodeCheckFragment() {
        // Required empty public constructor
    }

    FragmentPinCodeCheckBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPinCodeCheckBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pin Code Check");
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinCode = binding.editTextPinCode.getText().toString();
                if (mListener.checkPinCode(pinCode)) {
                    mListener.onPinCodeCheckSuccessful();
                } else {
                    Toast.makeText(getActivity(),"In Correct Pin Code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    PinCodeCheckListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (PinCodeCheckListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement PinCodeCheckListener");
        }
    }

    public interface PinCodeCheckListener {
        boolean checkPinCode(String pinCode);
        void onPinCodeCheckSuccessful();
    }

}