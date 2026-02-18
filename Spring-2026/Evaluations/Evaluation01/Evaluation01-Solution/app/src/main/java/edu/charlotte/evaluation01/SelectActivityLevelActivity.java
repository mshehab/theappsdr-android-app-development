package edu.charlotte.evaluation01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.charlotte.evaluation01.databinding.ActivitySelectLevelBinding;

public class SelectActivityLevelActivity extends AppCompatActivity {
    ActivitySelectLevelBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySelectLevelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.buttonSedentary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("activityLevel", "Sedentary");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        binding.buttonLightlyActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("activityLevel", "Lightly active");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        binding.buttonModeratelyActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("activityLevel", "Moderately active");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        binding.buttonVeryActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("activityLevel", "Very active");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        binding.buttonSuperActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("activityLevel", "Super active");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}