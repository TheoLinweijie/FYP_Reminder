package com.example.fyp_reminder;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderFragment extends Fragment {

    private View ReminderView;
    private RecyclerView ReminderRecycleList;
    private DatabaseReference ReminderRef;
    private FirebaseAuth mAuth;

    //private final List<ReminderEvent> reminderEventList = new ArrayList<>();
    private ReminderAdapter reminderAdapter;

    ArrayList<ReminderEvent> list;
    public ReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        ReminderView = inflater.inflate(R.layout.fragment_reminder, container, false);


        ReminderRecycleList = (RecyclerView) ReminderView.findViewById(R.id.reminder_recycle_list);
        ReminderRecycleList.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<ReminderEvent>();



        ReminderRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        return ReminderView;
    }

    @Override
     public void onStart()
    {
        super.onStart();
        String currentUserID = "11";
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if(mFirebaseUser != null) {
            currentUserID = mFirebaseUser.getUid(); //Do what you need to do with the id
        }

        ReminderRef.child("ReminderEvents").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    ReminderEvent reminderEventList = dataSnapshot1.getValue(ReminderEvent.class);
                    list.add(reminderEventList);
                }
                reminderAdapter = new ReminderAdapter(getContext(), list);
                ReminderRecycleList.setAdapter(reminderAdapter);
                reminderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
