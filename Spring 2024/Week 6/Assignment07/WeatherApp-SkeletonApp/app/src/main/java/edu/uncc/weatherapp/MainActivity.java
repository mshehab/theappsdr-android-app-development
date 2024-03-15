package edu.uncc.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.uncc.weatherapp.fragments.CitiesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new CitiesFragment())
                .commit();

    }
}