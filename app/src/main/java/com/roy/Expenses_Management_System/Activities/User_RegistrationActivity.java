package com.roy.Expenses_Management_System.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roy.Expenses_Management_System.Models.RegisterUser;
import com.roy.Expenses_Management_System.R;

public class User_RegistrationActivity extends AppCompatActivity {
    private Button mSubmitBtn;
    private EditText mUserName, mMobileNum, mMailID, mConfirmPassword,mPassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__registration);

        mUserName = findViewById(R.id.user_registration_user_name);
        mMobileNum = findViewById(R.id.user_registration_mob_no);
        mPassword = findViewById(R.id.user_registration_password);
        mMailID = findViewById(R.id.user_registration_mail);
        mConfirmPassword= findViewById(R.id.user_registration_conf_password);
        mSubmitBtn = findViewById(R.id.user_registration_btn);
        mProgressDialog = new ProgressDialog(User_RegistrationActivity.this);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.show();
                final String userName = mUserName.getText().toString().trim();
                final String mobileName = mMobileNum.getText().toString().trim();
                final String emailID = mMailID.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String confirmPassword = mConfirmPassword.getText().toString().trim();

                if(!userName.isEmpty() && !mobileName.isEmpty() && !emailID.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty())
                {
                    if(password.equals(confirmPassword))
                    {
                            mAuth.createUserWithEmailAndPassword(emailID,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        RegisterUser registerUser = new RegisterUser(userName,mobileName);
                                        addUser(registerUser);
                                    }
                                }
                            });
                    }
                    else
                    {
                        showToast("Password does not match");
                        mProgressDialog.dismiss();
                    }
                }
                else
                {
                    showToast("Few data field is empty");
                    mProgressDialog.dismiss();
                }
            }
        });

    }

    private void addUser(RegisterUser registerUser) {
        mReference = mDatabase.getReference("Users");
        String key = mAuth.getCurrentUser().getUid();
        registerUser.setUser_key(key);
        DatabaseReference mRef = mReference.child(key);
        mRef.setValue(registerUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mProgressDialog.dismiss();
                Intent intent =new Intent(User_RegistrationActivity.this,Login_pageActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showToast(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}