package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_time


import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.DatePickerMinMaxDate
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest
import co.com.lafemmeapp.core.domain.entities.abstracts.SpecialistTime
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity
import co.com.lafemmeapp.lafemmeapp.presentation.BaseFragment
import co.com.lafemmeapp.lafemmeapp.presentation.utils.BitmapTransform
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SelectSpecialistTimeFragment : BaseFragment(), ISelectSpecialistTimeFragmentView {

    private var mSelectSpecialistFragmentPresenter: ISelectSpecialistTimeFragmentPresenter? = null

    private var mIVSpecialistAvatar: CircularImageView? = null

    private var mTVName: TextView? = null

    private var mTVAditionalInfo: TextView? = null

    private var mTVDate: TextView? = null

    private var mRVTime: RecyclerView? = null

    private var mIVCalendar: ImageView? = null

    private var mBTNNextSelectSpecialistTime: Button? = null

    private var mLYCurrentDateContainer: LinearLayout? = null

    private var mDatePickerDialog: DatePickerDialog? = null

    private var mCalendarCurrentDate: Calendar? = null

    private var mLYNoTime: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSelectSpecialistFragmentPresenter?.onCreate(arguments)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_select_specialist_time, container, false)
        mSelectSpecialistFragmentPresenter?.onCreateView(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mSelectSpecialistFragmentPresenter = SelectSpecialistTimeFragmentPresenter(this)
        mSelectSpecialistFragmentPresenter?.onAttach(context as IFragmentCallbacks)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mSelectSpecialistFragmentPresenter = SelectSpecialistTimeFragmentPresenter(this)
        mSelectSpecialistFragmentPresenter?.onAttach(activity as IFragmentCallbacks)
    }

    override fun initViewComponents(view: View) {
        super.hideKeyboard(view, activity.applicationContext)
        mIVSpecialistAvatar = view.findViewById(R.id.iv_specialist_user_avatar) as CircularImageView
        mTVName = view.findViewById(R.id.tv_specialist_name) as TextView
        mTVAditionalInfo = view.findViewById(R.id.tv_specialist_aditional_info) as TextView
        mTVDate = view.findViewById(R.id.tv_date) as TextView
        mRVTime = view.findViewById(R.id.rv_times) as RecyclerView
        mIVCalendar = view.findViewById(R.id.iv_calendar) as ImageView
        mBTNNextSelectSpecialistTime = view.findViewById(R.id.btn_next_select_specialist_time) as Button
        mLYCurrentDateContainer = view.findViewById(R.id.ly_current_date_container) as LinearLayout
        mLYNoTime = view.findViewById(R.id.ly_no_time) as LinearLayout

    }

    override fun initComponents() {
        mRVTime!!.setHasFixedSize(true)
        mRVTime!!.layoutManager = LinearLayoutManager(activity)
        mCalendarCurrentDate = Calendar.getInstance()

        mLYNoTime!!.visibility = View.GONE
        mIVCalendar!!.setOnClickListener {
            if (mSelectSpecialistFragmentPresenter != null) {
                mSelectSpecialistFragmentPresenter!!.showCalendar()
            }
        }

        mBTNNextSelectSpecialistTime!!.setOnClickListener { v ->

            mSelectSpecialistFragmentPresenter?.onViewClicked(v, null)

        }


        if (mSelectSpecialistFragmentPresenter != null) {
            mSelectSpecialistFragmentPresenter!!.getSpecialistTime(Calendar.getInstance().time)
        }

    }

    override fun showMessage(title: String, description: String, action: String, showBothButtons: Boolean) {
        if (activity != null) {
            (activity as BaseActivity)
                    .showMessage(title, description, action, showBothButtons)
        }
    }

    override fun populateView() {

    }

    override fun showSpecialistUserAvailableTimeForADate(specialistTimeList: List<SpecialistTime>) {

        mRVTime?.adapter = SelectSpecialistTimeRecyclerViewAdapter(specialistTimeList,
                activity, mSelectSpecialistFragmentPresenter)
        mLYNoTime?.visibility = if (specialistTimeList.isNotEmpty()) View.GONE else View.VISIBLE


    }

    override fun changeBTNNextAvailability(enabled: Boolean) {
        if (mBTNNextSelectSpecialistTime != null && activity != null) {
            mBTNNextSelectSpecialistTime!!.isEnabled = enabled
            mBTNNextSelectSpecialistTime!!.background = if (enabled)
                activity.resources.getDrawable(R.drawable.main_button_rounded_background)
            else
                activity.resources.getDrawable(R.drawable.main_button_disable_rounded_background)
        }
    }

    override fun showSpecialistName(name: String) {
        if (mTVName != null) {
            mTVName!!.text = name
        }
    }

    override fun showSpecialistAditionalInfo(aditionalInfo: String) {
        if (mTVAditionalInfo != null) {
            mTVAditionalInfo!!.text = aditionalInfo
        }
    }

    override fun showDatePicker(datePickerMinMaxDate: DatePickerMinMaxDate) {
        mDatePickerDialog = DatePickerDialog.newInstance(mSelectSpecialistFragmentPresenter, mCalendarCurrentDate!!
                .get(Calendar.YEAR), mCalendarCurrentDate!!.get(Calendar.MONTH),
                mCalendarCurrentDate!!.get(Calendar.DAY_OF_MONTH))
        val minDateCalendar = Calendar.getInstance()
        val maxDateCalendar = Calendar.getInstance()

        minDateCalendar.timeInMillis = datePickerMinMaxDate.minDate
        maxDateCalendar.timeInMillis = datePickerMinMaxDate.minDate

        // mDatePickerDialog
        //       .setMinDate(minDateCalendar);
        //mDatePickerDialog.setMaxDate(maxDateCalendar);
        mDatePickerDialog!!.selectableDays = datePickerMinMaxDate.selectableDates
        mDatePickerDialog!!.show(fragmentManager, null)

    }

    override fun showDate(date: String) {
        if (mTVDate != null) {
            mTVDate!!.text = date
        }
    }

    override fun getDateShown(): String? {
        if (mTVDate != null) {
            return mTVDate!!.text.toString()
        }
        return null
    }

    override fun showSpecialistAvatar(avatarUrl: String) {
        if (mIVSpecialistAvatar != null) {
            val builder = Picasso.Builder(activity)

            builder.build().load(avatarUrl)
                    .transform(BitmapTransform(200, 200))
                    .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                    .resize(200, 200)
                    .into(mIVSpecialistAvatar!!, object : Callback {
                        override fun onSuccess() {
                            Log.i("Avatar", "Loaded")
                        }

                        override fun onError() {
                            Log.i("Avatar", "Error")
                        }
                    })
        }
    }

    companion object {

        fun newInstance(viewServicesRequest: ViewServicesRequest): SelectSpecialistTimeFragment {
            val selectSpecialistFragment = SelectSpecialistTimeFragment()
            val bundle = Bundle()
            bundle.putParcelable(AppModuleConstants.VIEW_SERVICES_SELECTED_KEY, viewServicesRequest)
            selectSpecialistFragment.arguments = bundle
            return selectSpecialistFragment
        }
    }

    override fun showProgressDialog() {
        super.showProgressDialog()
    }

    override fun hideProgressDialog() {
        super.hideProgressDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSelectSpecialistFragmentPresenter?.onDestroy()
    }
}
