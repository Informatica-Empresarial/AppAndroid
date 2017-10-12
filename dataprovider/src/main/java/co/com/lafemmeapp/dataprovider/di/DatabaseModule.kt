package co.com.lafemmeapp.dataprovider.di

import android.arch.persistence.room.Room
import android.content.Context
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.di.scopes.RouteScope
import co.com.lafemmeapp.dataprovider.local.AppDatabase
import dagger.Module
import dagger.Provides

/**
 * Created by oscargallon on 6/2/17.
 */
@RouteScope
@Module
class DatabaseModule(val mContext: Context) {


    @Provides
    fun provideDatabase(): AppDatabase = Room.databaseBuilder(mContext, AppDatabase::class.java,
            Constants.DB_NAME).build()

    @Provides
    fun provideContext(): Context = mContext

}