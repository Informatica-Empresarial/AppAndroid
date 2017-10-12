package co.com.lafemmeapp.lafemmeapp.presentation.utils.select_city

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.com.lafemmeapp.core.domain.entities.City
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks

/**
 * Created by oscargallon on 7/10/17.
 */
class SelectCityRecyclerViewAdapter(val mCitiesList: List<City>,
                                    val mFragmentCallback: IFragmentCallbacks) : RecyclerView.Adapter<SelectCityViewHolder>() {


    override fun onBindViewHolder(holder: SelectCityViewHolder, position: Int) {
        holder.mTVCityName.text = mCitiesList[position].name
        holder.mItemView.setOnClickListener { v ->
            mFragmentCallback.onViewClicked(v, mCitiesList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SelectCityViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.city_item, parent, false)
        return SelectCityViewHolder(view)

    }

    override fun getItemCount(): Int =
            mCitiesList.size

}