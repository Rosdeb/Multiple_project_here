package com.example.daraz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class Viewpage extends FragmentStateAdapter {

    private final List<Fragment>fragmentslist;

    public Viewpage(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentslist) {
        super(fragmentActivity);
        this.fragmentslist = fragmentslist;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentslist.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentslist.size();
    }
}
