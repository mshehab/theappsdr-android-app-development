package edu.charlotte.evaluation02.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.charlotte.evaluation02.R;
import edu.charlotte.evaluation02.databinding.FragmentDepartmentSelectFilterBinding;
import edu.charlotte.evaluation02.databinding.ListItemDepartmentBinding;
import edu.charlotte.evaluation02.models.DataService;
import edu.charlotte.evaluation02.models.Employee;

public class DepartmentSelectFilterFragment extends Fragment {
    public DepartmentSelectFilterFragment() {
        // Required empty public constructor
    }

    FragmentDepartmentSelectFilterBinding binding;
    ArrayList<String> mDepartments = new ArrayList<>();
    DeptAdapter adapter;
    HashSet<String> selectedDepts = new HashSet<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDepartmentSelectFilterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDepartments.clear();
        mDepartments.addAll(setupDepartments());
        adapter = new DeptAdapter(getContext(), R.layout.list_item_department, mDepartments);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dept = mDepartments.get(position);
                if(selectedDepts.contains(dept)){
                    selectedDepts.remove(dept);
                } else {
                    selectedDepts.add(dept);
                }
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedDepts.size() > 0){
                    mListener.doneSelectingDepts(selectedDepts);
                } else {
                    Toast.makeText(getContext(), "Please select at least one department", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelSelectDepts();
            }
        });
    }

    private ArrayList<String> setupDepartments(){
        HashSet<String> deptSet = new HashSet<>();
        for (Employee emp: DataService.getInstance().getEmployees()) {
            deptSet.add(emp.getDepartment());
        }
        return new ArrayList<>(deptSet);
    }

    class DeptAdapter extends ArrayAdapter<String> {
        public DeptAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ListItemDepartmentBinding itemBinding;
            if (convertView == null) {
                itemBinding = ListItemDepartmentBinding.inflate(getLayoutInflater(), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            } else {
                itemBinding = (ListItemDepartmentBinding) convertView.getTag();
            }
            String dept = getItem(position);
            itemBinding.textViewMain.setText(dept);

            if(selectedDepts.contains(dept)){
                itemBinding.imageViewIcon.setImageResource(R.drawable.ic_check_fill);
            } else {
                itemBinding.imageViewIcon.setImageResource(R.drawable.ic_check_not_fill);
            }

            return convertView;
        }
    }

    DepartmentsSelectListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof DepartmentsSelectListener){
            mListener = (DepartmentsSelectListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement DepartmentsSelectListener");
        }
    }

    public interface DepartmentsSelectListener{
        void doneSelectingDepts(HashSet<String> depts);
        void cancelSelectDepts();
    }
}