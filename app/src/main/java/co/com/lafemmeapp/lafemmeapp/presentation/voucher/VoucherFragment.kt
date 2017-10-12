package co.com.lafemmeapp.lafemmeapp.presentation.voucher


import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.core.domain.entities.ViewService
import co.com.lafemmeapp.core.domain.entities.ViewServicesRequest
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.presentation.BaseFragment
import co.com.lafemmeapp.lafemmeapp.presentation.voucher.dialog.AppointmentConfirmationDialogFragment
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class VoucherFragment : BaseFragment(), IVoucherFragmentView {


    private var mTVDate: TextView? = null

    private var mTVTime: TextView? = null

    private var mRVService: RecyclerView? = null

    private var mTVAddress: TextView? = null

    private var mTVSpecialist: TextView? = null

    private var mVoucherFragmentPresenter: IVoucherFragmentPresenter? = null

    private var mBTNNextVoucher: Button? = null

    private var mBTNCancelVoucher: Button? = null

    private var mSVVoucherContainer: ScrollView? = null

    private var mPBVoucher: ProgressBar? = null

    private var mTVPrice: TextView? = null


    override fun initViewComponents(view: View) {
        super.hideKeyboard(view, activity.applicationContext)
        mRVService = view.findViewById(R.id.rv_services) as RecyclerView
        mTVAddress = view.findViewById(R.id.tv_address) as TextView
        mTVSpecialist = view.findViewById(R.id.tv_specialist) as TextView
        mBTNNextVoucher = view.findViewById(R.id.btn_next_voucher) as Button
        mSVVoucherContainer = view.findViewById(R.id.sv_voucher_container) as ScrollView
        mPBVoucher = view.findViewById(R.id.pb_voucher) as ProgressBar
        mTVPrice = view.findViewById(R.id.tv_price) as TextView
        mTVDate = view.findViewById(R.id.tv_date) as TextView
        mTVTime = view.findViewById(R.id.tv_time) as TextView
        mBTNCancelVoucher = view.findViewById(R.id.btn_cancel_voucher) as Button


    }

    override fun showAppointmentSpecialist(name: String, lastName: String) {
        mTVSpecialist?.text = String.format(Locale.getDefault(),
                "%s %s", name, lastName)
    }

    override fun showAppointmentAddress(address: String) {
        mTVAddress?.text = address
    }


    override fun showServices(services: List<ViewService>) {
        if (mRVService !== null) {
            mRVService?.adapter = ServicesVoucherRecyclerViewAdapter(services)
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mVoucherFragmentPresenter = VoucherFragmentPresenter(this)
        mVoucherFragmentPresenter?.onAttach(activity as IFragmentCallbacks)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mVoucherFragmentPresenter = VoucherFragmentPresenter(this)
        mVoucherFragmentPresenter?.onAttach(context as IFragmentCallbacks)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVoucherFragmentPresenter?.onCreate(arguments)
    }

    override fun showVoucher() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initComponents() {
        mRVService?.setHasFixedSize(true)
        mRVService?.layoutManager = LinearLayoutManager(activity)
        changeBTNNextAvailability(false)
        mBTNNextVoucher?.setOnClickListener { view ->
            mVoucherFragmentPresenter?.onViewClicked(view, null)
        }

        mBTNCancelVoucher?.setOnClickListener { view ->
            mVoucherFragmentPresenter?.onViewClicked(view, null)
        }
    }

    override fun showMessage(title: String?, description: String?, action: String?, showBothButtons: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun populateView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance(viewServicesRequest: ViewServicesRequest): VoucherFragment {
            val bundle = Bundle()
            bundle.putParcelable(AppModuleConstants.VIEW_SERVICES_SELECTED_KEY,
                    viewServicesRequest)
            val voucherFragment = VoucherFragment()
            voucherFragment.arguments = bundle
            return voucherFragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_vouncher, container, false)
        mVoucherFragmentPresenter?.onCreateView(view)
        return view
    }

    override fun dissmissProgressBar() {
        mPBVoucher?.visibility = View.GONE
        mSVVoucherContainer?.visibility = View.VISIBLE
    }

    override fun showProgressBar() {
        mPBVoucher?.visibility = View.VISIBLE
        mSVVoucherContainer?.visibility = View.GONE
    }

    override fun showAppointmentPrice(price: Int?) {
        if (price !== null) {
            mTVPrice?.text = String.format(Locale.getDefault(),
                    "$%,d", price)
        }

    }

    override fun showAppointmentDate(date: String) {
        if (mTVDate !== null) {
            mTVDate?.text = date
        }
    }

    override fun showAppointmentTime(time: String) {
        if (mTVTime !== null) {
            mTVTime?.text = time
        }
    }

    override fun changeBTNNextAvailability(enabled: Boolean) {
        if (mBTNNextVoucher !== null) {
            mBTNNextVoucher!!.isEnabled = enabled
            mBTNNextVoucher!!.background = if (enabled)
                resources.getDrawable(R.drawable.main_button_rounded_background)
            else resources.getDrawable(R.drawable.main_button_disable_rounded_background)
        }
    }


    override fun showConfirmationAppointmentDialog(specialistUser: SpecialistUser) {
        val ft = fragmentManager.beginTransaction()
        val prev = fragmentManager
                .findFragmentByTag(AppModuleConstants.ALERT_DIALOG_TAG)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        val alertFragmentDialog = AppointmentConfirmationDialogFragment.newInstance(specialistUser)
        alertFragmentDialog.show(ft, AppModuleConstants.ALERT_DIALOG_TAG)
    }

    override fun showProgressDialog(){
        super.showProgressDialog()
    }

    override fun hideProgressDialog(){
        super.hideProgressDialog()

    }

}
