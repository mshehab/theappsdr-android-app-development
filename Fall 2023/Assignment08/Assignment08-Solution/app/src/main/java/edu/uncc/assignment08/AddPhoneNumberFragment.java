package edu.uncc.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment08.databinding.FragmentAddPhoneNumberBinding;

public class AddPhoneNumberFragment extends Fragment {

    public AddPhoneNumberFragment() {
        // Required empty public constructor
    }

    FragmentAddPhoneNumberBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentAddPhoneNumberBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Phone Number");
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelCreatePhoneNumber();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = binding.editTextPhone.getText().toString();

                if (number.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Phone Number!", Toast.LENGTH_SHORT).show();
                } else {
                    String type = "Cell";
                    int checkedId = binding.radioGroup.getCheckedRadioButtonId();

                    if(checkedId == R.id.radioButtonCell){
                        type = "Cell";
                    } else if(checkedId == R.id.radioButtonHome){
                        type = "Home";
                    } else if(checkedId == R.id.radioButtonWork){
                        type = "Work";
                    } else if(checkedId == R.id.radioButtonOther){
                        type = "Other";
                    }

                    PhoneNumber phoneNumber = new PhoneNumber(number, type);
                    mListener.sendCreatedPhonNumber(phoneNumber);
                }
            }
        });
    }

    AddPhoneNumberListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddPhoneNumberListener) {
            mListener = (AddPhoneNumberListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddPhoneNumberListener");
        }
    }

    interface AddPhoneNumberListener {
        void sendCreatedPhonNumber(PhoneNumber phoneNumber);
        void cancelCreatePhoneNumber();
    }
}