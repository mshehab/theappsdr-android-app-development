package edu.uncc.evaluation02.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.databinding.FragmentSelectDiscountBinding;


public class SelectDiscountFragment extends Fragment {
    public SelectDiscountFragment() {
        // Required empty public constructor
    }

    FragmentSelectDiscountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectDiscountBinding.inflate(inflater, container, false);
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

        binding.seekBar.setMax(50);
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textViewSeekBarProgress.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = binding.radioGroup.getCheckedRadioButtonId();
                if(checkedId == -1){
                    Toast.makeText(getContext(), "Make a discount selection!", Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.radioButton10Percent){
                    mListener.onDiscountSelected(10);
                } else if(checkedId == R.id.radioButton15Percent){
                    mListener.onDiscountSelected(15);
                } else if(checkedId == R.id.radioButton18Percent){
                    mListener.onDiscountSelected(18);
                } else if(checkedId == R.id.radioButtonCustom) {
                    mListener.onDiscountSelected(binding.seekBar.getProgress());
                }
            }
        });
    }

    SelectDiscountListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (SelectDiscountListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SelectDiscountListener");
        }
    }

    public interface SelectDiscountListener {
        void onDiscountSelected(double discount);
        void onSelectionCanceled();
    }
}