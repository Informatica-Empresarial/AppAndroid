package co.com.lafemmeapp.utilmodule.presentation.alert_dialog;

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView;

/**
 * Created by oscargallon on 5/13/17.
 */

public interface IAlertFragmentDialogView extends IBaseFragmentView {

    void setDialogTitle(String text);

    void setDialogMessage(String text);

    void setPositiveButtonText(String text);

    void setNegativeButtonText(String text);

    void hideNegativeButton();
}
