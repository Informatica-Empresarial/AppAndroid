package co.com.lafemmeapp.utilmodule.presentation.view_interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;


/**
 * Created by stephany.berrio on 4/12/17.
 * This is the BasePresenter interface to control the presenter behaviour
 */
public interface IBasePresenter {

    /**
     * This method should be called when the {@link android.app.Activity} calls onCreate
     */
    void onCreate(Bundle arguments);

    /**
     * This method should be called when the {@link android.app.Activity} calls onDestroy
     */
    void onDestroy();

    /**
     * This is a method to handle an activity result inside the presenter
     *
     * @param requestCode request code
     * @param resultCode  result
     * @param data        data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     * This method is used to add a textWatcher Observable to an specific {@link TextView}
     *
     * @param textView        {@link TextView} that will have the watcher
     * @param validatorMapper the fuction that will validate the text
     */
    void subscribeTextViewToTextWatcherEvent(@NonNull TextView textView,
                                             @NonNull ITextViewValidatorMapper validatorMapper);

    /**
     * This method is used to validate if a textView match with his validation
     * @param textChangedMappedEvent textChangedMappedEvent that contains the view id
     *                              that has invoke the method and a boolean
     *                              that indicate if matches or not
     */
    void onTextChangeMappedEvent(TextChangedMappedEvent textChangedMappedEvent);

    /**
     * This is a method to handle an activity permission result inside the present
     *
     * @param requestCode  request code
     * @param permissions  permissions granted
     * @param grantResults result
     */
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                    @NonNull int[] grantResults);

}
