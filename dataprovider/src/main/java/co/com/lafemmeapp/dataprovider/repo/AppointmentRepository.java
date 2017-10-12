package co.com.lafemmeapp.dataprovider.repo;

import java.util.List;

import co.com.lafemmeapp.dataprovider.network.APIProvider;
import co.com.lafemmeapp.dataprovider.network.api_routes.IAppointmentsAPI;
import co.com.lafemmeapp.dataprovider.network.entities.APIAppointment;
import co.com.lafemmeapp.dataprovider.network.entities.APIAppointmentAvailability;
import co.com.lafemmeapp.dataprovider.network.entities.AppointmentAvailabilityRequest;
import co.com.lafemmeapp.dataprovider.network.entities.AppointmentServiceRequest;
import co.com.lafemmeapp.dataprovider.network.entities.CreateAppointmentRequest;
import co.com.lafemmeapp.dataprovider.network.entities.RateAppointmentRequest;
import co.com.lafemmeapp.dataprovider.params.ScheduleAppointmentRequest;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IAppointmentRepository;
import io.reactivex.Observable;

/**
 * Created by oscargallon on 5/8/17.
 */

public class AppointmentRepository implements IAppointmentRepository {

    private IAppointmentsAPI mAppointmentsAPI;

    public AppointmentRepository() {
        mAppointmentsAPI = APIProvider.getInstance()
                .getRetrofit().create(IAppointmentsAPI.class);

    }

    @Override
    public Observable<APIAppointmentAvailability>
    getAppointmentAvailability(String token,
                               AppointmentAvailabilityRequest appointmentServiceRequest) {
        return mAppointmentsAPI.getAppoinmentAvailability(token, appointmentServiceRequest);
    }

    @Override
    public Observable<APIAppointmentAvailability>
    getAppointmentAvailabilitySOS(String token,
                                  AppointmentAvailabilityRequest appointmentServiceRequest) {
        return mAppointmentsAPI.getAppoitmentAvailabilitySOS(token, appointmentServiceRequest);
    }

    @Override
    public Observable<APIAppointment>
    preScheduleAppointment(String token,
                           CreateAppointmentRequest createAppointmentRequest) {
        return mAppointmentsAPI.prescheduleAppointment(token, createAppointmentRequest);
    }

    @Override
    public Observable<List<APIAppointment>> getAppointments(String token, String uuid,
                                                            boolean isSpecialist) {

        if (isSpecialist) {
            return mAppointmentsAPI.getAppointmentsSpecilialist(token, uuid);
        }

        return mAppointmentsAPI.getAppointments(token, uuid);
    }

    @Override
    public Observable<APIAppointment> scheduleAppointment(String token, String uuid, boolean isSOS) {
        return mAppointmentsAPI.scheduleAppointment(token, uuid, new ScheduleAppointmentRequest(isSOS));
    }

    @Override
    public Observable<APIAppointment> cancelAppointment(String token, String uuid) {
        return mAppointmentsAPI.cancelAppointment(token, uuid);
    }

    @Override
    public Observable<APIAppointment> rateAppointment(String token, String uuid, RateAppointmentRequest rateAppointmentRequest) {
        return mAppointmentsAPI.rateAppointment(token, uuid, rateAppointmentRequest);
    }

    @Override
    public Observable<APIAppointment> startAppointment(String token, String uuid) {
        return mAppointmentsAPI.startAppointment(token, uuid);
    }

    @Override
    public Observable<APIAppointment> finishAppointment(String token, String uuid) {
        return mAppointmentsAPI.finishAppointment(token, uuid);
    }
}
