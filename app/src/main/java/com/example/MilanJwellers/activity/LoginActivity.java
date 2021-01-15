package com.example.MilanJwellers.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MilanJwellers.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.facebook.FacebookSdk;

import org.json.JSONObject;

import java.util.Arrays;

import com.example.MilanJwellers.model.LoginModel;
import com.example.MilanJwellers.model.SignInModel;

public class LoginActivity extends AppCompatActivity implements FacebookCallback {

    private static final String EMAIL = "email";
    Button firstFragment, secondFragment;
    public EditText edt_email, edt_psw;
    TextView txt_forgotpswd;
    DatabaseReference credentialsList;
    Activity activity;
    LoginButton login_button;
    CallbackManager callbackManager;
    String extraStr;
    public String username,password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);

        callbackManager = CallbackManager.Factory.create();

        //get the app actionbar
//        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
//        getSupportActionBar().setElevation(0);
//        View view = getSupportActionBar().getCustomView();
//        ImageButton action_bar_back = view.findViewById(R.id.action_bar_back);
//        action_bar_back.setVisibility(View.GONE);
//        ImageButton action_bar_forward = view.findViewById(R.id.action_bar_forward);
//        action_bar_forward.setVisibility(View.GONE);

        //TextView textView = view.findViewById(R.id.textView);
        // get the reference of Button's

        ImageButton btn_login = (ImageButton) findViewById(R.id.btn_login);
        ImageButton btn_create = (ImageButton) findViewById(R.id.btn_create);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_psw = (EditText) findViewById(R.id.edt_psw);
        txt_forgotpswd = (TextView) findViewById(R.id.txt_forgotpswd);

//        //facebooklogin button
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        login_button = (LoginButton) findViewById(R.id.login_button);
//        login_button.setReadPermissions(Arrays.asList(EMAIL));
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));

        // If you are using in a fragment, call loginButton.setFragment(this);



//        credentialsList= FirebaseDatabase.getInstance().getReference("Credentail");
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmail(edt_email)==false){
                    Toast t = Toast.makeText(getApplicationContext(),"Enter valid email id",Toast.LENGTH_SHORT);
                    t.show();

                }
                else {
                    passwordCheck();
                    //addCredentialData();
                }
            }
        });
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        // perform forgot password
        txt_forgotpswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),ForgotActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean passwordCheck() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            extraStr = extras.getString("Password");
            edt_psw.setText(extraStr);
            edt_psw.setHint(extraStr);
            username = edt_email.getText().toString();
            Intent intent = new Intent(getApplicationContext(), DisplayCategoryActivity.class);
            intent.putExtra("welcomeString","Welcome "+username + "!!");
            startActivity(intent);
        }
        else if (edt_psw.getText().toString().equals("user")){
//            Toast t = Toast.makeText(this, "Logged In !", Toast.LENGTH_SHORT);
//            t.show();
            username = edt_email.getText().toString();
            Intent intent = new Intent(getApplicationContext(), DisplayCategoryActivity.class);
            intent.putExtra("welcomeString","Welcome "+username + "!!");
            startActivity(intent);
        } else if(isEmpty(edt_psw)){
            Toast t = Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            Toast t = Toast.makeText(this, "Invalid password ", Toast.LENGTH_SHORT);
            t.show();
        }
        return true;
    }
    public void addCredentialData(){
        String username = edt_email.getText().toString();
        String value = edt_psw.getText().toString();
        if(!TextUtils.isEmpty(username)){
            String id = credentialsList.push().getKey();
            LoginModel artist =new LoginModel(username,id,value);
            credentialsList.child(id).setValue(artist);
            Toast.makeText(this,"User credentail added",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"You should enter user name",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }
}