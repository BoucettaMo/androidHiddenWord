package com.boucetta.hiddenword;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String[] words;
    String hiddenWord;


    int score;
    String submittedLetter;
    int lengthHiddenWord;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
  //  Button button10;
    Button newGame;
    TextView textViewScore;
    EditText editText;
    TextView warning;
    ArrayList<Button> listButton;
    ProgressBar progressBar;
    boolean flag = false;

    int secondRemaining=120;
    CountDownTimer timer = new CountDownTimer(120000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            secondRemaining--;
            textViewScore.setText(Integer.toString(secondRemaining)+"sc");
            progressBar.setProgress(120-secondRemaining);
        }

        @Override
        public void onFinish() {
            warning.setText("The hidden world is: "+hiddenWord);
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setMessage("Sorry, times is over. The hidden word is: "+hiddenWord);
           // alert.setTitle(R.string.congratulation+hiddenWord);
            alert.setIcon(androidx.constraintlayout.widget.R.drawable.notification_icon_background);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i<=8;i++) {
                        listButton.get(i).setText("");
                        listButton.get(i).setVisibility(View.VISIBLE);
                    }
                }
            });
            alert.create().show();


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    progressBar.setProgress(0);
                    secondRemaining = 120;
                }
            }, 4000);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  words = getResources().getStringArray(R.array.words);
        progressBar = findViewById(R.id.progressBar);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
      //  button10 = findViewById(R.id.button10);
        newGame = findViewById(R.id.newgame);
        textViewScore = findViewById(R.id.textViewScore);
        editText = findViewById(R.id.edittext);
        warning = findViewById(R.id.warning);
        listButton = new ArrayList<>(Arrays.asList(button1, button2,
                button3, button4, button5, button6, button7, button8, button9));


    }

    public void start(View v) {

        warning.setText("The hidden word contains " + hiddenWord.length() + " letters");
        if (hiddenWord.length() < 9) {
            for (int i = hiddenWord.length() + 1; i <= 9; i++) {
                listButton.get(i-1).setVisibility(View.INVISIBLE);
            }
        }
        timer.start();

    }

    public void setNewGame(View v) {

        if (flag == false) {



            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setMessage("Please choose a language");
            alert.setTitle("Warning");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.create().show();
        }


      else {
            score = 0;
            secondRemaining = 120;
            progressBar.setProgress(0);

            for (int i = 0; i <= 8; i++) {
                listButton.get(i).setVisibility(View.VISIBLE);
            }

            hiddenWord = words[new Random().nextInt(words.length)];

            textViewScore.setText("0");

            for (Button item : listButton) {
                item.setText("");
            }

            editText.setText("");
            warning.setText(" ");

        }


    }

    public void submitLetter(View v) {
        boolean flag = false;
        String foundWord = "";
        submittedLetter = editText.getText().toString();
        lengthHiddenWord = hiddenWord.length();
        for (int i = 0; i < lengthHiddenWord; ++i) {
            if (String.valueOf(hiddenWord.charAt(i)).equals(submittedLetter)) {
                listButton.get(i).setText(submittedLetter);

                flag = true;
            }


        }
        if (!flag) {
            score++;
        }

      //  textViewScore.setText(String.valueOf(score));


        for (int i = 0; i < hiddenWord.length(); ++i) {
            foundWord += listButton.get(i).getText().toString();
        }

        if (hiddenWord.equals(foundWord)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setMessage(R.string.success);
            alert.setTitle("Congratulation");
            alert.setIcon(androidx.constraintlayout.widget.R.drawable.notification_icon_background);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    timer.cancel();
                }
            });
            alert.create().show();
            timer.cancel();
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu,menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item ) {

        int id = item.getItemId();

        if (id == R.id.french) {
            words = getResources().getStringArray(R.array.mots);
            flag = true;
        }
        if (id == R.id.english) {
            words = getResources().getStringArray(R.array.words);
            flag = true;
        }


        return true;
    }



    }