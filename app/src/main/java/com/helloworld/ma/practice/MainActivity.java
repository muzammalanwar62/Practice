package com.helloworld.ma.practice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class MainActivity extends AppCompatActivity {
   EditText username,password;
   Button sign;
   FirebaseAuth fbauth;
   TextView signup;
   SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.username_textinput);
        password=findViewById(R.id.password_textinput);
        signup=findViewById(R.id.sign_Up_txt);
        sign=findViewById(R.id.btn_sign_in);
      prefs=PreferenceManager.getDefaultSharedPreferences(this);
        List<UsersDB> user_info=new Gson().fromJson(prefs.getString("userinfo",""),new TypeToken< List<UsersDB>>(){}.getType());
        if(user_info!=null) {
        startActivity(new Intent(MainActivity.this,Home.class));
        finish();
        }
        fbauth=FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(MainActivity.this,Sign_up.class);
             startActivity(intent);
             finish();

            }
        });
         sign.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 fbauth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {

                         if(task.isSuccessful()){

                             new Async(MainActivity.this).execute(username.getText().toString(),password.getText().toString());
                         }
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Log.e("login",e.getMessage());
                     }
                 });
             }
         });

    }
}
