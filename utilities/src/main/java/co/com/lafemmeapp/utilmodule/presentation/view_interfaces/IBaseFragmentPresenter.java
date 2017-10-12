package co.com.lafemmeapp.utilmodule.presentation.view_interfaces;

import android.os.Bundle;
import android.view.View;



/**
 * Created by oscargallon on 4/25/17.
 */

public interface IBaseFragmentPresenter extends IBasePresenter {


    void onCreateView(View view);

    void onAttach(IFragmentCallbacks fragmentCallback);



}
