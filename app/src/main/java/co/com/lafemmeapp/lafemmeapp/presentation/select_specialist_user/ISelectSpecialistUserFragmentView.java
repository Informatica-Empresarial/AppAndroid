package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_user;

import java.util.List;

import co.com.lafemmeapp.core.domain.entities.SpecialistUser;
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView;

/**
 * Created by oscargallon on 5/8/17.
 */

public interface ISelectSpecialistUserFragmentView extends IBaseFragmentView {

     void showSpecialistUsers(List<SpecialistUser> specialistUserList);

     void showProgressBar();

     void dismissProgressBar();

     void changeLYNoSpecialistVisibility(int visibility);

     void changeBTNRandomSpeciliastVisibility(int visibility);
}
