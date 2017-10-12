package co.com.lafemmeapp.lafemmeapp.presentation.utils.input_fragment_dialog

import android.app.Activity
import android.app.DialogFragment
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.InputFragmentDialogParams
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks


/**
 * Created by oscargallon on 6/13/17.
 */
class InputDialogFragment : DialogFragment(), IInputDialogFragmentView {


    private var mTVMessage: TextView? = null

    private var mToolbar: Toolbar? = null

    private var mBTNPositive: Button? = null

    private var mBTNNegative: Button? = null

    private var mETInput: EditText? = null

    private var mInputDialogFragmentPresenter: IInputDialogFragmentPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        mInputDialogFragmentPresenter?.onCreate(arguments)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.input_dialog_fragment, container, false)
        mInputDialogFragmentPresenter?.onCreateView(view)
        return view

    }


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mInputDialogFragmentPresenter = InputDialogFragmentPresenter(this)
        mInputDialogFragmentPresenter?.onAttach(activity as IFragmentCallbacks)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mInputDialogFragmentPresenter = InputDialogFragmentPresenter(this)
        mInputDialogFragmentPresenter?.onAttach(context as IFragmentCallbacks)


    }

    override fun initViewComponents(view: View) {
        mToolbar = view.findViewById(R.id.toolbar) as Toolbar
        mBTNNegative = view.findViewById(R.id.btn_negative) as Button
        mBTNPositive = view.findViewById(R.id.btn_positive) as Button
        mTVMessage = view.findViewById(R.id.tv_message) as TextView
        mETInput = view.findViewById(R.id.et_input) as EditText
    }

    override fun initComponents() {
        mToolbar?.setTitleTextColor(Color.WHITE);
        mBTNNegative?.setOnClickListener(this)
        mBTNPositive?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        mInputDialogFragmentPresenter?.actionTriggered(v, v.id == R.id.btn_positive, if (mETInput !== null)
            mETInput!!.text.toString() else "")
        dismissAllowingStateLoss()
    }


    override fun setDialogTitle(text: String) {
        text.let {
            mToolbar?.title = text

        }

    }

    override fun setDialogMessage(text: String) {
        mTVMessage?.text = text
    }

    override fun setPositiveButtonText(text: String) {
        mBTNPositive?.text = text
    }

    override fun setNegativeButtonText(text: String) {
        mBTNNegative?.text = text
    }

    override fun hideNegativeButton() {
        mBTNNegative?.visibility = View.GONE
    }

    override fun setInputHint(text: String) {
        mETInput?.hint = text
    }

    override fun setInputFieldInputType(type: Int) {
        mETInput?.inputType = type
    }

    companion object {
        fun newInstance(inputFragmentDialogParams: InputFragmentDialogParams): InputDialogFragment {
            val fragment = InputDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable("params", inputFragmentDialogParams)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun showInputError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inputHasErrors(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeBTNPositiveAvailability(enabled: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun showMessage(title: String?, description: String?, action: String?, showBothButtons: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun populateView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}