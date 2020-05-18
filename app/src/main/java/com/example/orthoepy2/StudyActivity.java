package com.example.orthoepy2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class StudyActivity extends AppCompatActivity {

    private static final String TAG = "STUDYACTIVITY";
    private View main_card;
    private FloatingActionButton finish;
    private FloatingActionButton color;
    private CardView first;
    private TextView first_text;
    private CardView second;
    private TextView second_text;
    private Button what;
    private View colorize;
    private boolean is_game_started = false;
    private boolean is_color_changed = false;
    private String correct = "emphasis";
    private String wrong = "wrong";
    private String value = "value";
    private String zero_link = "link";
    private String first_link = "link";
    private String second_link = "link";
    private TextView main_card_message;
    private TextView secondary_card_message;
    private View dictionary_item;
    private View user_item;
    private CardView color_radio;
    private Button white;
    private Button pink;
    private Button blue;
    private Button yellow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        color = findViewById(R.id.fab_color);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_radio.setVisibility(View.INVISIBLE);
            }
        });
        color_radio = findViewById(R.id.color_radio);
        color_radio.setVisibility(View.INVISIBLE);
        dictionary_item = findViewById(R.id.bar_study_words);
        dictionary_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudyActivity.this, DictionaryActivity.class);
                startActivity(i);
            }
        });
        user_item = findViewById(R.id.bar_study_user);
        user_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudyActivity.this, UserActivity.class);
                startActivity(i);
            }
        });
        main_card = findViewById(R.id.edu_card);
        what = findViewById(R.id.what_word_is_it);
        what.setVisibility(View.GONE);
        first = findViewById(R.id.first_edu_button);
        first_text = findViewById(R.id.first_text);
        second = findViewById(R.id.second_edu_button);
        second_text = findViewById(R.id.second_text);
        colorize = findViewById(R.id.bar_study_colorize);
        main_card_message = findViewById(R.id.main_card_message);
        secondary_card_message = findViewById(R.id.secondary_card_message);
        finish = findViewById(R.id.fab);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudyActivity.this, DictionaryActivity.class);
                startActivity(i);
            }
        });
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation move_text_on_card = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_card_up);
                main_card_message.startAnimation(move_text_on_card);
                Animation show_url_button = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_appearance);
                what.setVisibility(View.VISIBLE);
                what.startAnimation(show_url_button);
                secondary_card_message.setVisibility(View.INVISIBLE);
                Random rand = new Random();
                int randomNum = rand.nextInt((53 - 1) + 1) + 1;
                String doc = String.valueOf(randomNum);
                DocumentReference docRef = db.collection("dictionary").document(doc);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            main_card_message.setText("Где ударение в слове " + document.getString(value) + "?");
                            first_text.setText(document.getString(correct));
                            second_text.setText(document.getString(wrong));
                            first_text.setTextColor(Color.parseColor("#212121"));
                            second_text.setTextColor(Color.parseColor("#212121"));
                            zero_link = document.getString("link");
                            what.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(zero_link));
                                    startActivity(browserIntent);
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Упс! Что-то пошло не так... Попробуйте ещё раз!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                first.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        main_card_message.setTextColor(Color.parseColor("#87FF3E"));
                        main_card_message.setText("Всё верно!");
                        main_card_message.setTextSize(70);
                        Random rand = new Random();
                        int randomNum = rand.nextInt((53 - 1) + 1) + 1;
                        String doc = String.valueOf(randomNum);
                        DocumentReference docRef = db.collection("dictionary").document(doc);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    main_card_message.setTextSize(40);
                                    main_card_message.setText("Где ударение в слове " + document.getString(value) + "?");
                                    main_card_message.setTextColor(Color.parseColor("#212121"));
                                    first_text.setText(document.getString(correct));
                                    first_text.setTextColor(Color.parseColor("#212121"));
                                    second_text.setText(document.getString(wrong));
                                    second_text.setTextColor(Color.parseColor("#212121"));
                                    first_link = document.getString("link");
                                    what.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(first_link));
                                            startActivity(browserIntent);
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "Упс! Что-то пошло не так... Попробуйте ещё раз!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                second.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        main_card_message.setTextColor(Color.parseColor("#FF5733"));
                        main_card_message.setText("Не верно!");
                        main_card_message.setTextSize(70);
                        Random rand = new Random();
                        int randomNum = rand.nextInt((53 - 1) + 1) + 1;
                        String doc = String.valueOf(randomNum);
                        DocumentReference docRef = db.collection("dictionary").document(doc);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    main_card_message.setTextSize(40);
                                    main_card_message.setText("Где ударение в слове " + document.getString(value) + "?");
                                    main_card_message.setTextColor(Color.parseColor("#212121"));
                                    first_text.setText(document.getString(correct));
                                    second_text.setText(document.getString(wrong));
                                    first_text.setTextColor(Color.parseColor("#212121"));
                                    second_text.setTextColor(Color.parseColor("#212121"));
                                    second_link = document.getString("link");
                                    what.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(second_link));
                                            startActivity(browserIntent);
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "Упс! Что-то пошло не так... Попробуйте ещё раз!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        if(is_game_started = false){
            second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(StudyActivity.this, DictionaryActivity.class);
                    startActivity(i);
                    is_game_started = false;
                }
            });
        }
        colorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_radio.setVisibility(View.VISIBLE);
                is_color_changed = true;
                white = findViewById(R.id.white);
                white.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getWindow().setBackgroundDrawableResource(R.color.PrimaryTextWhite);
                        color_radio.setVisibility(View.INVISIBLE);
                    }
                });
                pink = findViewById(R.id.pink);
                pink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getWindow().setBackgroundDrawableResource(R.color.PrimaryPink);
                        color_radio.setVisibility(View.INVISIBLE);
                    }
                });
                blue = findViewById(R.id.blue);
                blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getWindow().setBackgroundDrawableResource(R.color.PrimaryBlue);
                        color_radio.setVisibility(View.INVISIBLE);
                    }
                });
                yellow = findViewById(R.id.yellow);
                yellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getWindow().setBackgroundDrawableResource(R.color.PrimaryYellow);
                        color_radio.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });
    }




}
