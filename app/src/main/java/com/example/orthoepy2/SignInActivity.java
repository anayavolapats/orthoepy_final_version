package com.example.orthoepy2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private TextInputLayout e_mail;
    private TextInputLayout name;
    private TextInputLayout password;
    String filename = "uname";
    String filename2 = "umail";
    String filename3 = "upass";
    FileOutputStream outputStream;
    FileOutputStream outputStream2;
    FileOutputStream outputStream3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final Map<String, Object> user_fb = new HashMap<>();
        e_mail = findViewById(R.id.emailTextView);
        name = findViewById(R.id.nameTextView);
        password = findViewById(R.id.passwordTextView);
        Button sign_in = findViewById(R.id.registerButton);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((check_email() == true) && (check_name() == true) && (check_password() == true)) {
                    Intent i = new Intent(SignInActivity.this, DictionaryActivity.class);
                    user_fb.put("name", name.getEditText().getText().toString());
                    user_fb.put("email", e_mail.getEditText().getText().toString());
                    user_fb.put("password", password.getEditText().getText().toString());
                    db.collection("users").add(user_fb)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("SignInActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    String userID = documentReference.getId();
                                    String string = userID;
                                    try {
                                        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                        outputStream.write(name.getEditText().getText().toString().getBytes());
                                        outputStream.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        outputStream2 = openFileOutput(filename2, Context.MODE_PRIVATE);
                                        outputStream2.write(e_mail.getEditText().getText().toString().getBytes());
                                        outputStream2.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        outputStream3 = openFileOutput(filename3, Context.MODE_PRIVATE);
                                        outputStream3.write(password.getEditText().getText().toString().getBytes());
                                        outputStream3.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("SignInActivity", "Error adding document", e);
                                }
                            });
                    startActivity(i);

                }
            }
        });
    }

    public boolean check_name(){
        boolean name_result = true;
        if(name.getEditText().getText().toString().isEmpty()){
            name.setError("Здесь ничего нет");
            name_result = false;
        }else{
            name.setError(null);
        }
        return name_result;
    }

    public boolean check_email() {
        boolean e_mail_result = true;
        if (e_mail.getEditText().getText().toString().isEmpty()) {
            e_mail.setError("Здесь ничего нет");
            e_mail_result = false;
        } else {
            if (e_mail.getEditText().getText().toString().contains("@") && e_mail.getEditText().getText().toString().contains(".")) {
            }else{
                e_mail.setError("Это не e-mail");
                e_mail_result = false;
            }
        }
        return e_mail_result;
    }

    public boolean check_password(){
        boolean password_result = true;
        if(password.getEditText().getText().toString().isEmpty()){
            password.setError("Здесь ничего нет");
            password_result = false;
        }
        if(password.getEditText().getText().toString().length() < 6){
            password.setError("Пароль должен быть длиннее 6 символов");
            password_result = false;
        }
        return password_result;
    }
}
