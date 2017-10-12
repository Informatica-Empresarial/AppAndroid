package co.com.lafemmeapp.lafemmeapp.presentation.services;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks;

/**
 * Created by oscargallon on 5/3/17.
 */

public interface IServiceFragmentPresenter extends IBaseFragmentPresenter, IFragmentCallbacks,
IServiceQuantityChange{

    void getServices();
}
