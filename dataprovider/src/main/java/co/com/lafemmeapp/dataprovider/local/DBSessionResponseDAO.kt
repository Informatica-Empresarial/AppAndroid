package co.com.lafemmeapp.dataprovider.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import co.com.lafemmeapp.dataprovider.local.entities.DBSessionRessponse

/**
 * Created by oscargallon on 6/2/17.
 */
@Dao
interface DBSessionResponseDAO {

    @Query("Select * From session LIMIT 1")
    fun getSession(): DBSessionRessponse

    @Insert(onConflict = IGNORE)
    fun insertSession(dbSessionResponse: DBSessionRessponse)


    @Query("DELETE from Session")
    fun deleteSession()

    @Query("UPDATE session SET sos=:arg0 where uuid=:arg1")
    fun updateSOS(arg0: Boolean, arg1: String)

    @Query("UPDATE session SET phoneNumber=:arg0 where uuid=:arg1")
    fun updatePhoneNumber( arg0: String, arg1: String)

    @Query("UPDATE session SET email=:arg0 where uuid=:arg1")
    fun updateEmail( arg0: String, arg1: String)
}