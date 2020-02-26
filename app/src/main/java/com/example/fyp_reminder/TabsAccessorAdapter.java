package com.example.fyp_reminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAccessorAdapter extends FragmentPagerAdapter {
    public TabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                ReminderFragment reminderFragment = new ReminderFragment();
                return reminderFragment;

            case 1:
                PlanFragment planFragment = new PlanFragment();
                return planFragment;

            case 2:
                InspirationFragment inspirationFragment = new InspirationFragment();
                return inspirationFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Reminder";

            case 1:
                return "Plan";

            case 2:
                return "Inspiration";

            default:
                return null;
        }
    }
}
