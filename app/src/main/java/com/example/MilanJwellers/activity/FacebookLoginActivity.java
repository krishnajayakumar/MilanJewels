package com.example.MilanJwellers.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MilanJwellers.R;
import com.example.MilanJwellers.model.SignInModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.Arrays;

public class FacebookLoginActivity extends AppCompatActivity implements View.OnClickListener
{
    LoginButton login_button;
    CallbackManager callbackManager;
    Activity activity;
    String username,email,password;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        activity=this.activity;
        setUI();
    }

    private void setUI() {


        login_button = (LoginButton) findViewById(R.id.login_button);
        FacebookSdk.sdkInitialize(getApplicationContext());
        login_button.setOnClickListener(this);
        login_button.setReadPermissions("email", "public_profile");
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_button:
                facebookLogin();System.out.println("facebook login on success call");
        }
    }

    private void facebookLogin() {
        //Callback registration
        System.out.println("facebook login on success call");
        databaseReference = FirebaseDatabase.getInstance().getReference("Signin");
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("facebook login on success call");
                final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
                        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                        LoginManager.getInstance().logOut();
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,profile_pic");
                        username = (user.optString("name"));
                        email = (user.optString("email"));
                        password = (user.optString("password"));

                        System.out.println(username);
                        String id = databaseReference.push().getKey();

                        SignInModel artist =new SignInModel(username,email,null,null);
                        databaseReference.child(id).setValue(artist);
                        Toast.makeText(getApplicationContext(),"User credentail added",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Login Success with facebook", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), DisplayCategoryActivity.class);
                        intent.putExtra("welcomeString","Welcome "+username + "!!");
                        startActivity(intent);
                    }

                }).executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }

        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}