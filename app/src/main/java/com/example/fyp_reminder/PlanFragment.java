package com.example.fyp_reminder;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment {


    private View PlanView;
    private RecyclerView PlanRecycleList;

    private DatabaseReference PlanRef;
    private FirebaseAuth mAuth;


    private PlanAdapter planAdapter;

    ArrayList<ReminderEvent> list;
    public PlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        PlanView = inflater.inflate(R.layout.fragment_plan, container, false);

        PlanRecycleList = (RecyclerView) PlanView.findViewById(R.id.plan_recycle_list);
        PlanRecycleList.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<ReminderEvent>();

        mAuth = FirebaseAuth.getInstance();
        PlanRef = FirebaseDatabase.getInstance().getReference();



        return  PlanView;


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

        PlanRef.child("PlanEvents").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    ReminderEvent reminderEventList = dataSnapshot1.getValue(ReminderEvent.class);
                    list.add(reminderEventList);
                }
                planAdapter = new PlanAdapter(getContext(), list);
                PlanRecycleList.setAdapter(planAdapter);
                planAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
