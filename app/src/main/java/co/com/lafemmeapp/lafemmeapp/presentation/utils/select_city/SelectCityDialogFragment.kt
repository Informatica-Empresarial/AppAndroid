package co.com.lafemmeapp.lafemmeapp.presentation.utils.select_city


import android.app.Activity
import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.com.lafemmeapp.core.domain.entities.City

import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import kotlinx.android.synthetic.main.fragment_select_city.*


/**
 * A simple [Fragment] subclass.
 * Use the [SelectCityDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectCityDialogFragment : Fragment(), ISelectCityDialogFragmentView {



    private var mSelectCityDialogFragmentPresenter: ISelectCityDialogFragmentPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSelectCityDialogFragmentPresenter?.onCreate(arguments)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mSelectCityDialogFragmentPresenter = SelectCityDialogFragmentPresenter(this)
        mSelectCityDialogFragmentPresenter?.onAttach(activity as IFragmentCallbacks)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_city, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSelectCityDialogFragmentPresenter?.onCreateView(view)
    }

    override fun initViewComponents(view: View) {

    }

    override fun showCities(cities: List<City>) {
        mSelectCityDialogFragmentPresenter.let {
            mRVCities.adapter = SelectCityRecyclerViewAdapter(cities, mSelectCityDialogFragmentPresenter!!)
        }

    }

    override fun initComponents() {
        with(mRVCities) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun showMessage(title: String?, description: String?, action: String?, showBothButtons: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun populateView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressDialog() {
        mPBCities.visibility = View.VISIBLE
        mCLSelectCities.visibility = View.INVISIBLE
    }

    override fun hideProgressDialog() {
        mPBCities.visibility = View.GONE
        mCLSelectCities.visibility = View.VISIBLE
    }



    companion object {

        fun newInstance(): SelectCityDialogFragment {
            val fragment = SelectCityDialogFragment()
            return fragment
        }
    }

}// Required empty public constructor
