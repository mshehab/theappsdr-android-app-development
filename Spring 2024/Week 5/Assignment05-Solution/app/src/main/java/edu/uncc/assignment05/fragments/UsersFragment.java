package edu.uncc.assignment05.fragments;

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

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentUsersBinding;
import edu.uncc.assignment05.databinding.UserListItemBinding;
import edu.uncc.assignment05.models.User;


public class UsersFragment extends Fragment {

    public UsersFragment() {
        // Required empty public constructor
    }

    FragmentUsersBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<User> mUsers = new ArrayList<>();
    UsersAdapter adapter;

    String sortBy = SelectSortFragment.SORT_BY_NAME;
    String sortOrder = SelectSortFragment.SORT_ORDER_ASC;

    public void setSort(String sortBy, String sortOrder) {
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUsers.clear();
        mUsers.addAll(mListener.getAllUsers());

        binding.textViewSortIndicator.setText("Sort by " + sortBy + " (" + sortOrder + ")");

        if(sortBy.equals(SelectSortFragment.SORT_BY_NAME)) {

            if(sortOrder.equals(SelectSortFragment.SORT_ORDER_ASC)) {
                Collections.sort(mUsers, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return u1.getName().compareTo(u2.getName());
                    }
                });
            } else {
                Collections.sort(mUsers, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return -1 * u1.getName().compareTo(u2.getName());
                    }
                });
            }

        } else if(sortBy.equals(SelectSortFragment.SORT_BY_EMAIL)) {
            if(sortOrder.equals(SelectSortFragment.SORT_ORDER_ASC)) {
                Collections.sort(mUsers, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return u1.getEmail().compareTo(u2.getEmail());
                    }
                });
            } else {
                Collections.sort(mUsers, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return -1 * u1.getEmail().compareTo(u2.getEmail());
                    }
                });
            }
        } else if(sortBy.equals(SelectSortFragment.SORT_BY_AGE)) {
            if(sortOrder.equals(SelectSortFragment.SORT_ORDER_ASC)) {
                Collections.sort(mUsers, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return u1.getAge() - u2.getAge();
                    }
                });
            } else {
                Collections.sort(mUsers, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return -1 * (u1.getAge() - u2.getAge());
                    }
                });
            }
        }


        adapter = new UsersAdapter(getContext(), R.layout.user_list_item, mUsers);
        binding.listView.setAdapter(adapter);


        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddUser();
            }
        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAllUsers();
                mUsers.clear();
                adapter.notifyDataSetChanged();
            }
        });

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.gotoUserDetails(mUsers.get(position));
            }
        });

        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectSort();
            }
        });

    }

    class UsersAdapter extends ArrayAdapter<User>{
        public UsersAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            UserListItemBinding itemBinding;

            if(convertView == null){
                itemBinding = UserListItemBinding.inflate(LayoutInflater.from(getContext()), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            } else {
                itemBinding = (UserListItemBinding) convertView.getTag();
            }

            User user = getItem(position);
            itemBinding.textViewName.setText(user.getName());
            itemBinding.textViewAge.setText(String.valueOf(user.getAge()));
            itemBinding.textViewGender.setText(user.getGender());
            itemBinding.textViewState.setText(user.getState());
            itemBinding.textViewGroup.setText(user.getGroup());
            itemBinding.textViewEmail.setText(user.getEmail());
            return convertView;
        }
    }

    UsersListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (UsersListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UsersListener");
        }
    }

    public interface UsersListener{
        void gotoAddUser();
        ArrayList<User> getAllUsers();
        void clearAllUsers();
        void gotoSelectSort();
        void gotoUserDetails(User user);
    }
}