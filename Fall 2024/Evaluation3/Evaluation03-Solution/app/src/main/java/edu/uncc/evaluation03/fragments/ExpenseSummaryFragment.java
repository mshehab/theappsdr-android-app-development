package edu.uncc.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.evaluation03.R;
import edu.uncc.evaluation03.databinding.FragmentAddExpenseBinding;
import edu.uncc.evaluation03.databinding.FragmentExpenseSummaryBinding;
import edu.uncc.evaluation03.models.Expense;

public class ExpenseSummaryFragment extends Fragment {
    private static final String ARG_PARAM_EXPENSE = "ARG_PARAM_EXPENSE";
    private static final String ARG_PARAM_CAT_TOTAL = "ARG_PARAM_CAT_TOTAL";

    private Expense mExpense;
    private double mCategorySum;

    public ExpenseSummaryFragment() {
        // Required empty public constructor
    }

    public static ExpenseSummaryFragment newInstance(Expense expense, double categorySum) {
        ExpenseSummaryFragment fragment = new ExpenseSummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_EXPENSE, expense);
        args.putDouble(ARG_PARAM_CAT_TOTAL, categorySum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mExpense = (Expense) getArguments().getSerializable(ARG_PARAM_EXPENSE);
            mCategorySum = getArguments().getDouble(ARG_PARAM_CAT_TOTAL);
        }
    }

    FragmentExpenseSummaryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExpenseSummaryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewNameAndAmount.setText(mExpense.getName() + " (" + String.format("$%.2f", mExpense.getAmount()) + ")");
        binding.textViewCategoryAndTotal.setText(mExpense.getCategory() + " (Total " + String.format("$%.2f", mCategorySum) + ")");
        binding.textViewPriorityName.setText(mExpense.getPriority().getName());
        binding.textViewPriorityDescription.setText(mExpense.getPriority().getDescription());

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelExpenseSummary();
            }
        });
    }

    ExpenseSummaryListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ExpenseSummaryListener) {
            mListener = (ExpenseSummaryListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ExpenseSummaryListener");
        }
    }

    public interface ExpenseSummaryListener{
        void onCancelExpenseSummary();
    }

}