package com.example.cakestore;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.Activity;
import android.app.DownloadManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    private EditText name, email, phone, password, c_password, address;
    private RelativeLayout rlayout;
    private Animation animation;
    private Button btSignup;
    private static String URL_REGIST = "http://10.0.2.2/CakeStore/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        btSignup = findViewById(R.id.btSignup);
        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Register() {
//        loading.setVisibility(View.VISIBLE);
//        btn_regist.setVisibility(View.GONE);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String c_password = this.c_password.getText().toString().trim();
        final String phone = this.phone.getText().toString().trim();
        final String address = this.address.getText().toString().trim();
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || c_password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Fill all the fields! ", Toast.LENGTH_SHORT).show();
            return;
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(RegisterActivity.this, "Email invalid! ", Toast.LENGTH_SHORT).show();
            return;
        } else if (phone.length() != 10) {
            Toast.makeText(RegisterActivity.this, "Phone number invalid! ", Toast.LENGTH_SHORT).show();
            return;
        } else if (!c_password.equals(password)) {
            Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            String type = "signup";
            RegisterBackground registerBackground = new RegisterBackground(this);
            registerBackground.execute(type, name, email, password, phone, address);
        }
    }
}
