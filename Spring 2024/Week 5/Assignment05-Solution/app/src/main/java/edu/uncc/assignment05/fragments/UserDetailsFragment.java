package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentUserDetailsBinding;
import edu.uncc.assignment05.models.User;

public class UserDetailsFragment extends Fragment {
    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";
    private User mUser;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    public static UserDetailsFragment newInstance(User user) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(ARG_PARAM_USER);
        }
    }

    FragmentUserDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelUserDetails();
            }
        });

        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteUser(mUser);
            }
        });

        binding.textViewName.setText(mUser.getName());
        binding.textViewAge.setText(String.valueOf(mUser.getAge()));
        binding.textViewGender.setText(mUser.getGender());
        binding.textViewGroup.setText(mUser.getGroup());
        binding.textViewState.setText(mUser.getState());

    }

    UserDetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UserDetailsListener) {
            mListener = (UserDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement UserDetailsListener");
        }
    }

    public interface UserDetailsListener {
        void deleteUser(User user);
        void cancelUserDetails();
    }
}