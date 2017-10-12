package co.com.lafemmeapp.dataprovider.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import co.com.lafemmeapp.dataprovider.local.entities.DBSessionRessponse
import co.com.lafemmeapp.dataprovider.local.entities.DBValues

/**
 * Created by oscargallon on 6/2/17.
 */
@Database(entities = arrayOf(DBSessionRessponse::class, DBValues::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sessionModel(): DBSessionResponseDAO

    abstract fun valuesModel(): DBValuesDAO


}