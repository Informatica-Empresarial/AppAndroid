package co.com.lafemmeapp.lafemmeapp.di

import co.com.lafemmeapp.core.domain.use_cases.IUseCaseFactory
import co.com.lafemmeapp.core.domain.use_cases.UseCaseFactory
import co.com.lafemmeapp.lafemmeapp.application.LaFemmeApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by oscargallon on 6/3/17.
 */
@Singleton
@Module
class AppModule(val mLaFemmeApplication: LaFemmeApplication) {

    @Provides
    fun provideApplication(): LaFemmeApplication = mLaFemmeApplication

    @Provides
    fun provideUseCaseFactory(): IUseCaseFactory = UseCaseFactory(mLaFemmeApplication.applicationContext)

}