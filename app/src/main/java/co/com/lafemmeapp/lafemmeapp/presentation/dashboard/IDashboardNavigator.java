package co.com.lafemmeapp.lafemmeapp.presentation.dashboard;

import android.app.Fragment;

/**
 * Created by oscargallon on 5/9/17.
 */

public interface IDashboardNavigator {

     void addFragmentToStack(Fragment fragment);

    Fragment getFragmentFromStack();

    int getStackSize();
}
