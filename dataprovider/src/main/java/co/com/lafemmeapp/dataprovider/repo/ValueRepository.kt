package co.com.lafemmeapp.dataprovider.repo

import co.com.lafemmeapp.dataprovider.local.AppDatabase
import co.com.lafemmeapp.dataprovider.local.entities.DBValues
import co.com.lafemmeapp.dataprovider.repo.interfaces.IValueRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by oscargallon on 6/5/17.
 */
class ValueRepository : IValueRepository {


    @Inject
    lateinit var mDatabase: AppDatabase

    override fun saveValue(key: String, value: String) {
        mDatabase
                .valuesModel()
                .insert(DBValues(key, value))
    }

    override fun getValue(key: String): Observable<String> {
        return Observable.create { emitter ->
            emitter.onNext(mDatabase.valuesModel()
                    .getValue(key))
            emitter.onComplete()
        }
    }

    override fun deleteKey(key: String) {
        mDatabase.valuesModel().delete(key)
    }
}