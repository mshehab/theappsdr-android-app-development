package edu.charlotte.evaluation02.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import edu.charlotte.evaluation02.R;
import edu.charlotte.evaluation02.databinding.FragmentPurchaseDetailsBinding;
import edu.charlotte.evaluation02.databinding.ListItemOrderItemBinding;
import edu.charlotte.evaluation02.models.Item;
import edu.charlotte.evaluation02.models.Purchase;

public class PurchaseDetailsFragment extends Fragment {
    private static final String ARG_PARAM_PURCHASE = "ARG_PARAM_PURCHASE";
    private Purchase mPurchase;

    public PurchaseDetailsFragment() {
        // Required empty public constructor
    }

    public static PurchaseDetailsFragment newInstance(Purchase purchase) {
        PurchaseDetailsFragment fragment = new PurchaseDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_PURCHASE, purchase);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPurchase = (Purchase) getArguments().getSerializable(ARG_PARAM_PURCHASE);
        }
    }

    FragmentPurchaseDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchaseDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    OrderAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewTotal.setText(String.format("Total: $%.2f", mPurchase.getTotalAmount()));
        binding.textViewQuantity.setText(String.format("Quantity: %d items", mPurchase.getItems().size()));


        adapter = new OrderAdapter(getContext(), R.layout.list_item_order_item, mPurchase.getItems());
        binding.listView.setAdapter(adapter);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelDetails();
            }
        });
    }

    class OrderAdapter extends ArrayAdapter<Item> {
        public OrderAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ListItemOrderItemBinding itemBinding;
            if (convertView == null) {
                itemBinding = ListItemOrderItemBinding.inflate(getLayoutInflater(), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            } else {
                itemBinding = (ListItemOrderItemBinding) convertView.getTag();
            }
            Item item = getItem(position);
            itemBinding.textViewName.setText(item.getItemName());
            itemBinding.textViewEquation.setText(String.format("%d x $%.2f", item.getQuantity(), item.getPrice()));
            itemBinding.textViewTotal.setText(String.format("$%.2f", item.getQuantity() * item.getPrice()));
            return convertView;
        }
    }

    DetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DetailsListener) {
            mListener = (DetailsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement DetailsListener");
        }

    }

    public interface DetailsListener {
        void onCancelDetails();
    }

}