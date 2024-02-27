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
import edu.uncc.assignment05.databinding.FragmentSelectSortBinding;


public class SelectSortFragment extends Fragment {

    public static final String SORT_BY_NAME = "Name";
    public static final String SORT_BY_AGE = "Age";
    public static final String SORT_BY_STATE = "State";
    public static final String SORT_BY_GROUP = "Group";
    public static final String SORT_BY_EMAIL = "Email";
    public static final String SORT_BY_GENDER = "Gender";

    public static final String SORT_ORDER_ASC = "ASC";
    public static final String SORT_ORDER_DESC = "DESC";

    public SelectSortFragment() {
        // Required empty public constructor
    }


    FragmentSelectSortBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectSortBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectionCanceled();
            }
        });

        binding.imageViewNameAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sendSortUsers(SORT_BY_NAME, SORT_ORDER_ASC);
            }
        });

        binding.imageViewNameDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sendSortUsers(SORT_BY_NAME, SORT_ORDER_DESC);
            }
        });

        binding.imageViewEmailAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sendSortUsers(SORT_BY_EMAIL, SORT_ORDER_ASC);
            }
        });

        binding.imageViewEmailDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sendSortUsers(SORT_BY_EMAIL, SORT_ORDER_DESC);
            }
        });

        binding.imageViewAgeAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sendSortUsers(SORT_BY_AGE, SORT_ORDER_ASC);
            }
        });

        binding.imageViewAgeDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sendSortUsers(SORT_BY_AGE, SORT_ORDER_DESC);
            }
        });

    }

    SortListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SortListener) {
            mListener = (SortListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SortListener");
        }
    }

    public interface SortListener {
        void sendSortUsers(String sortBy, String sortOrder);
        void onSelectionCanceled();
    }

}