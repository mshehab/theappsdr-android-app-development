package edu.charlotte.evaluation01;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.charlotte.evaluation01.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    edu.charlotte.evaluation01.databinding.ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(getIntent() != null){
            Intent intent = getIntent();
            Calorie calorie = (Calorie) intent.getSerializableExtra("calorie");
            if(calorie != null){
                Calorie.ActivityLevel activityLevel = calorie.calculateBMR();
                binding.textViewBMR.setText(String.format("%.2f", activityLevel.getBmr()));
                binding.textViewTDEE.setText(String.format("%.2f", activityLevel.getTdee()));

                binding.textViewGender3.setText(String.valueOf(calorie.getGender()));
                binding.textViewHeight3.setText(String.format("%.0f ft %.0f in", calorie.getHeightFt(), calorie.getHeightIn()));
                binding.textViewWeight3.setText(String.format("%.2f lbs", calorie.getWeight()));
                binding.textViewAge3.setText(String.format("%.0f years", calorie.getAge()));
                binding.textViewLevel3.setText(String.valueOf(calorie.getActivityLevel()));


            }
        }



    }
}