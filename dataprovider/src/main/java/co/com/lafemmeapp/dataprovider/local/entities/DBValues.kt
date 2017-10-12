package co.com.lafemmeapp.dataprovider.local.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by oscargallon on 6/5/17.
 */
@Entity(tableName = "app_values")
data class DBValues(@PrimaryKey var key: String? = null,
                    @ColumnInfo(name = "value") var value: String? = null)