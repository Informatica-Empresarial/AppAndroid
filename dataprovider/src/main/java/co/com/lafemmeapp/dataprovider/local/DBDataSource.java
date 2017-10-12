package co.com.lafemmeapp.dataprovider.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import co.com.lafemmeapp.dataprovider.local.exceptions.DBErrorException;
import co.com.lafemmeapp.dataprovider.local.exceptions.DBNoCreatedException;

import static co.com.lafemmeapp.dataprovider.Constants.DB_NAME;

/**
 * Created by oscargallon on 4/18/17.
 */

public class DBDataSource implements IDBDataSource {

    private DB mLafemmeDB;

    private AppDatabase mAppDatabase;

    private final static DBDataSource instance = new DBDataSource();


    private DBDataSource() {

    }

    public static DBDataSource getInstance() {
        return instance;
    }

    @Override
    public void initDataSource(@NonNull Context context) throws DBErrorException {
        try {
            mLafemmeDB = DBFactory.open(context, DB_NAME);

        } catch (SnappydbException e) {
            e.printStackTrace();
            throw new DBErrorException(e);
        }
    }

    @Override
    public void put(@NonNull String key, @NonNull String object) throws
            DBNoCreatedException, DBErrorException {
        try {
            mLafemmeDB.put(key, object);
        } catch (NullPointerException e) {
            throw new DBNoCreatedException(e);
        } catch (SnappydbException e) {
            e.printStackTrace();
            throw new DBErrorException(e);
        }

    }

    @Override
    public boolean exist(@NonNull String key) throws DBErrorException, DBNoCreatedException {
        try {
            return mLafemmeDB.exists(key);
        } catch (NullPointerException e) {
            throw new DBNoCreatedException(e);
        } catch (SnappydbException e) {
            e.printStackTrace();
            throw new DBErrorException(e);
        }

    }

    @Override
    public Object get(@NonNull String key) throws DBErrorException,
            DBNoCreatedException {
        try {
            return mLafemmeDB.get(key);
        } catch (NullPointerException e) {
            throw new DBNoCreatedException(e);
        } catch (SnappydbException e) {
            e.printStackTrace();
            throw new DBErrorException(e);
        }
    }

    @Override
    public void remove(@NonNull String key) throws DBErrorException, DBNoCreatedException {
        try {
            mLafemmeDB.del(key);
        } catch (NullPointerException e) {
            throw new DBNoCreatedException(e);
        } catch (SnappydbException e) {
            e.printStackTrace();
            throw new DBErrorException(e);
        }
    }

    @Override
    public AppDatabase getDatabase() {
        return mAppDatabase;
    }

}
