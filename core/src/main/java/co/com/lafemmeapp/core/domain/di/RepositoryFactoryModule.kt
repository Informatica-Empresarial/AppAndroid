package co.com.lafemmeapp.core.domain.di

import android.content.Context
import co.com.lafemmeapp.core.domain.di.scopes.RepositoryFactoryScope
import co.com.lafemmeapp.dataprovider.repo.RepositoryFactory
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory
import dagger.Module
import dagger.Provides

/**
 * Created by oscargallon on 6/2/17.
 */

@RepositoryFactoryScope
@Module
class RepositoryFactoryModule(val mContext: Context) {

    @Provides
    fun provideRepositoryFactory(): IRepositoryFactory = RepositoryFactory(mContext)

    @Provides
    fun provideContext(): Context = mContext
}