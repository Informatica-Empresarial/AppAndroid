package co.com.lafemmeapp.dataprovider.di

import co.com.lafemmeapp.dataprovider.di.scopes.RouteScope
import co.com.lafemmeapp.dataprovider.repo.SessionRepository
import co.com.lafemmeapp.dataprovider.repo.ValueRepository
import dagger.Component

/**
 * Created by oscargallon on 6/2/17.
 */
@RouteScope
@Component(modules = arrayOf(RoutesModule::class,DatabaseModule::class))
interface RoutesComponent {


    fun inject(sessionRepository: SessionRepository)

    fun inject(valuesRepository: ValueRepository)
}