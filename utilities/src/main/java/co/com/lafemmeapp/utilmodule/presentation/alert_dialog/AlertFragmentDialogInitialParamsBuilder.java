package co.com.lafemmeapp.utilmodule.presentation.alert_dialog;

public class AlertFragmentDialogInitialParamsBuilder {
    private String title;
    private String message;
    private boolean showBothButtons;
    private String positiveButtonText;
    private String negativeButtonText;
    private String action;

    public AlertFragmentDialogInitialParamsBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public AlertFragmentDialogInitialParamsBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public AlertFragmentDialogInitialParamsBuilder setShowBothButtons(boolean showBothButtons) {
        this.showBothButtons = showBothButtons;
        return this;
    }

    public AlertFragmentDialogInitialParamsBuilder setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
        return this;
    }

    public AlertFragmentDialogInitialParamsBuilder setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
        return this;
    }

    public AlertFragmentDialogInitialParamsBuilder setAction(String action){
        this.action = action;
        return this;
    }

    public AlertFragmentInitialDialogParams createAlertDialogFragmentInitialParams() {
        return new AlertFragmentInitialDialogParams(title, message,
                showBothButtons, positiveButtonText, negativeButtonText, action);
    }
}