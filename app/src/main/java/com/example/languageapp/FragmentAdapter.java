package com.example.languageapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter
{
    private Context mContext;
    public FragmentAdapter(Context context ,FragmentManager fm)
    {
        super(fm);
        mContext= context;
    }

    @NonNull

    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return new FamilyFragment();
        }
        else if(position==1)
        {
            return new ColorFragment();
        }
        else if(position==2)
        {
            return new PhrasesFragment();
        }
        else
        {
            return  new NumberFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }


    public CharSequence getPageTitle(int position) {
       if(position==0)
       {
           return mContext.getString(R.string.family);
       }
       else if(position==1)
       {
           return mContext.getString(R.string.Colors);
       }
       else if(position==2)
       {
           return mContext.getString(R.string.phrases);
       }
       else {
           return mContext.getString(R.string.numbers);
       }
    }
}
