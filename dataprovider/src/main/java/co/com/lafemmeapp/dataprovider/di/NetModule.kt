package com.co.giftlist.dataprovider.network.di

import co.com.lafemmeapp.dataprovider.Constants.DATE_FORMAT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by oscargallon on 6/2/17.
 * This Module Provides the Retrofit instance to
 * be used in the app
 */
@NetworkScope
@Module
class NetModule(val mBaseUrl: String) {


    @Provides
    fun provideGson(): com.google.gson.Gson =
            GsonBuilder()
                    .setDateFormat(DATE_FORMAT)
                    .setLenient()
                    .create()

    @Provides
    fun provideLoginInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.networkInterceptors().add(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    fun provideRetroFit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }


}