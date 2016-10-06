package museum.findit.com.myapplication.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import museum.findit.com.myapplication.fragment.ItemFragment;
import museum.findit.com.myapplication.fragment.LeaderboardFragment;

/**
 * Created by hui on 2016-10-06.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ItemFragment tab1 = new ItemFragment();
                return tab1;
            case 1:
                LeaderboardFragment tab2 = new LeaderboardFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
