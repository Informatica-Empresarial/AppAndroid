package co.com.lafemmeapp.lafemmeapp.presentation.select_specialist_time

import co.com.lafemmeapp.core.domain.entities.DatePickerMinMaxDate
import co.com.lafemmeapp.core.domain.entities.abstracts.SpecialistTime
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView

/**
 * Created by oscargallon on 5/9/17.
 */

interface ISelectSpecialistTimeFragmentView : IBaseFragmentView {

    fun showSpecialistUserAvailableTimeForADate(specialistTimeList: List<SpecialistTime>)

    fun changeBTNNextAvailability(enabled: Boolean)

    fun showSpecialistName(name: String)

    fun showSpecialistAditionalInfo(aditionalInfo: String)

    fun showDatePicker(datePickerMinMaxDate: DatePickerMinMaxDate)

    fun showDate(date: String)

    fun getDateShown(): String?

    fun showSpecialistAvatar(avatarUrl: String)


}
