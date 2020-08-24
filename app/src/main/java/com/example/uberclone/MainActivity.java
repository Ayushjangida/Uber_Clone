package com.example.uberclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    enum  State {
        SIGNUP, LOGIN
    }
    private State state;
    private EditText edtUsername, edtPassword, edtDriverOrPassenger;
    private RadioButton radioPassenger, radioDriver;
    private Button btnSignUp, btnOneTimeLogin;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = findViewById(R.id.edt_username_main);
        edtPassword = findViewById(R.id.edt_password_main);
        edtDriverOrPassenger = findViewById(R.id.edt_driver_or_passenger_main);
        radioDriver = findViewById(R.id.radio_driver_main);
        radioPassenger = findViewById(R.id.radio_passenger_main);
        radioGroup = findViewById(R.id.radioGroup_main);
        btnSignUp = findViewById(R.id.btn_signup_main);
        btnOneTimeLogin = findViewById(R.id.btn_one_time_login_main);

        state = State.SIGNUP;

        btnSignUp.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.login_user)   {
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            finish();
//            startActivity(intent);
            if (state == State.SIGNUP)  {
                state = State.LOGIN;
                item.setTitle("Sign Up");
                btnSignUp.setText("Login In");
            }   else    {
                state = State.SIGNUP;
                item.setTitle("Login In");
                btnSignUp.setText("Sign Up");
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.btn_signup_main :
                if (state == State.SIGNUP)  {
                    if (radioPassenger.isChecked() == false && radioDriver.isChecked() == false)    {
                        Toast.makeText(this, "Please select if you are a Driver or Passenger", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        ParseUser user = new ParseUser();
                        user.setUsername(edtUsername.getText().toString());
                        user.setPassword(edtPassword.getText().toString());
                        if (radioDriver.isChecked())    user.put("as", "Driver");
                        else if (radioPassenger.isChecked()) user.put("as", "Passenger");

                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null)  {
                                    Toast.makeText(getApplicationContext(), "Signup successfull", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }   catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(state == State.LOGIN)   {
                    ParseUser.logInInBackground(edtUsername.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null)  {
                                Toast.makeText(getApplicationContext(), "logged in successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;

            case R.id.btn_one_time_login_main :
                if (edtDriverOrPassenger.getText().toString().equals("driver") || edtDriverOrPassenger.getText().toString().equals("passenger")) {
                    if (ParseUser.getCurrentUser() == null) {
                        ParseAnonymousUtils.logIn(new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null && e == null)  {
                                    Toast.makeText(getApplicationContext(), "succefully logged in", Toast.LENGTH_SHORT).show();

                                    user.put("as", edtDriverOrPassenger.getText().toString());
                                    user.saveInBackground();
                                }
                            }
                        });
                    }
                }    else    {
                    Toast.makeText(getApplicationContext(), "Please enter valid input", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}