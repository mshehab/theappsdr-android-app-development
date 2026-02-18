package edu.charlotte.evaluation01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.charlotte.evaluation01.databinding.ActivitySelectAgeBinding;

public class SelectAgeActivity extends AppCompatActivity {
    ActivitySelectAgeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySelectAgeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Double age = Double.parseDouble(binding.editTextAge.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("age", age);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (NumberFormatException e) {
                    Toast.makeText(SelectAgeActivity.this, "Please enter a valid age number", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}