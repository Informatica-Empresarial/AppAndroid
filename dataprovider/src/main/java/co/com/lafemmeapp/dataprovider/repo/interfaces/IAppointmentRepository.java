package co.com.lafemmeapp.dataprovider.repo.interfaces;

import java.util.List;

import co.com.lafemmeapp.dataprovider.network.entities.APIAppointment;
import co.com.lafemmeapp.dataprovider.network.entities.APIAppointmentAvailability;
import co.com.lafemmeapp.dataprovider.network.entities.AppointmentAvailabilityRequest;
import co.com.lafemmeapp.dataprovider.network.entities.AppointmentServiceRequest;
import co.com.lafemmeapp.dataprovider.network.entities.CreateAppointmentRequest;
import co.com.lafemmeapp.dataprovider.network.entities.RateAppointmentRequest;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by oscargallon on 5/8/17.
 */

public interface IAppointmentRepository {

    Observable<APIAppointmentAvailability>
    getAppointmentAvailability(String token,
                               AppointmentAvailabilityRequest appointmentAvailabilityRequest);

    Observable<APIAppointmentAvailability>
    getAppointmentAvailabilitySOS(String token,
                                  AppointmentAvailabilityRequest appointmentAvailabilityRequest);

    Observable<APIAppointment>
    preScheduleAppointment(String token,
                           CreateAppointmentRequest createAppointmentRequest);

    Observable<List<APIAppointment>>
    getAppointments(String token, String uuid, boolean isSpecialist);

    Observable<APIAppointment>
    scheduleAppointment(String token, String uuid, boolean isSOS);

    Observable<APIAppointment>
    cancelAppointment(String token, String uuid);

    Observable<APIAppointment> rateAppointment(String token,
                                               String uuid,
                                               RateAppointmentRequest rateAppointmentRequest);

    Observable<APIAppointment> startAppointment(String token,
                                                String uuid);


    Observable<APIAppointment> finishAppointment(String token,
                                                 String uuid);
}
