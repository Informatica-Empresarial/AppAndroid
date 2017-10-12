package co.com.lafemmeapp.dataprovider.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import co.com.lafemmeapp.dataprovider.local.entities.DBValues

/**
 * Created by oscargallon on 6/5/17.
 */
@Dao
interface DBValuesDAO {

    @Query("Select value from app_values where key=:arg0")
    fun getValue(arg0: String): String

    @Insert(onConflict = REPLACE)
    fun insert(dbVaDBValues: DBValues)

    @Query("Delete from app_values where key=:arg0")
    fun delete(arg0: String)
}