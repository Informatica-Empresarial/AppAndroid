package co.com.lafemmeapp.lafemmeapp.presentation


import android.app.Fragment
import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.utilmodule.presentation.custom_progress_bar.CustomProgressDialog
import java.lang.ref.WeakReference
import android.widget.Toast
import android.view.Gravity





/**
 * A simple [Fragment] subclass.
 * Use the [BaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BaseFragment : Fragment() {

    private var mProgressDialog: CustomProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_base, container, false)
    }


    companion object {

        fun newInstance(): BaseFragment {


            return BaseFragment()
        }
    }

    open fun showProgressDialog() {
        if (mProgressDialog === null) {
            mProgressDialog = CustomProgressDialog(WeakReference(activity),
                    resources.getColor(R.color.colorPrimary))

        }
        mProgressDialog?.show()
    }

    open fun hideProgressDialog() {
        mProgressDialog?.hide()
    }

    open fun hideKeyboard(view: View?, context: Context): Boolean {
        view?.let {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(view.windowToken,
                    0)
        }
        return false
    }



}
