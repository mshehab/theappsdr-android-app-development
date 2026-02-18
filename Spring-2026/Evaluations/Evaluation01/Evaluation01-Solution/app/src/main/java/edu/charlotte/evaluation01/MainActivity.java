package edu.charlotte.evaluation01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.charlotte.evaluation01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActivityResultLauncher<Intent> selectGenderLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    mGender = data.getStringExtra("gender");
                    binding.textViewGender.setText(mGender);
                } else {
                    mGender = null;
                    binding.textViewGender.setText("N/A");
                }
            } else {
                mGender = null;
                binding.textViewGender.setText("N/A");
            }
        }
    });

    ActivityResultLauncher<Intent> selectAgeLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    mAge = data.getDoubleExtra("age", 0.0);
                    binding.textViewAge.setText(String.valueOf(mAge));
                } else {
                    mAge = null;
                    binding.textViewAge.setText("N/A");
                }
            } else {
                mAge = null;
                binding.textViewAge.setText("N/A");
            }
        }
    });

    ActivityResultLauncher<Intent> selectHeightLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    mHeightFt = data.getDoubleExtra("height_ft", 0.0);
                    mHeightIn = data.getDoubleExtra("height_in", 0.0);
                    binding.textViewHeight.setText(String.valueOf(mHeightFt) + " ft " + String.valueOf(mHeightIn) + " in");
                } else {
                    mHeightFt = null;
                    mHeightIn = null;
                    binding.textViewHeight.setText("N/A");
                }
            } else {
                mHeightFt = null;
                mHeightIn = null;
                binding.textViewHeight.setText("N/A");
            }
        }
    });

    ActivityResultLauncher<Intent> selectWeightLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    mWeight = data.getDoubleExtra("weight", 0.0);
                    binding.textViewWeight.setText(String.valueOf(mWeight) + " lbs");
                } else {
                    mWeight = null;
                    binding.textViewWeight.setText("N/A");
                }
            } else {
                mWeight = null;
                binding.textViewWeight.setText("N/A");
            }
        }
    });

    ActivityResultLauncher<Intent> selectLevelLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    mActivityLevel = data.getStringExtra("activityLevel");
                    binding.textViewLevel.setText(mActivityLevel);
                } else {
                    mActivityLevel = null;
                    binding.textViewLevel.setText("N/A");
                }
            } else {
                mActivityLevel = null;
                binding.textViewLevel.setText("N/A");
            }
        }
    });

    String mGender= null;
    Double mHeightFt= null;
    Double mHeightIn= null;
    Double mWeight = null;
    Double mAge = null;
    String mActivityLevel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSelectGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGenderLauncher.launch(new Intent(MainActivity.this, SelectGenderActivity.class));
            }
        });

        binding.buttonSelectActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLevelLauncher.launch(new Intent(MainActivity.this, SelectActivityLevelActivity.class));
            }
        });

        binding.buttonSelectHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHeightLauncher.launch(new Intent(MainActivity.this, SelectHeightActivity.class));
            }
        });

        binding.buttonSelectWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectWeightLauncher.launch(new Intent(MainActivity.this, SelectWeightActivity.class));
            }
        });

        binding.buttonSelectAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAgeLauncher.launch(new Intent(MainActivity.this, SelectAgeActivity.class));
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGender != null && mHeightFt != null && mHeightIn != null && mWeight != null && mAge != null && mActivityLevel != null) {
                    Calorie calorie = new Calorie(mGender, mHeightFt, mHeightIn, mWeight, mAge, mActivityLevel);
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("calorie", calorie);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please fill out all fields before submitting.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}