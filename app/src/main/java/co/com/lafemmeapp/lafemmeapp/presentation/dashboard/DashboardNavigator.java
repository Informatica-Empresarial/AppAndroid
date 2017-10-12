package co.com.lafemmeapp.lafemmeapp.presentation.dashboard;

import android.app.Fragment;

import com.google.common.collect.Iterables;

import java.util.Stack;

/**
 * Created by oscargallon on 5/9/17.
 */

public class DashboardNavigator implements IDashboardNavigator {

    private Stack<Fragment> mFragmentStack;

    private IDashboardActivityView mDashboardActivityView;

    public DashboardNavigator(IDashboardActivityView mDashboardActivityView) {
        this.mDashboardActivityView = mDashboardActivityView;
        mFragmentStack = new Stack<>();

    }



    public void addFragmentToStack(Fragment fragment) {
        if (mFragmentStack != null) {
            mFragmentStack.add(fragment);
        }
    }

    public Fragment getFragmentFromStack() {
        if (mFragmentStack != null && mFragmentStack.size() > 0) {
            return mFragmentStack.pop();
        }

        return null;
    }

    @Override
    public int getStackSize() {
        return mFragmentStack != null ? mFragmentStack.size() : 0;
    }
}
