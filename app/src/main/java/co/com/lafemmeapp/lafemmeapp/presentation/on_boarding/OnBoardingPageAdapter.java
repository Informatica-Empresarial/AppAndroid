package co.com.lafemmeapp.lafemmeapp.presentation.on_boarding;



import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oscargallon on 4/23/17.
 */

public class OnBoardingPageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList;

    public OnBoardingPageAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList = new ArrayList<>();
    }


    public  void addFragmentToAdapter(Fragment fragment){
        if(mFragmentList!=null){
            mFragmentList.add(fragment);
        }
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList != null && mFragmentList.size() > 0
                && mFragmentList.size() > position ? mFragmentList.get(position) : null;
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }
}
