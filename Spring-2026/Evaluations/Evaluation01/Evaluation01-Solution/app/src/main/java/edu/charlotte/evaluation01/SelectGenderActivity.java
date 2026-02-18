package edu.charlotte.evaluation01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.charlotte.evaluation01.databinding.ActivitySelectGenderBinding;

public class SelectGenderActivity extends AppCompatActivity {

    ActivitySelectGenderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySelectGenderBinding.inflate(getLayoutInflater());
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
                int selectedId = binding.radioGroup.getCheckedRadioButtonId();
                String gender = "Female";
                if(selectedId == R.id.radioButtonMale){
                    gender = "Male";
                }
                Intent intent = new Intent();
                intent.putExtra("gender", gender);
                setResult(RESULT_OK, intent);
                finish();
            }
        });



    }
}