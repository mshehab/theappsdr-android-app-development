package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.Response;
import edu.uncc.assignment04.databinding.FragmentIdentificationBinding;


public class IdentificationFragment extends Fragment {

    public IdentificationFragment() {
        // Required empty public constructor
    }

    FragmentIdentificationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIdentificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                int checkedId = binding.radioGroup.getCheckedRadioButtonId();
                String role = "Student";
                if (checkedId == R.id.radioButtonEmployee) {
                    role = "Employee";
                } else if (checkedId == R.id.radioButtonOther) {
                    role = "Other";
                }

               if(name.isEmpty()){
                   Toast.makeText(getActivity(), "Enter name!", Toast.LENGTH_SHORT).show();
               } else if (email.isEmpty()) {
                   Toast.makeText(getActivity(), "Enter email!", Toast.LENGTH_SHORT).show();
               } else if (role.isEmpty()) {
                   Toast.makeText(getActivity(), "Select role", Toast.LENGTH_SHORT).show();
               } else {
                   Response response = new Response(name, email, role);
                   mListener.gotoDemographicFromIdentification(response);
               }
            }
        });

    }

    IndentificationListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block to check if the parent activity implements the interface
        try {
            mListener = (IndentificationListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IndentificationListener");
        }
    }

    public interface IndentificationListener {
        void gotoDemographicFromIdentification(Response response);
    }

}