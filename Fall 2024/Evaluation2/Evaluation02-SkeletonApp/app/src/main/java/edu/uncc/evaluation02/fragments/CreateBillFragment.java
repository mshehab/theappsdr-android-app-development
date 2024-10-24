package edu.uncc.evaluation02.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.databinding.FragmentCreateBillBinding;
import edu.uncc.evaluation02.model.Bill;


public class CreateBillFragment extends Fragment {
    public CreateBillFragment() {
        // Required empty public constructor
    }

    FragmentCreateBillBinding binding;
    double selectedDiscount = -1;
    Date selectedDate = null;

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public void setSelectedDiscount(double selectedDiscount) {
        this.selectedDiscount = selectedDiscount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateBillBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(selectedDate != null){
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            binding.textViewBillDate.setText(sdf.format(selectedDate));
        } else {
            binding.textViewBillDate.setText("N/A");
        }

        if(selectedDiscount >= 0 ){
            binding.textViewDiscountPercent.setText(String.valueOf(selectedDiscount) + "%");
        } else {
            binding.textViewDiscountPercent.setText("N/A");
        }

        binding.buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String billName = binding.editTextText.getText().toString();
                String billAmountStr = binding.editTextNumberDecimal.getText().toString();
                if(billName.isEmpty()){
                    Toast.makeText(getContext(), "Enter bill name", Toast.LENGTH_SHORT).show();
                } else {
                    try{
                        double billAmount = Double.parseDouble(billAmountStr);
                        if(selectedDiscount == -1){
                            Toast.makeText(getContext(), "Select discount", Toast.LENGTH_SHORT).show();
                        } else if(selectedDate == null){
                            Toast.makeText(getContext(), "Select bill date", Toast.LENGTH_SHORT).show();
                        } else {
                            Bill bill = new Bill(billName, selectedDate, selectedDiscount, billAmount);
                            mListener.sendSelectedBill(bill);
                        }
                    } catch (NumberFormatException e){
                        Toast.makeText(getContext(), "Enter valid bill amount", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.buttonSelectBillDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectDate();
            }
        });

        binding.buttonSelectDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectDiscount();
            }
        });

    }

    CreateBillListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (CreateBillListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CreateBillListener");
        }
    }

    public interface CreateBillListener{
        void gotoSelectDiscount();
        void gotoSelectDate();
        void sendSelectedBill(Bill bill);
        void onCancelCreateBill();
    }

}