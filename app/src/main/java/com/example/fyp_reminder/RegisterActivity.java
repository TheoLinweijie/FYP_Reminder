package com.example.fyp_reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button RegisterNow;
    private EditText UserEmail, UserPassword;
    private TextView BackToLogin;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterNow = (Button) findViewById(R.id.RegisterNow);
        UserEmail = (EditText) findViewById(R.id.register_email);
        UserPassword = (EditText) findViewById(R.id.register_password);
        BackToLogin = (TextView) findViewById(R.id.back_to_login);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

        RegisterNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateAccount();
            }
        });
        BackToLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    private void CreateAccount()
    {
        final String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                //get current user id
                                String currentUserID = mAuth.getCurrentUser().getUid();
                                //myRef.child("Users").child(currentUserID).setValue("");


                                HashMap<String, Object> UserInfoMap = new HashMap<>();
                                UserInfoMap.put("uid", currentUserID);
                                UserInfoMap.put("email", email);

                                myRef.child("User").child(currentUserID).updateChildren(UserInfoMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(RegisterActivity.this, "Successful Register", Toast.LENGTH_SHORT).show();
                                                    Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                                                    startActivity(loginIntent);
                                                }
                                            }
                                        });




                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

}
