package com.example.matchtiles;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    TextView textViewGameStatus;
    private final int[] imageViewCardIds = {R.id.imageViewCard0, R.id.imageViewCard1, R.id.imageViewCard2, R.id.imageViewCard3, R.id.imageViewCard4, R.id.imageViewCard5, R.id.imageViewCard6, R.id.imageViewCard7, R.id.imageViewCard8, R.id.imageViewCard9, R.id.imageViewCard10, R.id.imageViewCard11, R.id.imageViewCard12, R.id.imageViewCard13, R.id.imageViewCard14, R.id.imageViewCard15};
    private final int[] drawableCardIds = {R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6, R.drawable.card7, R.drawable.card8};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewGameStatus = findViewById(R.id.textViewGameStatus);

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

}