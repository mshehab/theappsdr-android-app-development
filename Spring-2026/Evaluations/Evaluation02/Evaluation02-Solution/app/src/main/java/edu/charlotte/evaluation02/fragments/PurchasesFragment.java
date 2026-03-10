package edu.charlotte.evaluation02.fragments;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.charlotte.evaluation02.R;
import edu.charlotte.evaluation02.databinding.FragmentPurchasesBinding;
import edu.charlotte.evaluation02.databinding.ListItemPurchaseBinding;
import edu.charlotte.evaluation02.models.Employee;
import edu.charlotte.evaluation02.models.Purchase;

public class PurchasesFragment extends Fragment {
    private static final String ARG_PARAM_EMPLOYEE = "ARG_PARAM_EMPLOYEE";
    private Employee mEmployee;
    FragmentPurchasesBinding binding;
    PurchasesAdapter adapter;
    double filterAmount = 0.0;
    ArrayList<Purchase> mFilteredPurchases = new ArrayList<>();
    public PurchasesFragment() {
        // Required empty public constructor
    }

    public static PurchasesFragment newInstance(Employee employee) {
        PurchasesFragment fragment = new PurchasesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_EMPLOYEE, employee);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmployee = (Employee) getArguments().getSerializable(ARG_PARAM_EMPLOYEE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchasesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filterPurchases(filterAmount);
        adapter = new PurchasesAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelPurchases();
            }
        });

        binding.buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPurchases(0.0);
                adapter.notifyDataSetChanged();
            }
        });
    }

    class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.PurchasesViewHolder>{

        @NonNull
        @Override
        public PurchasesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemPurchaseBinding itemBinding = ListItemPurchaseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new PurchasesViewHolder(itemBinding);
        }

        @Override
        public int getItemCount() {
            return mFilteredPurchases.size();
        }

        @Override
        public void onBindViewHolder(@NonNull PurchasesViewHolder holder, int position) {
            holder.onBind(mFilteredPurchases.get(position));
        }

        class PurchasesViewHolder extends RecyclerView.ViewHolder{
            ListItemPurchaseBinding itemBinding;
            Purchase mPurchase;

            public PurchasesViewHolder(ListItemPurchaseBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void onBind(Purchase purchase){
                this.mPurchase = purchase;
                //format the total amount as currency with $0.00

                itemBinding.textViewCost.setText("$" + String.format("%.2f", purchase.getTotalAmount()));
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                itemBinding.textViewDate.setText(dateFormat.format(purchase.getPurchaseDate()));
                itemBinding.textViewNumItems.setText(String.valueOf(purchase.getItems().size()) + " items");
                itemBinding.imageViewFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filterPurchases(mPurchase.getTotalAmount());
                        notifyDataSetChanged();
                    }
                });
                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoPurchaseDetails(mPurchase);
                    }
                });
            }
        }
    }

    private void filterPurchases(double amount){
        mFilteredPurchases.clear();
        filterAmount = amount;
        if (amount <= 0.0) {
            mFilteredPurchases.addAll(mEmployee.getPurchases());
            binding.textViewFilterSummary.setText("All Records");
            return;
        }

        for (Purchase purchase : mEmployee.getPurchases()) {
            if (purchase.getTotalAmount() >= amount) {
                mFilteredPurchases.add(purchase);
            }
        }
        binding.textViewFilterSummary.setText("$" + String.format("%.2f", amount) +   " or more");
    }


    PurchasesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PurchasesListener) {
            mListener = (PurchasesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement PurchasesListener");
        }
    }

    public interface PurchasesListener {
        void onCancelPurchases();
        void gotoPurchaseDetails(Purchase purchase);
    }

}