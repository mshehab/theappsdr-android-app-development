package com.example.matchtiles;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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
                setupNewGame();
            }
        });
        setupNewGame();
    }

    private void setupNewGame(){
        ArrayList<Integer> shuffledDrawableIds = new ArrayList<>();
        for (int drawableId: drawableCardIds) {
            shuffledDrawableIds.add(drawableId);
            shuffledDrawableIds.add(drawableId);
        }
        Collections.shuffle(shuffledDrawableIds);

        for (int i = 0; i < shuffledDrawableIds.size(); i++) {
            int imageViewId = imageViewCardIds[i];
            int drawableId = shuffledDrawableIds.get(i);
            CardInfo cardInfo = new CardInfo(imageViewId, drawableId);
            ImageView imageView = findViewById(imageViewId);
            imageView.setImageResource(R.drawable.covered_tile);
            imageView.setTag(cardInfo);
            imageView.setOnClickListener(this);
        }

        cardInfo1 = null;
        matchCount = 0;
        isWaiting = false;
        textViewGameStatus.setText("Match Count : " + matchCount);
    }

    CardInfo cardInfo1 = null;
    int matchCount = 0;
    boolean isWaiting = false;

    private void coverCard(CardInfo cardInfo){
        ImageView imageView = findViewById(cardInfo.getImageViewId());
        imageView.setImageResource(R.drawable.covered_tile);
        cardInfo.setFlipped(false);
        cardInfo.setMatched(false);
    }

    @Override
    public void onClick(View v) {

        if(!isWaiting){
            ImageView imageView = (ImageView) v;
            CardInfo cardInfo = (CardInfo) imageView.getTag();
            Log.d("demo", "onClick: " + cardInfo);

            if(!cardInfo.isFlipped() && !cardInfo.isMatched()){
                imageView.setImageResource(cardInfo.getDrawableId());
                cardInfo.setFlipped(true);

                if(cardInfo1 == null){
                    cardInfo1 = cardInfo;
                } else {
                    if(cardInfo.getDrawableId() == cardInfo1.getDrawableId()){
                        //match ..
                        matchCount++;
                        cardInfo1.setMatched(true);
                        cardInfo.setMatched(true);

                        textViewGameStatus.setText("Match Count : " + matchCount);

                        if(matchCount == 8){
                            //game completed !!
                            textViewGameStatus.setText("Game Completed!!");
                        }
                        cardInfo1 = null;
                    } else {
                        isWaiting = true;
                        //not match
                        imageView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isWaiting = false;
                                coverCard(cardInfo);
                                coverCard(cardInfo1);
                                cardInfo1 = null;
                            }
                        }, 2000);


                    }
                }
            }
        }


    }
}