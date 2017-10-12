package co.com.lafemmeapp.utilmodule.presentation.view_interfaces;

import android.os.Parcelable;
import android.view.View;

import org.jetbrains.annotations.Nullable;

/**
 * Created by oscargallon on 4/25/17.
 */

public interface IFragmentCallbacks {


    void onViewClicked(View view, @Nullable Parcelable parcelable);

}
