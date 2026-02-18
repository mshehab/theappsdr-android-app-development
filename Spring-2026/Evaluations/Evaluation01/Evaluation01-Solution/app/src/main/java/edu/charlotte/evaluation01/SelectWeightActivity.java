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

import edu.charlotte.evaluation01.databinding.ActivitySelectWeightBinding;

public class SelectWeightActivity extends AppCompatActivity {
    ActivitySelectWeightBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySelectWeightBinding.inflate(getLayoutInflater());
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
                    Double weight = Double.parseDouble(binding.editTextWeight.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("weight", weight);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (NumberFormatException e){
                    Toast.makeText(SelectWeightActivity.this, "Enter valid weight !!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}