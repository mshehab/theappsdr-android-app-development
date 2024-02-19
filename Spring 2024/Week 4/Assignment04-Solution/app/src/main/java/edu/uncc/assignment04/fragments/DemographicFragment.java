package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.Response;
import edu.uncc.assignment04.databinding.FragmentDemographicBinding;

public class DemographicFragment extends Fragment {
    private static final String ARG_PARAM_RESPONSE = "ARG_PARAM_RESPONSE";

    private Response mResponse;

    public DemographicFragment() {
        // Required empty public constructor
    }

    public void setMaritalStatus(String maritalStatus){
        mResponse.setMaritalStatus(maritalStatus);
    }

    public static DemographicFragment newInstance(Response response) {
        DemographicFragment fragment = new DemographicFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_RESPONSE, response);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResponse = (Response) getArguments().getSerializable(ARG_PARAM_RESPONSE);
        }
    }

    FragmentDemographicBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDemographicBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mResponse.getEducation() == null){
            binding.textViewEducation.setText("N/A");
        } else {
            binding.textViewEducation.setText(mResponse.getEducation());
        }

        if(mResponse.getMaritalStatus() == null){
            binding.textViewMaritalStatus.setText("N/A");
        } else {
            binding.textViewMaritalStatus.setText(mResponse.getMaritalStatus());
        }

        if(mResponse.getLivingStatus() == null){
            binding.textViewLivingStatus.setText("N/A");
        } else {
            binding.textViewLivingStatus.setText(mResponse.getLivingStatus());
        }

        if(mResponse.getIncome() == null){
            binding.textViewIncomeStatus.setText("N/A");
        } else {
            binding.textViewIncomeStatus.setText(mResponse.getIncome());
        }

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //need to validate that the selections have been completed !!
                mListener.gotoProfile(mResponse);



                //mListener.gotoProfile(mResponse);
            }
        });

        binding.buttonSelectEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selectEducation();
            }
        });

        binding.buttonSelectMarital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selectMaritalStatus();
            }
        });

        binding.buttonSelectLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selectLivingStatus();
            }
        });

        binding.buttonSelectIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selectIncome();
            }
        });

    }

    DemographicListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DemographicListener) {
            mListener = (DemographicListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement DemographicListener");
        }
    }

    public interface DemographicListener {
        void gotoProfile(Response response);
        void selectEducation();
        void selectMaritalStatus();
        void selectLivingStatus();
        void selectIncome();
    }


}