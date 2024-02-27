package edu.uncc.assignment05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import edu.uncc.assignment05.fragments.AddUserFragment;
import edu.uncc.assignment05.fragments.SelectAgeFragment;
import edu.uncc.assignment05.fragments.SelectGenderFragment;
import edu.uncc.assignment05.fragments.SelectGroupFragment;
import edu.uncc.assignment05.fragments.SelectSortFragment;
import edu.uncc.assignment05.fragments.SelectStateFragment;
import edu.uncc.assignment05.fragments.UserDetailsFragment;
import edu.uncc.assignment05.fragments.UsersFragment;
import edu.uncc.assignment05.models.Data;
import edu.uncc.assignment05.models.User;

public class MainActivity extends AppCompatActivity implements UsersFragment.UsersListener, AddUserFragment.AddUserListener,
        SelectGenderFragment.GenderListener, SelectAgeFragment.AgeListener, SelectGroupFragment.GroupListener,
        SelectStateFragment.StateListener, UserDetailsFragment.UserDetailsListener, SelectSortFragment.SortListener {

    private ArrayList<User> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsers.addAll(Data.sampleTestUsers);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new UsersFragment(), "UsersFragment")
                .commit();
    }

    @Override
    public void gotoAddUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddUserFragment(), "AddUserFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return mUsers;
    }

    @Override
    public void clearAllUsers() {
        mUsers.clear();
    }

    @Override
    public void gotoSelectSort() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectSortFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoUserDetails(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UserDetailsFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectGender() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectGenderFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectAge() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectAgeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectState() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectStateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectGroup() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectGroupFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void doneAddUser(User user) {
        mUsers.add(user);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSortUsers(String sortBy, String sortOrder) {
        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("UsersFragment");
        if (fragment != null) {
            fragment.setSort(sortBy, sortOrder);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onSelectionCanceled() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onStateSelected(String state) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("AddUserFragment");
        if (fragment != null) {
            fragment.setState(state);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onGroupSelected(String group) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("AddUserFragment");
        if (fragment != null) {
            fragment.setGroup(group);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onAgeSelected(Integer age) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("AddUserFragment");
        if (fragment != null) {
            fragment.setAge(age);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onGenderSelected(String gender) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("AddUserFragment");
        if (fragment != null) {
            fragment.setGender(gender);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void deleteUser(User user) {
        mUsers.remove(user);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelUserDetails() {
        getSupportFragmentManager().popBackStack();
    }
}