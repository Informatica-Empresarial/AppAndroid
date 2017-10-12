package co.com.lafemmeapp.lafemmeapp.presentation.voucher

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.com.lafemmeapp.core.domain.entities.ViewService
import co.com.lafemmeapp.lafemmeapp.R
import java.util.*

/**
 * Created by oscargallon on 5/17/17.
 * This class is the services adapter for the voucher or the service detail
 */
class ServicesVoucherRecyclerViewAdapter(serviceList: List<ViewService>)
    : RecyclerView.Adapter<ServiceVoucherItemViewHolder>() {

    private var mServiceList: List<ViewService> = serviceList


    override fun onBindViewHolder(holder: ServiceVoucherItemViewHolder, position: Int) {
        val service = mServiceList[position]

        holder.getTVServiceNameAndPrice().text = String.format(Locale.getDefault(),
                "%s (x%s) $ %,d",
                service.name, service.quantity, Integer.valueOf(service.price))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceVoucherItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.service_voucher_list_item, parent, false)

        return ServiceVoucherItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mServiceList.size
    }
}