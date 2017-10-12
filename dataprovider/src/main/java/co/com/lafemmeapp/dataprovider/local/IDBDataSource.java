package co.com.lafemmeapp.dataprovider.local;

import android.content.Context;
import android.support.annotation.NonNull;

import co.com.lafemmeapp.dataprovider.local.exceptions.DBErrorException;
import co.com.lafemmeapp.dataprovider.local.exceptions.DBNoCreatedException;

/**
 * Created by oscargallon on 4/18/17.
 */

public interface IDBDataSource {

    void initDataSource(@NonNull Context context) throws DBErrorException;

    void put(@NonNull String key, @NonNull String object) throws DBNoCreatedException, DBErrorException;

    boolean exist(@NonNull String key) throws DBErrorException, DBNoCreatedException;

    Object get(@NonNull String key) throws DBErrorException, DBNoCreatedException;

    void remove(@NonNull String key) throws DBErrorException, DBNoCreatedException;

    AppDatabase getDatabase();


}
