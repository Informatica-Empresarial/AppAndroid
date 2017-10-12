package co.com.lafemmeapp.lafemmeapp.presentation.voucher

import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentPresenter
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks

/**
 * Created by oscargallon on 5/17/17.
 */
interface IVoucherFragmentPresenter : IBaseFragmentPresenter, IFragmentCallbacks {

       var mVoucherFragmentView:IVoucherFragmentView

}