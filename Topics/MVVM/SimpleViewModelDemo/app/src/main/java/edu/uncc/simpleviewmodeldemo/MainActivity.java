package edu.uncc.simpleviewmodeldemo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.uncc.simpleviewmodeldemo.fragments.DecrementFragment;
import edu.uncc.simpleviewmodeldemo.fragments.IncrementFragment;

public class MainActivity extends AppCompatActivity implements IncrementFragment.IncrementListener, DecrementFragment.DecrementListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new IncrementFragment())
                .commit();
    }

    @Override
    public void gotoBackToIncrement() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoDecrementFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new DecrementFragment())
                .addToBackStack(null)
                .commit();
    }
}