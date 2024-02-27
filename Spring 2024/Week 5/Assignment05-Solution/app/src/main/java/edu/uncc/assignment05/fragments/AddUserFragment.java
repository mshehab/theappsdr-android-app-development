package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentAddUserBinding;
import edu.uncc.assignment05.models.User;


public class AddUserFragment extends Fragment {
    public AddUserFragment() {
        // Required empty public constructor
    }

    FragmentAddUserBinding binding;
    User mUser = new User();

    public void setGender(String gender){
        mUser.setGender(gender);
    }

    public void setAge(Integer age){
        mUser.setAge(age.intValue());
    }

    public void setState(String state){
        mUser.setState(state);
    }

    public void setGroup(String group){
        mUser.setGroup(group);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSelectGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectGender();
            }
        });


        binding.buttonSelectAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectAge();
            }
        });

        binding.buttonSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectState();
            }
        });

        binding.buttonSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectGroup();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Enter name !!", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Enter email !!", Toast.LENGTH_SHORT).show();
                } else if(mUser.getGender() == null){
                    Toast.makeText(getActivity(), "Select gender !!", Toast.LENGTH_SHORT).show();
                }  else if(mUser.getAge() < 0){
                    Toast.makeText(getActivity(), "Select age !!", Toast.LENGTH_SHORT).show();
                } else if(mUser.getState() == null){
                    Toast.makeText(getActivity(), "Select state !!", Toast.LENGTH_SHORT).show();
                } else if(mUser.getGroup() == null){
                    Toast.makeText(getActivity(), "Select group !!", Toast.LENGTH_SHORT).show();
                } else {
                    mUser.setName(name);
                    mUser.setEmail(email);
                    mListener.doneAddUser(mUser);
                }
            }
        });

        if(mUser.getGender() != null) {
            binding.textViewGender.setText(mUser.getGender());
        } else {
            binding.textViewGender.setText("N/A");
        }

        if(mUser.getState() != null) {
            binding.textViewState.setText(mUser.getState());
        } else {
            binding.textViewState.setText("N/A");
        }

        if(mUser.getGroup() != null) {
            binding.textViewGroup.setText(mUser.getGroup());
        } else {
            binding.textViewGroup.setText("N/A");
        }

        if(mUser.getAge() > 0) {
            binding.textViewAge.setText(String.valueOf(mUser.getAge()));
        } else {
            binding.textViewAge.setText("N/A");
        }

    }

    AddUserListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddUserListener) {
            mListener = (AddUserListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddUserListener");
        }
    }

    public interface AddUserListener {
        void gotoSelectGender();
        void gotoSelectAge();
        void gotoSelectState();
        void gotoSelectGroup();
        void doneAddUser(User user);
    }
}