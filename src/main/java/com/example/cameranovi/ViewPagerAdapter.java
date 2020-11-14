package com.example.cameranovi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


//Class to controll tabs using fragments
//It holds a fragment list added via the Main ViewActivity
//And handles the Titles using a String Array
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitle = new ArrayList<>();


    public ViewPagerAdapter(@NonNull FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitle.get(position);
    }


    public void addFragment(Fragment f, String title) {

        fragmentList.add(f);
        fragmentListTitle.add(title);

    }
}
