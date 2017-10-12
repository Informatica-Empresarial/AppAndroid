package co.com.lafemmeapp.lafemmeapp.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import co.com.lafemmeapp.core.domain.entities.Customer
import co.com.lafemmeapp.core.domain.entities.SpecialistUser
import co.com.lafemmeapp.core.domain.entities.abstracts.User
import co.com.lafemmeapp.core.domain.use_cases.IUseCaseIterator
import co.com.lafemmeapp.dataprovider.exceptions.NoSessionException
import co.com.lafemmeapp.dataprovider.params.EditProfileParams
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication
import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlin.collections.HashMap

/**
 * Created by oscar.gallon on 6/7/17.
 */
class ProfileFragmentPresenter(override var mProfileFragmentView: IProfileFragmentView) : IProfileFragmentPresenter {


    private var mFragmentCallback: IFragmentCallbacks? = null

    private val mTextViewValidatorHashMap: HashMap<Int, Boolean> = HashMap()

    private var mFieldsValidatorObservable: Observable<Boolean>? = null

    private var mCompositeDisposable: CompositeDisposable? = null

    private var mUserInfo: User? = null

    override fun onCreateView(view: View?) {
        mProfileFragmentView.initViewComponents(view)
        mProfileFragmentView.initComponents()
        mProfileFragmentView.populateView()
        getUserProfile(true)
        mFieldsValidatorObservable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
            if (mTextViewValidatorHashMap.size > 0) {
                for (entry in mTextViewValidatorHashMap
                        .entries) {
                    if (!entry.value) {
                        emitter.onNext(false)
                        emitter.onComplete()
                        return@ObservableOnSubscribe
                    }
                }
                emitter.onNext(true)
                emitter.onComplete()

            } else {
                emitter.onNext(false)
                emitter.onComplete()
            }
        })
    }

    override fun onViewClicked(view: View?, parcelable: Parcelable?) {
        mFragmentCallback?.onViewClicked(view, parcelable)
    }

    override fun onAttach(fragmentCallback: IFragmentCallbacks?) {
        mFragmentCallback = fragmentCallback
    }

    override fun onCreate(arguments: Bundle?) {
        mCompositeDisposable = CompositeDisposable()
        if (arguments !== null) {
            // mAppointment = arguments.getParcelable(AppModuleConstants.VIEW_HISTORY_DETAIL)
        }
    }

    fun getUserProfile(initView: Boolean) {
        checkSession(object : DisposableObserver<User>() {
            override fun onNext(value: User?) {
                if (mProfileFragmentView != null) {
                    if (value != null) {
                        mUserInfo = value
                        mProfileFragmentView.setEmail(true, value.email)
                        mProfileFragmentView.setPhone(true, value.phoneNumber)
                    }
                    if (value is SpecialistUser) {
                        setSpecialistProfile(value)
                    } else {
                        setUserProfile(value as Customer)
                    }
                }

            }

            override fun onError(e: Throwable) {
                if (e is NoSessionException) {
                    if (mProfileFragmentView != null) {

                    }
                }
            }

            override fun onComplete() {

            }
        })
    }

    private fun checkSession(customerDisposableObserver: DisposableObserver<User>) {
        val iterator = LaFemmeApplication
                .getInstance()
                .getmUseCaseFactory()
                .getSessionUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread())

        iterator.execute(customerDisposableObserver, null)
    }

    private fun setSpecialistProfile(specialistUser: SpecialistUser) {
        if (mProfileFragmentView != null && specialistUser != null) {
            mProfileFragmentView.setSpecialistName(true, specialistUser.name + " " + specialistUser.lastName)
            mProfileFragmentView.setAddress(false, "")
            mProfileFragmentView.setCity(false, "")
            mProfileFragmentView.setAvatar(true, specialistUser.avatar)
            mProfileFragmentView.showTerms(false)
        }
    }

    private fun setUserProfile(user: Customer) {
        if (mProfileFragmentView != null && user != null) {
            mProfileFragmentView.setName(true, user.name + " " + user.lastName)
            mProfileFragmentView.setCity(true, user.city)
            mProfileFragmentView.setAddress(true, user.address)
            mProfileFragmentView.showTerms(true)
            mProfileFragmentView.setAvatar(false, "")
        }
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChangeMappedEvent(textChangedMappedEvent: TextChangedMappedEvent?) {
        if (textChangedMappedEvent === null) {
            return
        }

        mTextViewValidatorHashMap.put(textChangedMappedEvent.sourceId,
                textChangedMappedEvent.isValid)

        when (textChangedMappedEvent.sourceId) {

            R.id.tv_phone -> {
                mProfileFragmentView.showPhoneError(if (textChangedMappedEvent.isValid)
                    null
                else
                    "Por favor ingresa una telefono valido")
            }
            R.id.tv_email -> {
                mProfileFragmentView.showEmailError(if (textChangedMappedEvent.isValid)
                    null
                else
                    "Por favor ingresa un email valido")
            }

        }
        if (mFieldsValidatorObservable != null) {
            mFieldsValidatorObservable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { aBoolean ->
                        if (mProfileFragmentView !== null) {
                            mProfileFragmentView.changeBTNSaveAvailability(aBoolean!!)
                        }
                    }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subscribeTextViewToTextWatcherEvent(textView: TextView,
                                                     validatorMapper: ITextViewValidatorMapper) {

        if (mCompositeDisposable !== null && mTextViewValidatorHashMap !== null) {
            mTextViewValidatorHashMap.put(textView.id, false)
            mCompositeDisposable?.add(RxTextView.textChangeEvents(textView)
                    .skipInitialValue()
                    .distinctUntilChanged()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(validatorMapper)
                    .subscribe { textChangedMappedEvent -> onTextChangeMappedEvent(textChangedMappedEvent) })


        }


    }

    override fun getUserInfo(): User? {
        return mUserInfo
    }

    override fun updateUser(email: String, phoneNumber: String) {

        mProfileFragmentView.showProgressDialog()
        val iterator: IUseCaseIterator<User, EditProfileParams>
                = LaFemmeApplication.getInstance()
                .getmUseCaseFactory()
                .updateUserUseCase(Schedulers.computation(),
                        AndroidSchedulers.mainThread())

        iterator.execute(object : DisposableObserver<User>() {
            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                mProfileFragmentView.hideProgressDialog()
                iterator.dispose()
            }

            override fun onComplete() {

            }

            override fun onNext(value: User) {
                if (mProfileFragmentView != null) {
                    if (value != null) {
                        mUserInfo = value
                        mProfileFragmentView.setEmail(true, value.email)
                        mProfileFragmentView.setPhone(true, value.phoneNumber)
                    }
                    if (value is SpecialistUser) {
                        setSpecialistProfile(value)
                    } else {
                        setUserProfile(value as Customer)
                    }
                }
                mProfileFragmentView.hideProgressDialog()
                iterator.dispose()
            }

        }, EditProfileParams(email, phoneNumber));
    }


}