package co.com.lafemmeapp.lafemmeapp.presentation.history

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import co.com.lafemmeapp.lafemmeapp.R

/**
 * Created by oscargallon on 5/22/17.
 */
class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val mTVDate = itemView.findViewById(R.id.tv_date) as TextView

    val mTVTime = itemView.findViewById(R.id.tv_time) as TextView

    val mIVLeftToken = itemView.findViewById(R.id.iv_left_token) as ImageView

    val mIVShowAppoitmentDetails = itemView.findViewById(R.id.iv_show_appoitments_detail)
            as ImageView


}