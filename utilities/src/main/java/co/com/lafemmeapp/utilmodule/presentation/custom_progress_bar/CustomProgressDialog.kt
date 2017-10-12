package co.com.lafemmeapp.utilmodule.presentation.custom_progress_bar

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import co.com.lafemmeapp.utilmodule.R
import com.eyalbira.loadingdots.LoadingDots
import java.lang.ref.WeakReference

/**
 * Created by oscargallon on 5/25/17.
 */
class CustomProgressDialog(mContextWeakReference: WeakReference<Context>,
                           val mColor: Int) : ProgressDialog(mContextWeakReference.get()),
        ICustomProgressDialogView {


    var mLDLoading: LoadingDots? = null
    var mCustomProgressDialogPresenter: ICustomProgressDialogPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_progress_dialog)
        mCustomProgressDialogPresenter = CustomProgressDialogPresenter(this)
        mCustomProgressDialogPresenter?.onCreate(null)

    }

    override fun setColor() {
        mLDLoading?.dotsColor = mColor
    }

    override fun initViewComponents() {
        mLDLoading = findViewById(R.id.ld_loading) as LoadingDots
    }

    override fun show() {
        super.show()
        mLDLoading?.startAnimation()
    }

    override fun hide() {
        super.hide()
        mLDLoading?.stopAnimation()
    }

    override fun initComponents() {
        isIndeterminate = true
        setCancelable(false)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun showMessage(title: String?, description: String?, action: String?, showBothButtons: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun populateView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeActivity(arguments: Bundle?, destinationActivity: Class<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startActivityForResult(arguments: Bundle?, requestCode: Int, destinationActivity: Class<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}