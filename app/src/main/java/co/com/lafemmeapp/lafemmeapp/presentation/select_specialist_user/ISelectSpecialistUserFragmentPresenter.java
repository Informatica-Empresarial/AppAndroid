package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_user;

import android.view.View;

import co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_time.SelectSpecialistTimeRecyclerViewAdapter;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by oscargallon on 5/8/17.
 */

public interface ISelectSpecialistUserFragmentPresenter extends IBaseFragmentPresenter,
        IFragmentCallbacks {

    void getAppointmentAvailability();

    void selectRandomSpecialist(SpecialistUserRecyclerViewAdapter
                                        specialistUserRecyclerViewAdapter,
                                View view);
}
