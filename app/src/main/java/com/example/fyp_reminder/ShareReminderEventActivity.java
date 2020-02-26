package com.example.fyp_reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ShareReminderEventActivity extends AppCompatActivity {

    private RecyclerView ShareEventRecyclerView;
    private Toolbar toolbar;
    private Button Cancel;
    private DatabaseReference UsersRef, myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_reminder_event);

        toolbar = (Toolbar) findViewById(R.id.share_event_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Share Reminder");

        ShareEventRecyclerView =(RecyclerView) findViewById(R.id.share_recycle_list);
        ShareEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String Title = getIntent().getExtras().get("Title").toString();
        String Descri = getIntent().getExtras().get("Descri").toString();
        String Timeline = getIntent().getExtras().get("Timeline").toString();
        String ItemID = getIntent().getExtras().get("ItemID").toString();


        Toast.makeText(this, Title + "\n" + Descri + "\n" + Timeline + "\n" + ItemID, Toast.LENGTH_SHORT).show();

        UsersRef = FirebaseDatabase.getInstance().getReference().child("User");
        myRef = FirebaseDatabase.getInstance().getReference();

        Cancel = (Button) findViewById(R.id.cancel_share);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent aaaa = new Intent(ShareReminderEventActivity.this, MainActivity.class);
                startActivity(aaaa);
            }
        });

    }

    //Use RecyclerView Show User Info
    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(UsersRef, Contacts.class)
                .build();

        FirebaseRecyclerAdapter<Contacts, ShareEventViewHolder> adapter = new FirebaseRecyclerAdapter<Contacts, ShareEventViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ShareEventViewHolder shareEventViewHolder, final int i, @NonNull Contacts contacts)
            {
                shareEventViewHolder.email.setText(contacts.getEmail());



                shareEventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        String Title = getIntent().getExtras().get("Title").toString();
                        String Descri = getIntent().getExtras().get("Descri").toString();
                        String Timeline = getIntent().getExtras().get("Timeline").toString();
                        String ItemID = getIntent().getExtras().get("ItemID").toString();

                        String click_uid =getRef(i).getKey();



                        String ReminderRef = "ReminderEvents/" + click_uid;
                        DatabaseReference ReminderEventKeyRef = myRef.child("ReminderEvents").child(click_uid).push();
                        String EventsPushID = ReminderEventKeyRef.getKey();
                        Map EventText = new HashMap();
                        EventText.put("title", Title + "(share)");
                        EventText.put("description", Descri);
                        EventText.put("timeline", Timeline);
                        EventText.put("id", EventsPushID);

                        Map EventContent =new HashMap();
                        EventContent.put(ReminderRef + "/" + EventsPushID, EventText);

                        myRef.updateChildren(EventContent).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(ShareReminderEventActivity.this, "Save Successful", Toast.LENGTH_SHORT).show();
                                    Intent aaa = new Intent(ShareReminderEventActivity.this, MainActivity.class);
                                    startActivity(aaa);

                                }
                                else
                                {
                                    Toast.makeText(ShareReminderEventActivity.this, "Save Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                });
            }

            @NonNull
            @Override
            public ShareEventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
            {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_show_user, viewGroup, false);
                ShareEventViewHolder viewHolder = new ShareEventViewHolder(view);
                return viewHolder;
            }
        };

        ShareEventRecyclerView.setAdapter(adapter);

        adapter.startListening();
    }

    //set all info save to itemView
    public static class ShareEventViewHolder extends RecyclerView.ViewHolder
    {
        TextView email, uid;


        public ShareEventViewHolder(@NonNull View itemView)
        {
            super(itemView);

            email = itemView.findViewById(R.id.user_email);
            uid = itemView.findViewById(R.id.user_uid);

        }
    }
}
