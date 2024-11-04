package edu.uncc.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.evaluation03.R;
import edu.uncc.evaluation03.databinding.FragmentBudgetListBinding;
import edu.uncc.evaluation03.models.Expense;

public class BudgetListFragment extends Fragment {
    public BudgetListFragment() {
        // Required empty public constructor
    }

    FragmentBudgetListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBudgetListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddExpense();
            }
        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAllExpenses();
            }
        });

        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    BudgetListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BudgetListListener) {
            mListener = (BudgetListListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BudgetListListener");
        }
    }

    public interface BudgetListListener {
        void gotoAddExpense();
        void deleteExpense(Expense expense);
        void clearAllExpenses();
        ArrayList<Expense> getAllExpenses();
        void gotoExpenseSummary(Expense expense);
    }
}