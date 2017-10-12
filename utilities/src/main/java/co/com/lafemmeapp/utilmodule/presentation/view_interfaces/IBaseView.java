package co.com.lafemmeapp.utilmodule.presentation.view_interfaces;

import android.os.Bundle;

import org.jetbrains.annotations.Nullable;

/**
 * Created by stephany.berrio on 4/12/17.
 * BaseView interface to control the Views behaviour
 */
public interface IBaseView {

    /**
     * this method should init the view components inside the View
     */
    void initViewComponents();

    /**
     * This method should init the variables and the logic in the View
     */
    void initComponents();


    void showMessage(String title, String description,
                     String action, boolean showBothButtons);

    /**
     * This method should populate the view components inside the View
     * View components are objects such as {@link android.widget.Button}
     * {@link android.widget.TextView} ...
     */
    void populateView();

    void changeActivity(Bundle arguments, Class destinationActivity);

    void startActivityForResult(@Nullable Bundle arguments, int requestCode, Class destinationActivity);

    void showProgressDialog();

    void hideProgressDialog();
}
