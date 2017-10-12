package co.com.lafemmeapp.lafemmeapp.presentation.dashboard;


import android.app.Fragment;

import java.util.List;

import co.com.lafemmeapp.core.domain.entities.Appointment;
import co.com.lafemmeapp.core.domain.entities.City;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseView;

/**
 * Created by oscargallon on 4/25/17.
 */

public interface IDashboardActivityView extends IBaseView {

    void closeDrawer();

    void changeFragment(Fragment fragment, String tag);

    void callSuperBackPressed();

    void removeCurrentFragment(Fragment fragment);

    void setNavigationDrawerMenu(int menuId);

    void setNavigationHeader(int header);

    void changeHeaderUserName(String userName);

    void changeHeaderUserEmail(String userEmail);

    Fragment getCurrentFragment();

    void setCurrentFragment(Fragment fragment);


}
