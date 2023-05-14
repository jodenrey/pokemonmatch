package com.example.pokemonmatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;

public class Activity2x2 extends AppCompatActivity {
    TextView textView;
    ImageView imageView11, imageView12,
            imageView21, imageView22;
    Integer[] cardsArray = {11, 12, 21, 22};
    MediaPlayer correct, incorrect, base, victory;

    int image011, image012,
            image021, image022;
    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;
    int points = 0;

    int counterTentative;
    long startTime = 0;
    int timerCounter = 0;

    TextView textViewTimer, textViewTentative;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;

            seconds = seconds % 60;
            textViewTimer.setText(String.format("%02d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 30);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2x2);
        textView = (TextView) findViewById(R.id.textView);
        textViewTimer = (TextView) findViewById(R.id.textViewTimer);
        textViewTentative = (TextView) findViewById(R.id.textViewTentativi);

        imageView11 = (ImageView) findViewById(R.id.imageView11);
        imageView12 = (ImageView) findViewById(R.id.imageView12);
        imageView21 = (ImageView) findViewById(R.id.imageView21);
        imageView22 = (ImageView) findViewById(R.id.imageView22);

        imageView11.setTag("0");
        imageView12.setTag("1");
        imageView21.setTag("2");
        imageView22.setTag("3");

        correct = MediaPlayer.create(getApplicationContext(), R.raw.correct);
        incorrect = MediaPlayer.create(getApplicationContext(), R.raw.incorrect);
        base = MediaPlayer.create(getApplicationContext(), R.raw.base);
        victory = MediaPlayer.create(getApplicationContext(), R.raw.victory);
        base.setLooping(true);

        frontOfCardsResources();
        Collections.shuffle(Arrays.asList(cardsArray));
        textView.setTextColor(Color.BLACK);



        base.start();

        imageView11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(imageView11, theCard);
                if (timerCounter == 0) {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    timerCounter++;
                }
            }
        });
        imageView12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(imageView12, theCard);
                if (timerCounter == 0) {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    timerCounter++;
                }
            }
        });
        imageView21.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(imageView21, theCard);
                if (timerCounter == 0) {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    timerCounter++;
                }
            }
        });
        imageView22.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(imageView22, theCard);
                if (timerCounter == 0) {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    timerCounter++;
                }
            }
        });
    }
    protected void onPause(){
        super.onPause();
        base.stop();
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onDestroy(){
        super.onDestroy();
    }

    public void doStuff(ImageView imageView, int card) {
        if (cardsArray[card] == 11) {
            imageView.setImageResource(image011);
        } else if (cardsArray[card] == 12) {
            imageView.setImageResource(image012);
        } else if (cardsArray[card] == 21) {
            imageView.setImageResource(image021);
        } else if (cardsArray[card] == 22) {
            imageView.setImageResource(image022);
        }

        if (cardNumber == 1) {
            firstCard = cardsArray[card];
            if (firstCard > 20) {
                firstCard = firstCard - 10;
            }
            cardNumber = 2;
            clickedFirst = card;
            imageView.setEnabled(false);
        } else if (cardNumber == 2) {
            secondCard = cardsArray[card];
            if (secondCard > 20) {
                secondCard = secondCard - 10;
            }
            cardNumber = 1;
            clickedSecond = card;

            imageView11.setEnabled(false);
            imageView12.setEnabled(false);
            imageView21.setEnabled(false);
            imageView22.setEnabled(false);

            if (firstCard == secondCard) {
                //    sameClicked()
                correct.start();
            } else{
                incorrect.start();
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sameCard();
                }
            }, 1000);
        }
    }

    public void sameCard() {
        counterTentative++;
        textViewTentative.setText(counterTentative + " ");
        if (firstCard == secondCard) {
            sameClicked();
            points++;
            textView.setText("Points: " + points);
        } else {
            imageView11.setImageResource(R.drawable.xx);
            imageView12.setImageResource(R.drawable.xx);
            imageView21.setImageResource(R.drawable.xx);
            imageView22.setImageResource(R.drawable.xx);
        }
        imageView11.setEnabled(true);
        imageView12.setEnabled(true);
        imageView21.setEnabled(true);
        imageView22.setEnabled(true);

        checkEnd();
    }

    public void sameClicked(){
        if (clickedFirst == 0) {
            imageView11.setVisibility(View.INVISIBLE);
        } else if (clickedFirst == 1) {
            imageView12.setVisibility(View.INVISIBLE);
        } else if (clickedFirst == 2) {
            imageView21.setVisibility(View.INVISIBLE);
        } else if (clickedFirst == 3) {
            imageView22.setVisibility(View.INVISIBLE);
        }

        if (clickedSecond == 0) {
            imageView11.setVisibility(View.INVISIBLE);
        } else if (clickedSecond == 1) {
            imageView12.setVisibility(View.INVISIBLE);
        } else if (clickedSecond == 2) {
            imageView21.setVisibility(View.INVISIBLE);
        } else if (clickedSecond == 3) {
            imageView22.setVisibility(View.INVISIBLE);
        }
    }

    public void checkEnd() {
        if (imageView11.getVisibility() == View.INVISIBLE &&
                imageView12.getVisibility() == View.INVISIBLE &&
                imageView21.getVisibility() == View.INVISIBLE &&
                imageView22.getVisibility() == View.INVISIBLE) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Activity2x2.this);
            alertDialogBuilder
                    .setMessage("You finished the game in "+ textViewTimer.getText() + "!!!\nYou finshed by discovering the " + points + " pairs in " + counterTentative + " attempts" + "\nWhat do you want to do?")
                    .setCancelable(false)
                    .setNeutralButton("Menu", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), Activity2x2.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Next", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), Activity4x3.class);
                            startActivity(intent);
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            timerHandler.removeCallbacks(timerRunnable);
            base.stop();
            victory.start();
        }
    }

    private void frontOfCardsResources() {
        image011 = R.drawable.a1;
        image012 = R.drawable.a2;
        image021 = R.drawable.b1;
        image022 = R.drawable.b2;
    }
}
