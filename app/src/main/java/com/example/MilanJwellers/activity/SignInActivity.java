package com.example.MilanJwellers.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MilanJwellers.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.MilanJwellers.model.SignInModel;

public class SignInActivity extends AppCompatActivity {


    private boolean isEmpty;
    public EditText edt_name,edt_email,edt_number,edt_pswd1,edt_pswd2;
    ImageButton btn_create;
    DatabaseReference signinCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //get custom actionbar
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        View view = getSupportActionBar().getCustomView();
        ImageButton action_bar_back = view.findViewById(R.id.action_bar_back);
        ImageButton action_bar_forward = view.findViewById(R.id.action_bar_forward);
        action_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        action_bar_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
                addCredentialData();
            }
        });

        // get the reference of Button's

         edt_name = (EditText) findViewById(R.id.edt_name);
         edt_email = (EditText) findViewById(R.id.edt_email);
         edt_number = (EditText) findViewById(R.id.edt_number);
         btn_create = (ImageButton)findViewById(R.id.btn_create);
         edt_pswd1 = (EditText)findViewById(R.id.edt_pswd1);
         edt_pswd2 = (EditText)findViewById(R.id.edt_pswd2);
        signinCredentials = FirebaseDatabase.getInstance().getReference("Signin");
        edt_name.setInputType(InputType.TYPE_CLASS_TEXT);
        edt_name.requestFocus();
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(edt_name, InputMethodManager.SHOW_FORCED);

        edt_email.setInputType(InputType.TYPE_CLASS_TEXT);
        edt_email.requestFocus();
        InputMethodManager mgr1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr1.showSoftInput(edt_email, InputMethodManager.SHOW_FORCED);

        edt_number.setInputType(InputType.TYPE_CLASS_TEXT);
        edt_number.requestFocus();
        InputMethodManager mgr2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr2.showSoftInput(edt_number, InputMethodManager.SHOW_FORCED);

        edt_pswd1.setInputType(InputType.TYPE_CLASS_TEXT);
        edt_pswd1.requestFocus();
        InputMethodManager mgr3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr3.showSoftInput(edt_pswd1, InputMethodManager.SHOW_FORCED);

        edt_pswd2.setInputType(InputType.TYPE_CLASS_TEXT);
        edt_pswd2.requestFocus();
        InputMethodManager mgr4 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr4.showSoftInput(edt_pswd2, InputMethodManager.SHOW_FORCED);
        // perform event change listener for firebase data (retrieve updated data from firebase)
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Signin");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                SignInModel newPost = dataSnapshot.getValue(SignInModel.class);
                System.out.println("Name : "+newPost.name);
                System.out.println("Email : "+newPost.email);
                System.out.println("Phone : "+newPost.number);
                System.out.println("Password : "+newPost.password);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
                addCredentialData();

             }
         });
    }

    private void checkDataEntered(){
        if (isEmpty(edt_name)) {
            edt_name.setError("Name is required!");

        }
        else if (isEmpty(edt_email)) {
            edt_email.setError("Email is required!");

        }
        else if(isEmail(edt_email)==false){
            Toast t = Toast.makeText(this,"Enter valid email id",Toast.LENGTH_SHORT);
            t.show();

        }
        else if (isEmpty(edt_number)) {
            edt_number.setError("Contact Number is required!");

        }
        else if((edt_pswd1.getText().toString().equals(edt_pswd2.getText().toString()))){
            Intent intent= new Intent(getApplicationContext(), DisplayCategoryActivity.class);
            startActivity(intent);
        }
        else {
            Toast t = Toast.makeText(this,"Both passwords should match!",Toast.LENGTH_SHORT);
            t.show();
        }

    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void addCredentialData(){
        String name = edt_name.getText().toString();
        String email = edt_email.getText().toString();
        String number = edt_number.getText().toString();
        String password = edt_pswd1.getText().toString();
        if(!TextUtils.isEmpty(name)){
            String id = signinCredentials.push().getKey();
            SignInModel data =new SignInModel(name,email,number,password);
            signinCredentials.child(id).setValue(data);
            Toast.makeText(this,"User credentail added",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"You should enter user name",Toast.LENGTH_SHORT).show();
        }
    }
}