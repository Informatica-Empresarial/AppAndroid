package co.com.lafemmeapp.lafemmeapp.presentation.services;

import java.util.List;

import co.com.lafemmeapp.core.domain.entities.ViewService;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView;

/**
 * Created by oscargallon on 5/3/17.
 */

public interface IServiceFragmentView extends IBaseFragmentView {

    void showServices(List<ViewService> serviceList);

    void showProgressBar();

    void dismissProgressBar();

    void changeServicesAmountPrice(int price);

    void changeBTNNextAvailability(boolean enabled);

}
