package com.example.MilanJwellers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MilanJwellers.R;

public class LandingPageActivity extends AppCompatActivity {

    Button landingImg,btn_exit;
    TextView txt_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        View view = getSupportActionBar().getCustomView();
        ImageButton action_bar_back = view.findViewById(R.id.action_bar_back);
        ImageButton action_bar_forward = view.findViewById(R.id.action_bar_forward);
        action_bar_forward.setVisibility(View.GONE);
        action_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



//        landingImg = (ImageButton)findViewById(R.id.landingImage);
        txt_userName = (TextView)findViewById(R.id.txt_userName);
        btn_exit = (Button)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                System.exit(0);
            }
        });
        Intent intent = getIntent();
        String userName = intent.getStringExtra("welcomeString");
        txt_userName.setText(userName);
    }
}