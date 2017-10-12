package co.com.lafemmeapp.lafemmeapp.presentation.history


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v13.app.FragmentCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SwitchCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication
import co.com.lafemmeapp.lafemmeapp.events.OnAppointmentRatedEvent
import co.com.lafemmeapp.lafemmeapp.presentation.BaseActivity
import co.com.lafemmeapp.lafemmeapp.presentation.BaseFragment
import co.com.lafemmeapp.lafemmeapp.presentation.utils.RecyclerViewDividerItemDecoration
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.fragment_history.*
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList


class HistoryFragment : BaseFragment(), IHistoryFragmentView, View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {


    private var mPBHistory: ProgressBar? = null
    private var mRVHistory: RecyclerView? = null

    private var mTVOngoing: TextView? = null

    private var mTVPast: TextView? = null

    private var mHistoryFragmentPresenter: IHistoryFragmentPresenter? = null

    private var mLYSOSAvailability: LinearLayout? = null

    private var mSWSOS: SwitchCompat? = null

    private var mSRLHistory:SwipeRefreshLayout? = null

    override fun initViewComponents(view: View) {
        super.hideKeyboard(view, activity.applicationContext)
        mRVHistory = view.findViewById(R.id.rv_history) as RecyclerView
        mPBHistory = view.findViewById(R.id.pb_history) as ProgressBar
        mTVOngoing = view.findViewById(R.id.tv_ongoing) as TextView
        mTVPast = view.findViewById(R.id.tv_past) as TextView
        mSWSOS = view.findViewById(R.id.sw_sos) as SwitchCompat
        mLYSOSAvailability = view.findViewById(R.id.ly_sos_availability) as LinearLayout
        mSRLHistory = view.findViewById(R.id.srl_history) as SwipeRefreshLayout
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mHistoryFragmentPresenter = HistoryFragmentPresenter(this)
        mHistoryFragmentPresenter?.onAttach(activity as IFragmentCallbacks)


    }

    override fun onDetach() {
        super.onDetach()
        mHistoryFragmentPresenter?.onDestroy()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mHistoryFragmentPresenter = HistoryFragmentPresenter(this)
        mHistoryFragmentPresenter?.onAttach(context as IFragmentCallbacks)
    }

    override fun initComponents() {
        mRVHistory?.setHasFixedSize(true)
        mRVHistory?.layoutManager = LinearLayoutManager(activity)
        mRVHistory?.addItemDecoration(RecyclerViewDividerItemDecoration(resources.getDrawable(R.drawable.recycler_view_divider)))
        mTVOngoing?.setOnClickListener(this)
        mTVPast?.setOnClickListener(this)
        mSWSOS?.setOnCheckedChangeListener(this)
        mSRLHistory?.setOnRefreshListener(mHistoryFragmentPresenter)
        mHistoryFragmentPresenter?.initHistory()
    }


    override fun updateAppointment(appointment: Appointment) {
        mRVHistory.let {
            var appointments: ArrayList<Appointment> =
                    (mRVHistory!!.adapter as AppointmentsRecyclerViewAdapter)
                            .getAppointmentsList()
            appointments = appointments.map { aux ->
                if (aux.uuid.equals(appointment.uuid)) {
                    appointment
                } else {
                    aux
                }

            } as ArrayList<Appointment>
            (mRVHistory!!.adapter as AppointmentsRecyclerViewAdapter)
                    .setAppointmentList(appointments)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHistoryFragmentPresenter?.onCreate(arguments)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        mHistoryFragmentPresenter?.onCreateView(view)
        return view
    }


    override fun showAppointments(appointments: List<Appointment>, areOngoing: Boolean) {
        if (mRVHistory !== null && mHistoryFragmentPresenter !== null) {
            mRVHistory!!.adapter = AppointmentsRecyclerViewAdapter(ArrayList<Appointment>(appointments),
                    mHistoryFragmentPresenter as IHistoryFragmentPresenter,
                    WeakReference(activity))
            if (mTVOngoing !== null) {
                mTVOngoing!!.setTextColor(if (areOngoing) resources.getColor(R.color.white)
                else resources.getColor(R.color.warmPink))
                mTVOngoing!!.setBackgroundColor(if (areOngoing) resources.getColor(R.color.colorPrimary)
                else resources.getColor(R.color.colorPrimaryDark))

            }
            if (mTVPast !== null) {
                mTVPast!!.setTextColor(if (areOngoing) resources.getColor(R.color.warmPink)
                else resources.getColor(R.color.white))
                mTVPast!!.setBackgroundColor(if (areOngoing) resources.getColor(R.color.colorPrimaryDark)
                else resources.getColor(R.color.colorPrimary))
            }


        }

    }

    override fun onClick(v: View) {
        if (mHistoryFragmentPresenter !== null) {
            when (v.id) {
                R.id.tv_ongoing -> mHistoryFragmentPresenter!!.filterAppointments(true)
                R.id.tv_past -> mHistoryFragmentPresenter!!.filterAppointments(false)
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        mSWSOS?.isEnabled = false
        mHistoryFragmentPresenter?.toggleSOS(activity)
    }

    override fun showProgressBar() {
        mSRLHistory?.isRefreshing = true

    }

    override fun hideProgressBar() {
        mSRLHistory?.isRefreshing = false

    }

    override fun showProgressDialog() {
        super.showProgressDialog()
    }

    override fun hideProgressDialog() {
        super.hideProgressDialog()
    }

    companion object {

        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    override fun changeLYSOSAvailabilityVisibility(visible: Boolean) {
        mLYSOSAvailability?.visibility =
                if (visible) View.VISIBLE else View.GONE
    }

    override fun checkOrUCheckSW(checked: Boolean) {

        if (checked) {
            LaFemmeApplication.getInstance()
                    .initSOSService()
        } else {
            LaFemmeApplication.getInstance()
                    .cancelSOSService()
        }

        mSWSOS?.setOnCheckedChangeListener(null)
        mSWSOS?.isChecked = checked
        mSWSOS?.setOnCheckedChangeListener(this)
        mSWSOS?.isEnabled = true


    }


    override fun onGPSEnabled() {
        mHistoryFragmentPresenter?.checkSOS()
        mHistoryFragmentPresenter?.getUserAppointmentsHistory()
    }

    override fun onSessionChecked(isSpecialist: Boolean) {
        if (isSpecialist) {
            requestPermissions()
        } else {
            mHistoryFragmentPresenter?.getUserAppointmentsHistory()
        }
    }

    fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity, Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (FragmentCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) || FragmentCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                FragmentCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                        AppModuleConstants.LOCATION_PERMISSION_RESULT)

            } else {
                FragmentCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                        AppModuleConstants.LOCATION_PERMISSION_RESULT)
            }
        } else if (mHistoryFragmentPresenter != null) {
            mHistoryFragmentPresenter?.getGoogleApiClientAndShowGPSDialog(activity)
        }
    }

    override fun onLocationPermissionsGranted() {
        mHistoryFragmentPresenter?.getGoogleApiClientAndShowGPSDialog(activity)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissions !== null && grantResults !== null) {
            mHistoryFragmentPresenter?.onRequestPermissionsResult(requestCode, permissions,
                    grantResults)
        }

    }

    override fun clearAppointments() {
        mRVHistory?.adapter?.let {
            (mRVHistory!!.adapter!! as AppointmentsRecyclerViewAdapter).clear()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mHistoryFragmentPresenter?.onActivityResult(requestCode, resultCode, data)
    }


    override fun showMessage(title: String, description: String, action: String, showBothButtons: Boolean) {
        (activity as BaseActivity).showMessage(title, description, action, showBothButtons)
    }

    override fun populateView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
