package com.example.fyp_reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsAccessorAdapter tabsAccessorAdapter;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SimpleRemind");
        viewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        tabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsAccessorAdapter);

        tabLayout = (TabLayout) findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewPager);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        if (currentUser == null)
        {
            GoToLoginActivity();
        }
        else
        {
            VerifyUserExistence();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.options, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_reminder)
        {
            GoToAddReminderActivity();
        }
        if (item.getItemId() == R.id.add_plan)
        {
            GoToAddPlanActivity();
        }
        if (item.getItemId() == R.id.add_inspiration)
        {
            GoToAddInspirationActivity();
        }
        /*if (item.getItemId() == R.id.profile_setting)
        {
            GoToProfileSettingActivity();
        }*/
        if (item.getItemId() == R.id.sign_out)
        {
            mAuth.signOut();
            GoToLoginActivity();
        }
        return true;
    }

    private void VerifyUserExistence()
    {
        String currentUserID = mAuth.getCurrentUser().getUid();
        myRef.child("Users").child(currentUserID)
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if ((dataSnapshot.child("name").exists()))
                        {
                            Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
    }

    private void GoToLoginActivity()
    {
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }
    private void GoToAddReminderActivity()
    {
        Intent AddReminderIntent = new Intent(MainActivity.this,AddReminderActivity.class);
        startActivity(AddReminderIntent);
    }
    private void GoToAddPlanActivity()
    {
        Intent AddPlanIntent = new Intent(MainActivity.this,AddPlanActivity.class);
        startActivity(AddPlanIntent);
    }
    private void GoToAddInspirationActivity()
    {
        Intent AddInspirationIntent = new Intent(MainActivity.this,AddInspirationActivity.class);
        startActivity(AddInspirationIntent);
    }
    /*private void GoToProfileSettingActivity()
    {
        Intent ProfileSettingIntent = new Intent(MainActivity.this,ProfileSettingActivity.class);
        startActivity(ProfileSettingIntent);
    }*/


}
