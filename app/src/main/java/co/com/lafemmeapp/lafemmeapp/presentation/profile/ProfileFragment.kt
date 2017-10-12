package co.com.lafemmeapp.lafemmeapp.presentation.profile


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.mappers.EmailTextViewValidatorMapper
import co.com.lafemmeapp.lafemmeapp.mappers.PhoneNumberValidatorMapper
import co.com.lafemmeapp.lafemmeapp.presentation.BaseFragment
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.squareup.picasso.Picasso


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : BaseFragment(), IProfileFragmentView {

    private var mProfileFragmentPresenter: IProfileFragmentPresenter? = null

    private var mTVName: TextView? = null

    private var mTVSpecialistName: TextView? = null

    private var mTVPhone: EditText? = null

    private var mTVCity: TextView? = null

    private var mTVEmail: EditText? = null

    private var mIVAvatar: ImageView? = null

    private var mTVAddres: TextView? = null

    private var mIVEditEmail: ImageView? = null

    private var mIVEditPhone: ImageView? = null

    private var mIVSeparatorAddres: ImageView? = null

    private var mIVSeparatorCity: ImageView? = null

    private var mBTNSave: Button? = null

    private var mBTNLogout: Button? = null

    private var mTVTerms: TextView? = null

    private var stateImageIVEditPhone: Int = 1

    private var stateImageIVEditEmail: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProfileFragmentPresenter?.onCreate(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_profile, container, false)
        mProfileFragmentPresenter?.onCreateView(view)
        return view
    }

    override fun initViewComponents(view: View) {
        mTVName = view.findViewById(R.id.tv_name) as TextView
        mTVEmail = view.findViewById(R.id.tv_email) as EditText
        mTVCity = view.findViewById(R.id.tv_city) as TextView
        mTVPhone = view.findViewById(R.id.tv_phone) as EditText
        mTVAddres = view.findViewById(R.id.tv_address) as TextView
        mIVEditEmail = view.findViewById(R.id.iv_edit_email) as ImageView
        mIVEditPhone = view.findViewById(R.id.iv_edit_phone) as ImageView
        mIVSeparatorAddres = view.findViewById(R.id.iv_separator_address) as ImageView
        mIVSeparatorCity = view.findViewById(R.id.iv_separator_city) as ImageView
        mIVAvatar = view.findViewById(R.id.iv_avatar) as ImageView
        mBTNLogout = view.findViewById(R.id.btn_logout) as Button
        mBTNSave = view.findViewById(R.id.btn_save) as Button
        mTVSpecialistName = view.findViewById(R.id.tv_specialist_name) as TextView
        mTVTerms = view.findViewById(R.id.tv_terms) as TextView
    }

    override fun initComponents() {

        if (mProfileFragmentPresenter != null) {

            mProfileFragmentPresenter!!.subscribeTextViewToTextWatcherEvent(mTVEmail!!,
                    EmailTextViewValidatorMapper.getInstance())
            mProfileFragmentPresenter!!.subscribeTextViewToTextWatcherEvent(mTVPhone!!,
                    PhoneNumberValidatorMapper.getInstance())
        }

        mTVTerms?.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(getString(R.string.tos_url))
            startActivity(i)
        }

        mBTNSave?.setOnClickListener {
            mProfileFragmentPresenter?.updateUser(mTVEmail?.text.toString(),
                    mTVPhone?.text.toString())

        }
    }

    override fun showMessage(title: String?, description: String?, action: String?, showBothButtons: Boolean) {

    }

    override fun populateView() {

        mIVEditEmail?.setOnClickListener {
            if (mTVEmail != null) {
                if (mProfileFragmentPresenter != null) {
                    stateImageIVEditEmail = checkImageResource(mIVEditEmail, mTVEmail,
                            stateImageIVEditEmail, R.drawable.ic_edit, R.drawable.ic_cancel,
                            mProfileFragmentPresenter?.getUserInfo()!!.email)
                    enableSaveBTN()
                }
            }
        }

        mIVEditPhone?.setOnClickListener({
            if (mTVPhone != null) {
                if (mProfileFragmentPresenter != null) {
                    stateImageIVEditPhone = checkImageResource(mIVEditPhone, mTVPhone, stateImageIVEditPhone,
                            R.drawable.ic_edit, R.drawable.ic_cancel, mProfileFragmentPresenter?.getUserInfo()!!.phoneNumber)
                    enableSaveBTN()
                }
            }
        })

        mBTNLogout?.setOnClickListener { v ->
            mProfileFragmentPresenter?.onViewClicked(v, null)

        }
    }

    fun enableSaveBTN() {
        if (!mTVEmail?.isEnabled!! && !mTVPhone?.isEnabled!!) {
            mBTNSave?.visibility = View.GONE
        } else {
            mBTNSave?.visibility = View.VISIBLE
        }
    }

    fun checkImageResource(imageView: ImageView?, inputText: EditText?, state: Int, firstResource: Int,
                           secondResource: Int, previousTxt: String): Int {
        if (state == 1) {
            imageView?.setImageResource(secondResource)
            inputText?.isEnabled = true
            return 2
        } else {
            imageView?.setImageResource(firstResource)
            inputText?.isEnabled = false
            inputText?.setText(previousTxt)
            return 1
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mProfileFragmentPresenter = ProfileFragmentPresenter(this)
        mProfileFragmentPresenter?.onAttach(activity as IFragmentCallbacks)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mProfileFragmentPresenter = ProfileFragmentPresenter(this)
        mProfileFragmentPresenter?.onAttach(context as IFragmentCallbacks)
    }

    override fun onDetach() {
        super.onDetach()
    }


    companion object {
        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            return fragment
        }
    }


    override fun setName(haveToShow: Boolean, name: String?) {
        if (haveToShow) {
            if (name !== null && mTVName !== null) {
                mTVName?.text = name
                mTVName?.visibility = View.VISIBLE
                mTVSpecialistName?.visibility = View.GONE
            }
        } else {
            mTVName?.visibility = View.GONE
        }

    }

    override fun setEmail(haveToShow: Boolean, email: String?) {
        if (haveToShow) {
            if (email !== null && mTVEmail !== null) {
                mTVEmail?.setText(email)
                mTVEmail?.visibility = View.VISIBLE
            }
        } else {
            mTVEmail?.visibility = View.GONE
        }
    }

    override fun setPhone(haveToShow: Boolean, phone: String?) {
        if (haveToShow) {
            if (phone !== null && mTVPhone !== null) {
                mTVPhone?.setText(phone)
                mTVPhone?.visibility = View.VISIBLE
            }
        } else {
            mTVPhone?.visibility = View.GONE
        }
    }

    override fun setCity(haveToShow: Boolean, city: String?) {
        if (haveToShow) {
            if (city !== null && mTVCity !== null) {
                mTVCity?.text = city
                mTVCity?.visibility = View.VISIBLE
                mIVSeparatorAddres?.visibility = View.VISIBLE
            }
        } else {
            mTVCity?.visibility = View.GONE
            mIVSeparatorAddres?.visibility = View.GONE
        }
    }

    override fun setAddress(haveToShow: Boolean, address: String?) {
        if (haveToShow) {
            if (address !== null && mTVAddres !== null) {
                mTVAddres?.text = address
                mTVAddres?.visibility = View.VISIBLE
            }
        } else {
            mTVAddres?.visibility = View.GONE
        }
    }

    override fun setAvatar(haveToShow: Boolean, avatar: String?) {
        if (haveToShow) {
            if (avatar !== null && mIVAvatar !== null) {
                Picasso.with(activity)
                        .load(avatar)
                        .into(mIVAvatar)
                mIVAvatar?.visibility = View.VISIBLE
            }
        } else {
            mIVAvatar?.visibility = View.GONE
        }
    }

    override fun showEmailError(s: String?) {
        if (mTVEmail != null) {
            mTVEmail!!.error = s
        }
    }

    override fun showPhoneError(s: String?) {
        if (mTVPhone != null) {
            mTVPhone!!.error = s
        }
    }

    override fun changeBTNSaveAvailability(disable: Boolean) {

        mBTNSave.let {
            mBTNSave!!.isEnabled = disable
        }
    }

    override fun setSpecialistName(haveToShow: Boolean, name: String?) {
        if (haveToShow) {
            if (name !== null && mTVSpecialistName !== null) {
                mTVSpecialistName?.text = name
                mTVSpecialistName?.visibility = View.VISIBLE
                mTVName?.visibility = View.GONE
            }
        } else {
            mTVSpecialistName?.visibility = View.GONE
        }

    }

    override fun showTerms(haveToShow: Boolean) {
        if (haveToShow) {
            if (mTVTerms !== null) {
                mTVTerms?.visibility = View.VISIBLE
                mIVSeparatorCity?.visibility = View.VISIBLE
            }
        } else {
            mTVTerms?.visibility = View.GONE
            mIVSeparatorCity?.visibility = View.GONE
        }
    }


    override fun showProgressDialog() {
        super.showProgressDialog()
    }

    override fun hideProgressDialog() {
        super.hideProgressDialog()
    }
}
