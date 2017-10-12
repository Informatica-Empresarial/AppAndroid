package co.com.lafemmeapp.lafemmeapp.presentation.utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication
import co.com.lafemmeapp.lafemmeapp.events.OnAppointmentRatedEvent
import co.com.lafemmeapp.lafemmeapp.presentation.BaseFragment
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import me.zhanghai.android.materialratingbar.MaterialRatingBar

/**
 * Created by oscargallon on 4/23/17.
 */

class RateAppointmentDialogFragment : BaseFragment(), IRateAppointmentDialogFragmentView {

    var mTVRateStatus: TextView? = null

    var mRBAppoitmentRate: MaterialRatingBar? = null

    var mRateAppointmentDialogFragmentPresenter: IRateAppointmentDialogFragmentPresenter? = null

    var mLYOptionsContainer: LinearLayout? = null

    var mTVOption1: TextView? = null

    var mTVOption2: TextView? = null

    var mTVOption3: TextView? = null

    var mTVOption4: TextView? = null

    var mBTNSendRate: Button? = null

    var mIVAvatar: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRateAppointmentDialogFragmentPresenter?.onCreate(arguments)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_full_screen_dialog, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view?.let {
            mRateAppointmentDialogFragmentPresenter?.onCreateView(view)
        }
    }


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mRateAppointmentDialogFragmentPresenter = RateAppointmentDialogFragmentPresenter(this, activity!!)
        mRateAppointmentDialogFragmentPresenter?.onAttach(activity as IFragmentCallbacks)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mRateAppointmentDialogFragmentPresenter = RateAppointmentDialogFragmentPresenter(this, context!!)
        mRateAppointmentDialogFragmentPresenter?.onAttach(context as IFragmentCallbacks)
    }

    override fun initViewComponents(view: View) {
        mTVRateStatus = view.findViewById(R.id.tv_rate_status) as TextView
        mRBAppoitmentRate = view.findViewById(R.id.rb_appointment_rate) as MaterialRatingBar
        mLYOptionsContainer = view.findViewById(R.id.ly_option_buttons_container) as LinearLayout
        mBTNSendRate = view.findViewById(R.id.btn_send_rate) as Button
        mTVOption1 = view.findViewById(R.id.tv_option_1) as TextView
        mTVOption2 = view.findViewById(R.id.tv_option_2) as TextView
        mTVOption3 = view.findViewById(R.id.tv_option_3) as TextView
        mTVOption4 = view.findViewById(R.id.tv_option_4) as TextView
        mIVAvatar = view.findViewById(R.id.iv_avatar) as ImageView

    }

    override fun showHideOptionsButtons(visibility: Int) {
        mLYOptionsContainer?.visibility = visibility
    }

    override fun initComponents() {
        mRBAppoitmentRate.let {

            mRateAppointmentDialogFragmentPresenter?.subscribeToRatingBarChanges(mRBAppoitmentRate!!)


        }

        mTVOption1?.setOnClickListener(this)
        mTVOption2?.setOnClickListener(this)
        mTVOption3?.setOnClickListener(this)
        mTVOption4?.setOnClickListener(this)

        mBTNSendRate?.setOnClickListener {

            if (mRBAppoitmentRate !== null) {
                var options: String? = ""
                if (mTVOption1 !== null
                        && mTVOption1!!.tag !== null
                        && mTVOption1!!.tag as Boolean) {
                    options += mTVOption1!!.text.toString()
                }

                if (mTVOption2 !== null
                        && mTVOption2!!.tag !== null
                        && mTVOption2!!.tag as Boolean) {
                    options += if (TextUtils.isEmpty(options)) mTVOption2!!.text.toString() else
                        ", ${mTVOption2!!.text}"
                }

                if (mTVOption3 !== null
                        && mTVOption3!!.tag !== null
                        && mTVOption3!!.tag as Boolean) {
                    options += if (TextUtils.isEmpty(options)) mTVOption3!!.text.toString() else
                        ", ${mTVOption3!!.text}"
                }

                if (mTVOption4 !== null
                        && mTVOption4!!.tag !== null
                        && mTVOption4!!.tag as Boolean) {
                    options += if (TextUtils.isEmpty(options)) mTVOption4!!.text.toString() else
                        ", ${mTVOption4!!.text}"
                }

                mRateAppointmentDialogFragmentPresenter?.sendRate(options, mRBAppoitmentRate!!.rating)
            }


        }


    }

    override fun onClick(view: View) {

        if ((view as TextView).currentTextColor == resources.getColor(R.color.colorAccent)) {
            view.setTextColor(resources.getColor(R.color.colorPrimary))
            view.background = resources.getDrawable(R.drawable.button_border_primary_white_background)
            view.tag = true

        } else {
            view.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent))
            view.background = resources.getDrawable(R.drawable.tertiary_button_rounded_background)
            view.tag = false
        }

    }

    override fun changeStatus(status: String) {
        mTVRateStatus?.text = status
    }

    override fun onAppointmentRated(appointment: Appointment) {
        LaFemmeApplication
                .getInstance()
                .getmBus()
                .post(OnAppointmentRatedEvent(appointment))
        activity.onBackPressed()
    }

    override fun showMessage(title: String, description: String, action: String, showBothButtons: Boolean) {

    }

    override fun populateView() {

    }

    override fun showProgressDialog() {
        super.showProgressDialog()

    }

    override fun hideProgressDialog() {
        super.hideProgressDialog()
    }

    override fun showSpecialistAvatar(url: String) {
        mIVAvatar?.let {
            Picasso.with(mIVAvatar!!.context)
                    .load(url)
                    .transform(BitmapTransform(200, 200))
                    .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                    .resize(200, 200)
                    .into(mIVAvatar!!)
        }
    }

    companion object {

        fun newInstance(appointment: Appointment): RateAppointmentDialogFragment {
            val fragment = RateAppointmentDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(AppModuleConstants.VIEW_HISTORY_DETAIL,
                    appointment)
            fragment.arguments = bundle
            return fragment
        }
    }
}
