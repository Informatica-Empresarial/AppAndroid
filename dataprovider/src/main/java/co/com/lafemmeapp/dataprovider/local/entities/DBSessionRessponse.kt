package co.com.lafemmeapp.dataprovider.local.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by oscargallon on 6/2/17.
 */
@Entity(tableName = "session")
data class DBSessionRessponse(var name: String? = null,
                              var lastName: String? = null,
                              var address: String? = null,
                              var email: String? = null,
                              var phoneNumber: String? = null,
                              @PrimaryKey var uuid: String? = null,
                              var avatar: String? = null,
                              var token: String? = null,
                              var role: String? = null,
                              var city: String? = null,
                              @ColumnInfo(name = "sos") var SOS: Boolean? = false)