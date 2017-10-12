package co.com.lafemmeapp.utilmodule.presentation.view_interfaces;

import android.view.View;

/**
 * Created by oscargallon on 4/25/17.
 */

public interface IBaseFragmentView {

    /**
     * this method should init the view components inside the View
     * @param view The layout view
     */
    void initViewComponents(View view);

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

    void showProgressDialog();

    void hideProgressDialog();
}
