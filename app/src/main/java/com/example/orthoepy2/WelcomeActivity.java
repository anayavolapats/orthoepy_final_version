package com.example.orthoepy2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    private static final String MY_SETTINGS = "my_settings";
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences sp = getSharedPreferences(MY_SETTINGS,
                Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited", false);
        if (!hasVisited) {
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.apply();
        }else{
            Intent i = new Intent(WelcomeActivity.this, SignInActivity.class);
            startActivity(i);
        }

        TextView welcome = findViewById(R.id.welcome_text);
        TextView what_app_is_it = findViewById(R.id.what_app_is_it_text);
        String text = what_app_is_it.getText().toString();
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan start_now_link = new ClickableSpan() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this, DictionaryActivity.class);
                startActivity(i);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan register_link = new ClickableSpan() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this, SignInActivity.class);
                startActivity(i);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        spannableString.setSpan(start_now_link, 59, 78, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(register_link, 97, 114, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        what_app_is_it.setText(spannableString);
        what_app_is_it.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
