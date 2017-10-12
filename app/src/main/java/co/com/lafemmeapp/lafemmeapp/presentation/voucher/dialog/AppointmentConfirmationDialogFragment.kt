package co.com.lafemmeapp.lafemmeapp.presentation.voucher.dialog


import android.app.Activity
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.presentation.utils.BitmapTransform
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso


/**
 * A simple [Fragment] subclass.
 */
class AppointmentConfirmationDialogFragment : DialogFragment(), IAppointmentConfirmationDialogFragmentView {


    private var mToolbar: Toolbar? = null
    private var mTVSpecialistName: TextView? = null
    private var mTVSpecialistPhoneNumber: TextView? = null
    private var mBTNAcceptAppointmentConfirmation: Button? = null
    private var mIVAvatar: CircularImageView? = null
    private var mAppointmentConfirmationDialogPresenter: IAppointmentConfirmationDialogFragmentPresenter? = null


    companion object {
        fun newInstance(specialistUser: SpecialistUser): AppointmentConfirmationDialogFragment {
            val appointmentConfirmationDialogFragment = AppointmentConfirmationDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(AppModuleConstants.SPECIALIST_USER, specialistUser)
            appointmentConfirmationDialogFragment.arguments = bundle
            return appointmentConfirmationDialogFragment

        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mAppointmentConfirmationDialogPresenter = AppointmentConfirmationDialogFragmentPresenter(this)
        mAppointmentConfirmationDialogPresenter?.onAttach(activity as IFragmentCallbacks)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mAppointmentConfirmationDialogPresenter = AppointmentConfirmationDialogFragmentPresenter(this)
        mAppointmentConfirmationDialogPresenter?.onAttach(context as IFragmentCallbacks)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mAppointmentConfirmationDialogPresenter !== null) {
            mAppointmentConfirmationDialogPresenter!!.onCreate(arguments)
        }
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_appointment_confirmation_dialog, container, false)
        if (mAppointmentConfirmationDialogPresenter !== null) {
            mAppointmentConfirmationDialogPresenter!!.onCreateView(view)
        }
        return view
    }


    override fun initViewComponents(view: View) {
        mTVSpecialistName = view.findViewById(R.id.tv_specialist_name) as TextView
        mTVSpecialistPhoneNumber = view.findViewById(R.id.tv_specialist_phone_number) as TextView
        mBTNAcceptAppointmentConfirmation = view.findViewById(R.id.btn_accept_appointment_confirmation) as Button
        mIVAvatar = view.findViewById(R.id.iv_avatar) as CircularImageView
        mToolbar = view.findViewById(R.id.toolbar) as Toolbar

    }

    override fun initComponents() {

        mToolbar!!.title = getString(R.string.thanks_for_choose_la_femme_label)
        mToolbar!!.setTitleTextColor(Color.WHITE)
        mBTNAcceptAppointmentConfirmation?.setOnClickListener({ view ->
            dismissAllowingStateLoss()
            mAppointmentConfirmationDialogPresenter?.onViewClicked(view, null)
        })
    }


    override fun showSpecialistName(name: String) {
        if (mTVSpecialistName !== null) {
            mTVSpecialistName!!.text = name
        }
    }

    override fun showSpecialistPhoneNumber(phoneNumber: String) {
        if (mTVSpecialistPhoneNumber !== null) {
            mTVSpecialistPhoneNumber!!.text = phoneNumber
        }
    }

    override fun showSpecialistAvatar(avatarUrl: String) {
        if (mIVAvatar !== null) {
            Picasso.with(activity)
                    .load(avatarUrl)
                    .transform(BitmapTransform(200, 200))
                    .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                    .resize(200, 200)
                    .into(mIVAvatar)
        }
    }

    override fun showProgressDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(title: String?, description: String?, action: String?, showBothButtons: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun populateView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}// Required empty public constructor
