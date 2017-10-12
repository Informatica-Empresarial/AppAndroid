package co.com.lafemmeapp.lafemmeapp.presentation.history_detail

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import co.com.lafemmeapp.core.domain.entities.Appointment
import co.com.lafemmeapp.core.domain.entities.ViewService
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.presentation.BaseFragment
import co.com.lafemmeapp.lafemmeapp.presentation.voucher.ServicesVoucherRecyclerViewAdapter
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HistoryDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HistoryDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryDetailFragment : BaseFragment(), IHistoryDetailFragmentView {



    private var mTVDate: TextView? = null

    private var mTVTime: TextView? = null

    private var mRVService: RecyclerView? = null

    private var mTVAddress: TextView? = null

    private var mTVSpecialist: TextView? = null

    private var mHistoryDetailFragmentPresenter: IHistoryDetailFragmentPresenter? = null

    private var mBTNCancelAppointment: Button? = null

    private var mSVVoucherContainer: ScrollView? = null

    private var mPBVoucher: ProgressBar? = null

    private var mTVPrice: TextView? = null

    private var mTVPhone: TextView? = null

    private var mBTNRateService: Button? = null

    private var mIVShowAppointmentAddress: ImageView? = null

    private var mTVNameTitle:TextView ? = null

    private var mBTNInitService:Button? = null

    private var mBTNFinishService:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHistoryDetailFragmentPresenter?.onCreate(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_history_detail, container, false)
        mHistoryDetailFragmentPresenter?.onCreateView(view)
        return view
    }

    override fun initViewComponents(view: View) {
        super.hideKeyboard(view, activity.applicationContext)
        mRVService = view.findViewById(R.id.rv_services) as RecyclerView
        mTVAddress = view.findViewById(R.id.tv_address) as TextView
        mTVSpecialist = view.findViewById(R.id.tv_specialist) as TextView
        mSVVoucherContainer = view.findViewById(R.id.sv_voucher_container) as ScrollView
        mPBVoucher = view.findViewById(R.id.pb_voucher) as ProgressBar
        mTVPrice = view.findViewById(R.id.tv_price) as TextView
        mTVDate = view.findViewById(R.id.tv_date) as TextView
        mTVTime = view.findViewById(R.id.tv_time) as TextView
        mTVPhone = view.findViewById(R.id.tv_phone) as TextView
        mBTNCancelAppointment = view.findViewById(R.id.btn_cancel_appointment) as Button
        mBTNRateService = view.findViewById(R.id.btn_rate_service) as Button
        mIVShowAppointmentAddress = view.findViewById(R.id.iv_show_appointment_address) as ImageView
        mTVNameTitle = view.findViewById(R.id.tv_specialist_title) as TextView
        mBTNInitService = view.findViewById(R.id.btn_init_service) as Button
        mBTNFinishService = view.findViewById(R.id.btn_finish_service) as Button

    }

    override fun initComponents() {
        mRVService?.setHasFixedSize(true)
        mRVService?.layoutManager = LinearLayoutManager(activity)
        if (mBTNCancelAppointment !== null && mHistoryDetailFragmentPresenter !== null) {
            mBTNCancelAppointment?.setOnClickListener {
                if (mHistoryDetailFragmentPresenter !== null) {
                    mHistoryDetailFragmentPresenter!!.cancelAppointment()
                }
            }
        }

        mBTNRateService?.setOnClickListener {
            view ->
            mHistoryDetailFragmentPresenter?.onViewClicked(view, null)
        }

        mIVShowAppointmentAddress?.setOnClickListener {
            mHistoryDetailFragmentPresenter?.showAddress(activity)
        }

        mBTNFinishService?.setOnClickListener {
            mHistoryDetailFragmentPresenter?.startFinishAppointment(false)
        }

        mBTNInitService?.setOnClickListener {
            mHistoryDetailFragmentPresenter?.startFinishAppointment(true)
        }
    }


    override fun showMessage(title: String?, description: String?, action: String?, showBothButtons: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun populateView() {

    }

    companion object {

        fun newInstance(parcelable: Appointment): HistoryDetailFragment {
            val bundle = Bundle()
            bundle.putParcelable(AppModuleConstants.VIEW_HISTORY_DETAIL,
                    parcelable)
            val historyDetail = HistoryDetailFragment()
            historyDetail.arguments = bundle
            return historyDetail
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mHistoryDetailFragmentPresenter = HistoryDetailFragmentPresenter(this)
        mHistoryDetailFragmentPresenter?.onAttach(activity as IFragmentCallbacks)


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mHistoryDetailFragmentPresenter = HistoryDetailFragmentPresenter(this)
        mHistoryDetailFragmentPresenter?.onAttach(activity as IFragmentCallbacks)
    }

    override fun onDetach() {
        super.onDetach()
        mHistoryDetailFragmentPresenter?.onDestroy()
    }



    override fun setDate(date: String) {
        if (date !== null && mTVDate !== null) {
            mTVDate?.text = date
        }
    }

    override fun setTime(time: String) {
        if (time !== null && mTVTime !== null) {
            mTVTime?.text = time
        }
    }

    override fun setServices(services: List<ViewService>) {
        if (services !== null && mRVService !== null) {
            mRVService?.adapter = ServicesVoucherRecyclerViewAdapter(services)
        }
    }

    override fun setAddress(address: String) {
        if (address !== null && mTVAddress !== null) {
            mTVAddress?.text = address
        }
    }

    override fun setSpecialistNameOurCustomer(specialist: String) {
        if (specialist !== null && mTVSpecialist !== null) {
            mTVSpecialist?.text = specialist
        }
    }

    override fun setPrice(price: String) {
        if (price !== null && mTVPrice !== null) {
            mTVPrice?.text = price
        }
    }

    override fun setPhone(phone: String) {
        if (phone !== null && mTVPhone !== null) {
            mTVPhone?.text = phone
        }
    }

    override fun showCancelButton(visibility: Int) {
        mBTNCancelAppointment?.visibility = visibility
    }

    override fun onAppointmentCanceled() {
        if (mHistoryDetailFragmentPresenter !== null) {
            mHistoryDetailFragmentPresenter!!.onViewClicked(mBTNCancelAppointment, null)
        }
    }

    override fun showProgressDialog() {
        super.showProgressDialog()
    }

    override fun hideProgressDialog() {
        super.hideProgressDialog()

    }

    override fun showHideRateAppointmentButton(visibility: Int) {
        mBTNRateService?.visibility = visibility
    }



    override fun showHideShowAddress(visibility: Int) {
        mIVShowAppointmentAddress?.visibility = visibility
    }

    override fun changeNameTitle(isSpecialist: Boolean) {
        mTVNameTitle?.text =
                if(isSpecialist) getString(R.string.customer_label) else
                    getString(R.string.specialist_label)
    }

    override fun showHideBTNInitService(visibility: Int) {
       mBTNInitService?.visibility = visibility
    }

    override fun showHideBTNFinishService(visibility: Int) {
        mBTNFinishService?.visibility = visibility
    }



}
