package com.example.fyp_reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlanEventDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTitle, editDescription, editTimeline;
    private Button SaveUpdate;
    private Button Delete;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_event_details);

        toolbar = (Toolbar) findViewById(R.id.plan_details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Edit Plan");



        editTitle = (EditText) findViewById(R.id.add_plan_title);
        editDescription = (EditText) findViewById(R.id.add_plan_description);
        editTimeline = (EditText) findViewById(R.id.add_plan_timeline);

        editTitle.setText(getIntent().getStringExtra("TitleText"));
        editDescription.setText(getIntent().getStringExtra("DescriptionText"));
        editTimeline.setText(getIntent().getStringExtra("TimelineText"));



        SaveUpdate = (Button) findViewById(R.id.save_update);
        Delete = (Button) findViewById(R.id.delete);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        currentUserID = mAuth.getCurrentUser().getUid();

        final String itemID = getIntent().getStringExtra("Id");

        SaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                myRef.child("PlanEvents").child(currentUserID).child(itemID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("title").setValue(editTitle.getText().toString());
                        dataSnapshot.getRef().child("description").setValue(editDescription.getText().toString());
                        dataSnapshot.getRef().child("timeline").setValue(editTimeline.getText().toString());
                        dataSnapshot.getRef().child("id").setValue(itemID);

                        Intent mainIntent = new Intent(PlanEventDetailsActivity.this,MainActivity.class);
                        startActivity(mainIntent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                myRef.child("PlanEvents").child(currentUserID).child(itemID).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Intent mainIntent = new Intent(PlanEventDetailsActivity.this,MainActivity.class);
                                    startActivity(mainIntent);
                                }
                                else
                                {
                                    Toast.makeText(PlanEventDetailsActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
