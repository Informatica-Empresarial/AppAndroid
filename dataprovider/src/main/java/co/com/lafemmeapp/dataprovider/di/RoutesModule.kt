package co.com.lafemmeapp.dataprovider.di

import co.com.lafemmeapp.dataprovider.di.scopes.RouteScope
import co.com.lafemmeapp.dataprovider.network.api_routes.ISessionAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by oscargallon on 6/2/17.
 */
@RouteScope
@Module
class RoutesModule(val mRetrofit: Retrofit) {

    @Provides
    fun provideISessionAPI(): ISessionAPI = mRetrofit.create(ISessionAPI::class.java)

    @Provides
    fun provideRetrofit(): Retrofit = mRetrofit
}