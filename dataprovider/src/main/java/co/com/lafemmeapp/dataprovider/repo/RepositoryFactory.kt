package co.com.lafemmeapp.dataprovider.repo

import android.content.Context
import co.com.lafemmeapp.dataprovider.Constants
import co.com.lafemmeapp.dataprovider.di.DaggerRoutesComponent
import co.com.lafemmeapp.dataprovider.di.DatabaseModule

import co.com.lafemmeapp.dataprovider.di.RoutesModule
import co.com.lafemmeapp.dataprovider.repo.interfaces.*
import com.co.giftlist.dataprovider.network.di.DaggerNetComponent
import com.co.giftlist.dataprovider.network.di.NetModule
import retrofit2.Retrofit
import javax.inject.Inject


/**
 * Created by oscargallon on 5/8/17.
 */

class RepositoryFactory(mContext: Context) : IRepositoryFactory {


    @Inject
    lateinit var mRetrofit: Retrofit

    override val customerRepository: ICustomerRepository

    override val serviceRepository: IServicesRepository

    override val appointmentRepository: IAppointmentRepository

    override val iParamsRepository: IParamsRepository

    override val deviceRepository: IDeviceRepository

    override val sessionRepositoty: ISessionRepository

    override val valuesRepository: IValueRepository


    init {

        DaggerNetComponent.builder()
                .netModule(NetModule(Constants.BASE_URL))
                .build()
                .inject(this)

        customerRepository = CustomerRepository()

        serviceRepository = ServicesRepository()

        appointmentRepository = AppointmentRepository()
        iParamsRepository = ParamsRepository()

        deviceRepository = DeviceRepository()

        sessionRepositoty = SessionRepository()

        valuesRepository = ValueRepository()

        val routesComponent =
        DaggerRoutesComponent.builder()
                .routesModule(RoutesModule(mRetrofit))
                .databaseModule(DatabaseModule(mContext))
                .build()

        routesComponent.inject(sessionRepositoty)
        routesComponent.inject(valuesRepository)


    }


}
