package com.example.orthoepy2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UserActivity extends AppCompatActivity {

    private View dictionary_item;
    private View settings_item;
    private View user_item;
    private View study_item;
    private ImageView avatar;
    private final int Pick_image = 1;
    private TextView userNameView;
    private TextView userMailView;
    private TextView oldPassView;
    private TextInputLayout newPassword;
    private Button change_password_button;
    String filename = "uname";
    String filename2 = "umail";
    String filename3 = "upass";


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        dictionary_item = findViewById(R.id.user_bar_words);
        dictionary_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserActivity.this, DictionaryActivity.class);
                startActivity(i);
            }
        });

        study_item = findViewById(R.id.user_bar_study);
        study_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserActivity.this, StudyActivity.class);
                startActivity(i);
            }
        });
        avatar = findViewById(R.id.user_avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        });
        userNameView = findViewById(R.id.nameTextView);
        userMailView = findViewById(R.id.mailTextView);
        oldPassView = findViewById(R.id.oldPassTextView);
        newPassword = findViewById(R.id.newPassTextView);
        change_password_button = findViewById(R.id.change_password_button);
        try {
            BufferedReader reading = new BufferedReader(new InputStreamReader(
                    openFileInput(filename)));
            String string;
            while ((string = reading.readLine()) != null) {
                userNameView.setText(string);
            }
            reading.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reading2 = new BufferedReader(new InputStreamReader(
                    openFileInput(filename2)));
            String string;
            while ((string = reading2.readLine()) != null) {
                userMailView.setText(string);
            }
            reading2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reading3 = new BufferedReader(new InputStreamReader(
                    openFileInput(filename3)));
            String string;
            while ((string = reading3.readLine()) != null) {
                oldPassView.setText("Mой пароль: " + string);
            }
            reading3.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_password()){
                    oldPassView.setText("Mой пароль: " + newPassword.getEditText().getText().toString());
                }
            }
        });
    }

    public boolean check_password(){
        boolean password_result = true;
        if(newPassword.getEditText().getText().toString().isEmpty()){
            newPassword.setError("Здесь ничего нет");
            password_result = false;
        }
        if(newPassword.getEditText().getText().toString().length() < 6){
            newPassword.setError("Пароль должен быть длиннее 6 символов");
            password_result = false;
        }
        if(!newPassword.getEditText().getText().toString().isEmpty()){
        }
        return password_result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        avatar.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
