package edu.uncc.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.evaluation03.R;
import edu.uncc.evaluation03.databinding.FragmentSelectPriorityBinding;
import edu.uncc.evaluation03.databinding.ListItemExpenseBinding;
import edu.uncc.evaluation03.databinding.ListItemPriorityBinding;
import edu.uncc.evaluation03.models.Data;
import edu.uncc.evaluation03.models.Priority;


public class SelectPriorityFragment extends Fragment {
    public SelectPriorityFragment() {
        // Required empty public constructor
    }
    ArrayList<Priority> mPriorities = Data.getPriorities();

    FragmentSelectPriorityBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectPriorityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelection();
            }
        });

        PriorityAdapter adapter = new PriorityAdapter(getContext(), R.layout.list_item_priority, mPriorities);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onPrioritySelected(mPriorities.get(position));
            }
        });
    }

    class PriorityAdapter extends ArrayAdapter<Priority>{
        public PriorityAdapter(@NonNull Context context, int resource, @NonNull List<Priority> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ListItemPriorityBinding itemBinding;
            if(convertView == null) {
                itemBinding = ListItemPriorityBinding.inflate(getLayoutInflater(), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            }
            itemBinding = (ListItemPriorityBinding) convertView.getTag();

            Priority priority = getItem(position);
            itemBinding.textViewPriorityName.setText(priority.getName());
            itemBinding.textViewPriorityDescription.setText(priority.getDescription());
            return convertView;
        }
    }

    SelectPriorityListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectPriorityListener) {
            mListener = (SelectPriorityListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectPriorityListener");
        }
    }

    public interface SelectPriorityListener {
        void onPrioritySelected(Priority priority);
        void onCancelSelection();
    }
}