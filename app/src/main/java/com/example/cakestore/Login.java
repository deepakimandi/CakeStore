package com.example.cakestore;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btRegister;
    private Button btLogin;
    private TextView tvLogin;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btRegister  = findViewById(R.id.btRegister);
        tvLogin     = findViewById(R.id.tvLogin);
        btLogin     = findViewById(R.id.btLogin);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        btRegister.setOnClickListener(this);
        btLogin.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v == btLogin)
        {
            String email = this.email.getText().toString().trim();
            String password = this.password.getText().toString().trim();
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(Login.this, "Fill all fields! ", Toast.LENGTH_SHORT).show();
            }
            else {
                String type = "login";
                LoginBackground loginBackground = new LoginBackground(this);
                loginBackground.execute(type, email, password);
            }
        }
        if (v == btRegister){
            Intent intent   = new Intent(Login.this, RegisterActivity.class);
            Pair[] pairs    = new Pair[1];
            pairs[0] = new Pair<View,String>(tvLogin,"tvLogin");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
            startActivity(intent, activityOptions.toBundle());
        }

    }
}