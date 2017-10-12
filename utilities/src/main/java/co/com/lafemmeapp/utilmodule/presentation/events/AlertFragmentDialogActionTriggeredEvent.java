package co.com.lafemmeapp.utilmodule.presentation.events;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oscargallon on 5/13/17.
 */

public class AlertFragmentDialogActionTriggeredEvent implements Parcelable {

    private final boolean wasPositiveButton;

    private final String action;

    public AlertFragmentDialogActionTriggeredEvent(boolean wasPositiveButton,
                                                   String action) {
        this.wasPositiveButton = wasPositiveButton;
        this.action = action;
    }

    public boolean isWasPositiveButton() {
        return wasPositiveButton;
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
        dest.writeByte(this.wasPositiveButton ? (byte) 1 : (byte) 0);
        dest.writeString(this.action);
    }

    protected AlertFragmentDialogActionTriggeredEvent(Parcel in) {
        this.wasPositiveButton = in.readByte() != 0;
        this.action = in.readString();
    }

    public static final Parcelable.Creator<AlertFragmentDialogActionTriggeredEvent> CREATOR = new Parcelable.Creator<AlertFragmentDialogActionTriggeredEvent>() {
        @Override
        public AlertFragmentDialogActionTriggeredEvent createFromParcel(Parcel source) {
            return new AlertFragmentDialogActionTriggeredEvent(source);
        }

        @Override
        public AlertFragmentDialogActionTriggeredEvent[] newArray(int size) {
            return new AlertFragmentDialogActionTriggeredEvent[size];
        }
    };
}
