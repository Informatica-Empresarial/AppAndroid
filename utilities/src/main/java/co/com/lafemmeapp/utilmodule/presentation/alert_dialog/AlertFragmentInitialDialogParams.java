package co.com.lafemmeapp.utilmodule.presentation.alert_dialog;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oscargallon on 5/13/17.
 */

public class AlertFragmentInitialDialogParams implements Parcelable {

    private final String title;

    private final String message;

    private final boolean showBothButtons;

    private final String positiveButtonText;

    private final String negativeButtonText;

    private final String action;

    public AlertFragmentInitialDialogParams(String title, String message,
                                            boolean showBothButtons,
                                            String positiveButtonText,
                                            String negativeButtonText, String action) {
        this.title = title;
        this.message = message;
        this.showBothButtons = showBothButtons;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public boolean isShowBothButtons() {
        return showBothButtons;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }


    public String getAction() {
        return action;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeByte(this.showBothButtons ? (byte) 1 : (byte) 0);
        dest.writeString(this.positiveButtonText);
        dest.writeString(this.negativeButtonText);
        dest.writeString(this.action);
    }

    protected AlertFragmentInitialDialogParams(Parcel in) {
        this.title = in.readString();
        this.message = in.readString();
        this.showBothButtons = in.readByte() != 0;
        this.positiveButtonText = in.readString();
        this.negativeButtonText = in.readString();
        this.action = in.readString();
    }

    public static final Parcelable.Creator<AlertFragmentInitialDialogParams> CREATOR = new Parcelable.Creator<AlertFragmentInitialDialogParams>() {
        @Override
        public AlertFragmentInitialDialogParams createFromParcel(Parcel source) {
            return new AlertFragmentInitialDialogParams(source);
        }

        @Override
        public AlertFragmentInitialDialogParams[] newArray(int size) {
            return new AlertFragmentInitialDialogParams[size];
        }
    };
}
