package co.com.lafemmeapp.lafemmeapp.presentation.utils.select_city

import co.com.lafemmeapp.core.domain.entities.City
import co.com.lafemmeapp.utilmodule.presentation.view_interfaces.IBaseFragmentView

/**
 * Created by oscargallon on 7/10/17.
 */
interface ISelectCityDialogFragmentView : IBaseFragmentView {

    fun showCities(cities: List<City>)


}