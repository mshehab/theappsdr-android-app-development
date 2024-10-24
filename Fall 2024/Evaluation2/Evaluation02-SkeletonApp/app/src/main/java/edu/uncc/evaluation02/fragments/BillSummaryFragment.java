package edu.uncc.evaluation02.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.activities.CreateBillActivity;
import edu.uncc.evaluation02.databinding.FragmentBillSummaryBinding;
import edu.uncc.evaluation02.model.Bill;


public class BillSummaryFragment extends Fragment {
    public BillSummaryFragment() {
        // Required empty public constructor
    }

    public final static String BILL_KEY = "BILL_KEY";

    FragmentBillSummaryBinding binding;
    Bill mBill;

    ActivityResultLauncher<Intent> startCreateBillActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK) {
                mBill = (Bill) result.getData().getSerializableExtra(BILL_KEY);
                binding.textViewBillName.setText(mBill.getName());
                binding.textViewBillAmount.setText("$" + String.format("%.2f", mBill.getAmount()));
                binding.textViewDiscountPercent.setText(String.format("%.2f", mBill.getDiscount()));

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                binding.textViewBillDate.setText(sdf.format(mBill.getCreatedAt()));

                double billDiscount = mBill.getAmount() * mBill.getDiscount() / 100.00;
                double billTotal = mBill.getAmount() - billDiscount;

                binding.textViewDiscountAmount.setText("$" + String.format("%.2f", billDiscount));
                binding.textViewBillTotal.setText("$" + String.format("%.2f", billTotal));
            }
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillSummaryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateBillActivityForResult.launch(new Intent(getContext(), CreateBillActivity.class));
            }
        });
    }
}