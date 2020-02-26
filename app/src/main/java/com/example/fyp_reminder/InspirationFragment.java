package com.example.fyp_reminder;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InspirationFragment extends Fragment {

    private View InspirationView;
    private RecyclerView InspirationRecycleList;

    private DatabaseReference InspirationRef;
    private FirebaseAuth mAuth;


    private InspirationAdapter inspirationAdapter;

    ArrayList<ReminderEvent> list;

    public InspirationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        InspirationView = inflater.inflate(R.layout.fragment_inspiration, container, false);


        InspirationRecycleList = (RecyclerView) InspirationView.findViewById(R.id.inspiration_recycle_list);
        InspirationRecycleList.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<ReminderEvent>();

        mAuth = FirebaseAuth.getInstance();
        InspirationRef = FirebaseDatabase.getInstance().getReference();

        return InspirationView;

    }
    @Override
    public void onStart()
    {
        super.onStart();

        String currentUserID = mAuth.getCurrentUser().getUid();

        InspirationRef.child("InspirationEvents").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    ReminderEvent reminderEventList = dataSnapshot1.getValue(ReminderEvent.class);
                    list.add(reminderEventList);
                }
                inspirationAdapter = new InspirationAdapter(getContext(), list);
                InspirationRecycleList.setAdapter(inspirationAdapter);
                inspirationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
