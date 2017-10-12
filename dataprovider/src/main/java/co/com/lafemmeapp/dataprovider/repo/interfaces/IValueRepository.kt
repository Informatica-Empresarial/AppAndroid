package co.com.lafemmeapp.dataprovider.repo.interfaces

import io.reactivex.Observable

/**
 * Created by oscargallon on 6/5/17.
 */
interface IValueRepository {


    fun saveValue(key: String, value: String)

    fun getValue(key: String): Observable<String>

    fun deleteKey(key:String)
}