package edu.uncc.evaluation02.fragments;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.databinding.FragmentTasksBinding;
import edu.uncc.evaluation02.databinding.TaskListItemBinding;
import edu.uncc.evaluation02.models.Task;

public class TasksFragment extends Fragment {
    private ArrayList<Task> mTasks = new ArrayList<>();

    public TasksFragment() {
        // Required empty public constructor
    }

    FragmentTasksBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
    TasksAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTasks.clear();
        mTasks.addAll(mListener.getAllTasks());

        binding.textViewSortIndicator.setText("Sort by Priority (DESC)");
        Collections.sort(mTasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return -1 * (o1.getPriority() - o2.getPriority());
            }
        });


        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddTask();
            }
        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTasks.clear();
                mListener.clearAllTasks();
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new TasksAdapter(getContext(), R.layout.task_list_item, mTasks);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.gotoTaskDetails(mTasks.get(position));
            }
        });




        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewSortIndicator.setText("Sort by Priority (ASC)");
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return (o1.getPriority() - o2.getPriority());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewSortIndicator.setText("Sort by Priority (DESC)");
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return -1 * (o1.getPriority() - o2.getPriority());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

    }

    class TasksAdapter extends ArrayAdapter<Task>{
        public TasksAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TaskListItemBinding itemBinding;
            if (convertView == null){
                itemBinding = TaskListItemBinding.inflate(LayoutInflater.from(getContext()), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            } else {
                itemBinding = (TaskListItemBinding) convertView.getTag();
            }

            Task task = getItem(position);
            itemBinding.textViewName.setText(task.getName());
            itemBinding.textViewCategory.setText(task.getCategory());
            itemBinding.textViewPriority.setText(task.getPriorityStr());

            return convertView;
        }
    }

    TasksListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (TasksListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TasksListener");
        }
    }

    public interface TasksListener{
        ArrayList<Task> getAllTasks();
        void gotoAddTask();
        void gotoTaskDetails(Task task);
        void clearAllTasks();
    }
}