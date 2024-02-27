package edu.uncc.evaluation02;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

import edu.uncc.evaluation02.fragments.AddTaskFragment;
import edu.uncc.evaluation02.fragments.SelectCategoryFragment;
import edu.uncc.evaluation02.fragments.TaskDetailsFragment;
import edu.uncc.evaluation02.fragments.TasksFragment;
import edu.uncc.evaluation02.models.Data;
import edu.uncc.evaluation02.models.Task;

public class MainActivity extends AppCompatActivity implements TasksFragment.TasksListener, AddTaskFragment.AddTaskListener, SelectCategoryFragment.SelectCategoryListener,
        TaskDetailsFragment.TaskDetailsListener {

    private ArrayList<Task> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTasks.addAll(Data.sampleTestTasks); //adding for testing

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new TasksFragment())
                .commit();

    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return mTasks;
    }

    @Override
    public void gotoAddTask() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddTaskFragment(), "AddTaskFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoTaskDetails(Task task) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, TaskDetailsFragment.newInstance(task))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clearAllTasks() {
        mTasks.clear();
    }

    @Override
    public void sendNewTask(Task task) {
        mTasks.add(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectCategory() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectCategoryFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCategorySelected(String category) {
       AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("AddTaskFragment");
       if (addTaskFragment != null) {
           addTaskFragment.setCategory(category);
           getSupportFragmentManager().popBackStack();
       }
    }

    @Override
    public void selectionCancelled() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void deleteTask(Task task) {
        mTasks.remove(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelTaskDetails() {
        getSupportFragmentManager().popBackStack();
    }
}