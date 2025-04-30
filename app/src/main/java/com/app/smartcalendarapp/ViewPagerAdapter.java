package com.app.smartcalendarapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.smartcalendarapp.ScheduleFragment;
import com.app.smartcalendarapp.PastFragment;
import com.app.smartcalendarapp.NotificationFragment;
import com.app.smartcalendarapp.ProfileFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ScheduleFragment();
            case 1:
                return new PastFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new ProfileFragment();
            default:
                return new ScheduleFragment(); // Fallback
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Total tabs
    }
}
