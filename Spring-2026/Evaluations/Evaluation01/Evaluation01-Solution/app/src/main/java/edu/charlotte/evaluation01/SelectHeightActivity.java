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

import edu.charlotte.evaluation01.databinding.ActivitySelectHeightBinding;

public class SelectHeightActivity extends AppCompatActivity {
    ActivitySelectHeightBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySelectHeightBinding.inflate(getLayoutInflater());
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
                    Double ft = Double.parseDouble(binding.editTextFeet.getText().toString());
                    try{
                        Double inches = Double.parseDouble(binding.editTextInches.getText().toString());
                        Intent intent = new Intent();
                        intent.putExtra("height_ft", ft);
                        intent.putExtra("height_in", inches);
                        setResult(RESULT_OK, intent);
                        finish();
                    } catch (NumberFormatException e) {
                        Toast.makeText(SelectHeightActivity.this, "Please enter a valid inches number", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(SelectHeightActivity.this, "Please enter a valid ft number", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}