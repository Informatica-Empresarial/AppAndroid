package co.com.lafemmeapp.lafemmeapp.di

import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication
import dagger.Component
import javax.inject.Singleton

/**
 * Created by oscargallon on 6/3/17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(laFemmeApplication: LaFemmeApplication)

}