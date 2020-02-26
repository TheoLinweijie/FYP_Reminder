package com.example.fyp_reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddInspirationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button Save;
    private Button Cancel;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String currentUserID;
    private EditText editTitle, editDescription, editTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inspiration);

        toolbar = (Toolbar) findViewById(R.id.add_inspiration_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add New Inspiration");

        editTitle = (EditText) findViewById(R.id.add_inspiration_title);
        editDescription = (EditText) findViewById(R.id.add_inspiration_description);

        Save = (Button) findViewById(R.id.save_change);
        Cancel = (Button) findViewById(R.id.cancel);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        currentUserID = mAuth.getCurrentUser().getUid();


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SaveChange();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                GoToMainActivity();
            }
        });
    }
    private void SaveChange() {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "The title is empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "The title is empty", Toast.LENGTH_SHORT).show();
        } else
            {
            String ReminderRef = "InspirationEvents/" + currentUserID;

            DatabaseReference PlanEventKeyRef = myRef.child("InspirationEvents").child(currentUserID).push();

            String EventsPushID = PlanEventKeyRef.getKey();

            Map EventText = new HashMap();
            EventText.put("title", title);
            EventText.put("description", description);
            EventText.put("id", EventsPushID);

            Map EventContent = new HashMap();
            EventContent.put(ReminderRef + "/" + EventsPushID, EventText);

            myRef.updateChildren(EventContent).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddInspirationActivity.this, "Save Successful", Toast.LENGTH_SHORT).show();
                        GoToMainActivity();
                    } else {
                        Toast.makeText(AddInspirationActivity.this, "Save Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void GoToMainActivity()
    {
        Intent mainIntent = new Intent(AddInspirationActivity.this,MainActivity.class);
        startActivity(mainIntent);
    }
}
