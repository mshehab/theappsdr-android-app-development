package edu.uncc.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uncc.evaluation03.R;
import edu.uncc.evaluation03.databinding.FragmentBudgetListBinding;
import edu.uncc.evaluation03.databinding.ListItemExpenseBinding;
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

    ArrayList<Expense> mExpenses = new ArrayList<>();
    ExpensesAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExpenses.clear();
        mExpenses.addAll(mListener.getAllExpenses());

        adapter = new ExpensesAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        setupBudgetAmount();

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
                mExpenses.clear();
                adapter.notifyDataSetChanged();
                setupBudgetAmount();
            }
        });

        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mExpenses, new Comparator<Expense>() {
                    @Override
                    public int compare(Expense e1, Expense e2) {
                        if(e1.getAmount() - e2.getAmount() > 0){
                            return 1;
                        } else if(e1.getAmount() - e2.getAmount() < 0){
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mExpenses, new Comparator<Expense>() {
                    @Override
                    public int compare(Expense e1, Expense e2) {
                        if(e1.getAmount() - e2.getAmount() > 0){
                            return -1;
                        } else if(e1.getAmount() - e2.getAmount() < 0){
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupBudgetAmount(){
        double total = 0.0;
        for (Expense expense: mExpenses) {
            total = total + expense.getAmount();
        }
        binding.textViewBudgetTotal.setText("Total Budget: " + String.format("$%.2f", total));

    }

    class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpesesViewHolder> {
        @NonNull
        @Override
        public ExpesesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemExpenseBinding itemBinding = ListItemExpenseBinding.inflate(getLayoutInflater(), parent, false);
            return new ExpesesViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ExpesesViewHolder holder, int position) {
            holder.bind(mExpenses.get(position));
        }

        @Override
        public int getItemCount() {
            return mExpenses.size();
        }

        class ExpesesViewHolder extends RecyclerView.ViewHolder{
            ListItemExpenseBinding itemBinding;
            Expense mExpense;
            public ExpesesViewHolder(ListItemExpenseBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(Expense expense){
                mExpense = expense;
                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoExpenseSummary(mExpense);
                    }
                });
                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mExpenses.remove(mExpense);
                        mListener.deleteExpense(mExpense);
                        notifyDataSetChanged();
                        setupBudgetAmount();
                    }
                });
                itemBinding.textViewAmount.setText(String.format("$%.2f", expense.getAmount()));
                itemBinding.textViewName.setText(expense.getName());
                itemBinding.textViewCategory.setText(expense.getCategory());
                itemBinding.textViewPriority.setText(expense.getPriority().getName());
            }
        }
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