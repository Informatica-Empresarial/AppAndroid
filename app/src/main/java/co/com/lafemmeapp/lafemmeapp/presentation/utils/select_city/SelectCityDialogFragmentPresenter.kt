package co.com.lafemmeapp.lafemmeapp.presentation.utils.select_city

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.City
import co.com.lafemmeapp.lafemmeapp.AppModuleConstants
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by oscargallon on 7/10/17.
 */
class SelectCityDialogFragmentPresenter(val mSelectCityDialogFragmentView: ISelectCityDialogFragmentView) : ISelectCityDialogFragmentPresenter {


    private var mFragmentCallbacks: IFragmentCallbacks? = null

    override fun onCreateView(view: View) {
        mSelectCityDialogFragmentView.initViewComponents(view)
        mSelectCityDialogFragmentView.initComponents()
       getCities()

    }


    fun getCities() {
        mSelectCityDialogFragmentView.showProgressDialog()
        val iterator = LaFemmeApplication.getInstance()
                .getmUseCaseFactory().getSelectableCitiesUseCase(Schedulers.io(),
                AndroidSchedulers.mainThread())

        iterator.execute(object : DisposableObserver<List<City>>() {
            override fun onError(e: Throwable) {
                e.printStackTrace()
                mSelectCityDialogFragmentView.hideProgressDialog()
            }

            override fun onComplete() {

            }

            override fun onNext(cities: List<City>) {
                mSelectCityDialogFragmentView.showCities(cities)
                mSelectCityDialogFragmentView.hideProgressDialog()
            }

        }, null)


    }

    override fun onAttach(fragmentCallback: IFragmentCallbacks) {
        mFragmentCallbacks = fragmentCallback
    }

    override fun onCreate(arguments: Bundle?) {

    }

    override fun onViewClicked(view: View?, parcelable: Parcelable?) {
        mFragmentCallbacks?.onViewClicked(view, parcelable)
    }


    override fun onDestroy() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun subscribeTextViewToTextWatcherEvent(textView: TextView, validatorMapper: ITextViewValidatorMapper) {
        TODO("not implemented") //To  body of created functions use File | Settings | File Templates.
    }

    override fun onTextChangeMappedEvent(textChangedMappedEvent: TextChangedMappedEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}