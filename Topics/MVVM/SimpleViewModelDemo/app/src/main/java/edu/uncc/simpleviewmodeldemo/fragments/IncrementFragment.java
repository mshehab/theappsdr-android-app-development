package edu.uncc.simpleviewmodeldemo.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.simpleviewmodeldemo.R;
import edu.uncc.simpleviewmodeldemo.databinding.FragmentIncrementBinding;
import edu.uncc.simpleviewmodeldemo.viewmodels.CounterViewModel;

public class IncrementFragment extends Fragment {
    public IncrementFragment() {
        // Required empty public constructor
    }

    FragmentIncrementBinding binding;
    CounterViewModel counterViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        counterViewModel = new ViewModelProvider(requireActivity()).get(CounterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentIncrementBinding.inflate(inflater, container, false);

        counterViewModel.getCountLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("demo", "onChanged: " + integer);
                binding.textViewCount.setText(String.valueOf(integer));
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Increment Fragment");

        binding.buttonGotoSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoDecrementFragment();
            }
        });

        binding.buttonIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterViewModel.incrementCounter();
            }
        });

    }

    IncrementListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IncrementListener) {
            mListener = (IncrementListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IncrementListener");
        }
    }

    public interface IncrementListener {
        void gotoDecrementFragment();
    }
}