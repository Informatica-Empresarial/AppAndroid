package co.com.lafemmeapp.dataprovider.local;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.local.exceptions.DBErrorException;
import co.com.lafemmeapp.dataprovider.local.exceptions.DBNoCreatedException;
import co.com.lafemmeapp.dataprovider.network.entities.APICity;

/**
 * Created by oscargallon on 5/13/17.
 */

public class LocalUtils {

    public static List<APICity> getCitiesFromDisk(Resources resources)
            throws Exception {
        StringBuilder builder =
                new StringBuilder();
        InputStream in = resources.openRawResource(co.com.lafemmeapp.dataprovider.R.raw.cities);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        Type listType =
                new TypeToken<ArrayList<APICity>>() {
                }.getType();

        return new Gson().fromJson(builder.toString(), listType);

    }

    public static List<APICity> getCitiesFromDB() throws DBErrorException, DBNoCreatedException {
        Type listType =
                new TypeToken<ArrayList<APICity>>() {
                }.getType();

        return new Gson().fromJson(DBDataSource.getInstance()
                .get(Constants.DBCITY).toString(), listType);
    }
}
