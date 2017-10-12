package co.com.lafemmeapp.lafemmeapp.presentation.voucher

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import co.com.lafemmeapp.lafemmeapp.R

/**
 * Created by oscargallon on 5/17/17.
 */
class ServiceVoucherItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mTVServiceNameAndPrice : TextView = itemView.findViewById(R.id.tv_service_name_and_price) as TextView


    fun getTVServiceNameAndPrice():TextView{
        return mTVServiceNameAndPrice
    }



}