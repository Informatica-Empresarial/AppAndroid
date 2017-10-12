package co.com.lafemmeapp.dataprovider.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.network.api_routes.ICustomerAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oscargallon on 4/8/17.
 */

public class APIProvider {




    /**
     * The Singleton class instance
     */
    private static APIProvider ourInstance = new APIProvider();

    /**
     * A method to get the class instance
     * @return the {@link APIProvider} instance
     */
    public static APIProvider getInstance() {
        return ourInstance;
    }

    private Retrofit retrofit;

    private APIProvider() {
        initAPI();
    }



    private void initAPI() {

        /**
         * We need a library to parse the data returned by the API into our
         * Java Models (DTOs)
         */
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();
        /**
         * We create an object to define the connection parameters
         */
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        /**
         * We specify the time outs for the connections to the API
         */
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        /**
         * We create an Object to log the services calls
         */
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        /**
         * We specify the level of the logs that we want
         */
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /**
         * We add the logs to our client
         */
        builder.networkInterceptors().add(httpLoggingInterceptor);


        /**
         * We create the object that will point to our base url,
         * we pass the object that have the connection properties
         * in the client method, also we pass the Gson library that will parse
         * the json data into java models
         */
       retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
