package edu.charlotte.evaluation02.fragments;

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
import java.util.HashSet;
import java.util.List;

import edu.charlotte.evaluation02.R;
import edu.charlotte.evaluation02.databinding.FragmentEmployeesBinding;
import edu.charlotte.evaluation02.databinding.ListItemEmployeeBinding;
import edu.charlotte.evaluation02.models.DataService;
import edu.charlotte.evaluation02.models.Employee;

public class EmployeesFragment extends Fragment {
    ArrayList<Employee> mAllEmployees = new ArrayList<>();
    ArrayList<Employee> mFilteredEmployees = new ArrayList<>();
    EmployeesAdapter adapter;
    FragmentEmployeesBinding binding;
    HashSet<String> deptFilter = new HashSet<>();
    public EmployeesFragment() {
        // Required empty public constructor
    }

    public void setDeptFilter(HashSet<String> deptFilter) {
        this.deptFilter = deptFilter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEmployeesBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAllEmployees.clear();
        mAllEmployees.addAll(DataService.getInstance().getEmployees());

        if(deptFilter.isEmpty()){
            mFilteredEmployees.clear();
            mFilteredEmployees.addAll(mAllEmployees);
            binding.textViewFilter.setText("All Records");
        } else {
            mFilteredEmployees.clear();
            for(Employee employee : mAllEmployees){
                if(deptFilter.contains(employee.getDepartment())){
                    mFilteredEmployees.add(employee);
                }
            }
            binding.textViewFilter.setText(String.join(",", deptFilter));
        }

        adapter = new EmployeesAdapter(getContext(), R.layout.list_item_employee, mFilteredEmployees);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.gotoPurchases(mFilteredEmployees.get(position));
            }
        });

        binding.buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectDeptFilter();
            }
        });

        binding.buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deptFilter.clear();
                mFilteredEmployees.clear();
                mFilteredEmployees.addAll(mAllEmployees);
                binding.textViewFilter.setText("All Records");
                adapter.notifyDataSetChanged();
            }
        });
    }

    EmployeesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof EmployeesListener){
            mListener = (EmployeesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement EmployeesListener");
        }
    }

    public interface EmployeesListener{
        void gotoSelectDeptFilter();
        void gotoPurchases(Employee employee);
    }

    class EmployeesAdapter extends ArrayAdapter<Employee>{
        public EmployeesAdapter(@NonNull Context context, int resource, @NonNull List<Employee> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ListItemEmployeeBinding itemBinding;

            if(convertView == null){
                itemBinding = ListItemEmployeeBinding.inflate(getLayoutInflater(), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            } else {
                itemBinding = (ListItemEmployeeBinding) convertView.getTag();
            }

            Employee employee = getItem(position);

            itemBinding.textViewName.setText(employee.getName());
            itemBinding.textViewDept.setText(employee.getDepartment());
            itemBinding.textViewPurchases.setText(String.valueOf(employee.getPurchases().size()) + " Purchases");
            return convertView;
        }
    }
}