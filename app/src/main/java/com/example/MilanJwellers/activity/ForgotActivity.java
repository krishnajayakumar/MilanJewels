package com.example.MilanJwellers.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MilanJwellers.R;

public class ForgotActivity extends AppCompatActivity {

    EditText edt_txtPassword,edt_confirmPswd;
    Button btn_submit;
    Activity LoginActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        edt_txtPassword = (EditText)findViewById(R.id.edt_password);
        edt_confirmPswd = (EditText)findViewById(R.id.edt_confirmPswd);
        btn_submit = (Button)findViewById(R.id.btn_submit);

        //call actionbar
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        final TextView error = (TextView)findViewById(R.id.txt_error);
        edt_confirmPswd.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String strPass1 = edt_txtPassword.getText().toString();
                String strPass2 = edt_confirmPswd.getText().toString();
                if (strPass1.equals(strPass2)) {
                    error.setText(R.string.settings_pwd_equal);
                    error.setTextColor(getResources().getColor(R.color.green));



                } else {
                    error.setText(R.string.settings_pwd_notequal);
                    error.setTextColor(getResources().getColor(R.color.red));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra("Password", edt_confirmPswd.getText().toString());
                startActivity(intent);
            }
        });
    }

    }