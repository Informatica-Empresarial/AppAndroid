package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_time

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import co.com.lafemmeapp.lafemmeapp.R

/**
 * Created by oscargallon on 5/9/17.
 */

class TimeItemViewHolder(val mitemView: View) : RecyclerView.ViewHolder(mitemView) {

    private val mTVTime: TextView = mitemView.findViewById(R.id.tv_time) as TextView

    private val mCLContainer: ConstraintLayout = itemView.findViewById(R.id.cl_container_time) as ConstraintLayout


    fun getmTVTime(): TextView {
        return mTVTime
    }

    fun getmCLContainer(): ConstraintLayout {
        return mCLContainer
    }

}
