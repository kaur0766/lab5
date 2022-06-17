package com.cst2335.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private EditText edtEmailAddress;
    private EditText edtPassword;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmailAddress = findViewById(R.id.edtEmailAdress);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences("my_pref", 0);
        edtEmailAddress.setText(sharedPreferences.getString("email", ""));
        edtPassword.setText(sharedPreferences.getString("password", ""));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoPofile = new Intent(getApplicationContext(), ProfileActivity.class);
                gotoPofile.putExtra("EMAIL", edtEmailAddress.getText().toString());
                startActivity(gotoPofile);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences.edit().putString("email", edtEmailAddress.getText().toString()).commit();
        sharedPreferences.edit().putString("password", edtPassword.getText().toString()).commit();
    }
}

