package co.com.lafemmeapp.lafemmeapp.presentation.utils

import android.widget.RatingBar
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter

/**
 * Created by oscargallon on 6/10/17.
 */
interface IRateAppointmentDialogFragmentPresenter : IBaseFragmentPresenter {

    fun subscribeToRatingBarChanges(ratingBar: RatingBar)

    fun sendRate(options: String?, rate: Float)
}