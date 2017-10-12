package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_time

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.com.lafemmeapp.core.domain.entities.ViewTimeChoose
import co.com.lafemmeapp.core.domain.entities.abstracts.SpecialistTime
import co.com.lafemmeapp.lafemmeapp.R
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IFragmentCallbacks
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

/**
 * Created by oscargallon on 5/9/17.
 */
class SelectSpecialistTimeRecyclerViewAdapter(private val mTimeList: List<SpecialistTime>,
                                              context: Context,
                                              private val mFragmentCallbacks: IFragmentCallbacks?) : RecyclerView.Adapter<TimeItemViewHolder>() {

    private val mContextWeakReference: WeakReference<Context> = WeakReference(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.time_item_view, parent, false)
        return TimeItemViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: TimeItemViewHolder, position: Int) {
        val specialistTime = mTimeList[position]

        holder.getmCLContainer()
                .setBackgroundColor(mContextWeakReference.get()
                        ?.resources!!.getColor(R.color.white))

        holder.getmTVTime().text = specialistTime.time

        when (specialistTime.choosen) {
            true -> holder.getmCLContainer().setBackgroundColor(mContextWeakReference.get()
                    ?.resources!!.getColor(R.color.silver))

        }

        holder.mitemView.setOnClickListener { v ->
            mFragmentCallbacks?.onViewClicked(v, ViewTimeChoose(specialistTime.time))
            specialistTime.choosen = true
            refreshTimes(specialistTime.time)
        }
    }

    private fun refreshTimes(time: String) {
        Single.create(SingleOnSubscribe<List<SpecialistTime>> { e ->
            mTimeList
                    .filter { it.time != time }
                    .forEach { it.choosen = false }
            e.onSuccess(mTimeList)
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<SpecialistTime>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(value: List<SpecialistTime>) {
                        notifyDataSetChanged()

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })


    }

    override fun getItemCount(): Int {
        return mTimeList.size
    }
}
