package co.com.lafemmeapp.lafemmeapp.presentation.on_boarding.fragments;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView;

/**
 * Created by oscargallon on 4/25/17.
 */

public interface IOnBoardingFragmentView extends IBaseFragmentView {

    void setTVTitleText(String title);

    void setTVDescriptionText(String description);

    void setImageResource(int imageResource);
}
