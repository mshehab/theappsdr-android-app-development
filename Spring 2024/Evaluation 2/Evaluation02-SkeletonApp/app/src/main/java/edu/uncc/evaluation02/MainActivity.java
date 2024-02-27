package edu.uncc.evaluation02;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

import edu.uncc.evaluation02.models.Data;
import edu.uncc.evaluation02.models.Task;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTasks.addAll(Data.sampleTestTasks); //adding for testing
    }
}