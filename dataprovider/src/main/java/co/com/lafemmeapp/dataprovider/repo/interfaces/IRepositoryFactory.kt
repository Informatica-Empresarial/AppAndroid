package co.com.lafemmeapp.dataprovider.repo.interfaces

/**
 * Created by oscargallon on 5/8/17.
 */

interface IRepositoryFactory {

    val customerRepository: ICustomerRepository

    val serviceRepository: IServicesRepository

    val appointmentRepository: IAppointmentRepository

    val iParamsRepository: IParamsRepository

    val deviceRepository: IDeviceRepository

    val sessionRepositoty: ISessionRepository

    val valuesRepository: IValueRepository


}
