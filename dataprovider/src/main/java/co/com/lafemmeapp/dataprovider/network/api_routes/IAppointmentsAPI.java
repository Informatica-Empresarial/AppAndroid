package co.com.lafemmeapp.dataprovider.network.api_routes;

import java.util.List;

import co.com.lafemmeapp.dataprovider.network.entities.APIAppointment;
import co.com.lafemmeapp.dataprovider.network.entities.APIAppointmentAvailability;
import co.com.lafemmeapp.dataprovider.network.entities.AppointmentAvailabilityRequest;
import co.com.lafemmeapp.dataprovider.network.entities.AppointmentServiceRequest;
import co.com.lafemmeapp.dataprovider.network.entities.CreateAppointmentRequest;
import co.com.lafemmeapp.dataprovider.network.entities.RateAppointmentRequest;
import co.com.lafemmeapp.dataprovider.params.ScheduleAppointmentRequest;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by oscargallon on 5/8/17.
 */

public interface IAppointmentsAPI {


    @POST("appointments/availability")
    Observable<APIAppointmentAvailability>
    getAppoinmentAvailability(@Header("Authorization")
                                      String token,
                              @Body AppointmentAvailabilityRequest appointmentAvailabilityRequest);

    @POST("appointments/availability/SOS")
    Observable<APIAppointmentAvailability>
    getAppoitmentAvailabilitySOS(@Header("Authorization") String token,
                                 @Body AppointmentAvailabilityRequest appointmentAvailabilityRequest);

    @POST("appointments")
    Observable<APIAppointment> prescheduleAppointment(@Header("Authorization") String token,
                                                      @Body CreateAppointmentRequest createAppointmentRequest);


    @GET("appointments")
    Observable<List<APIAppointment>> getAppointments(@Header("Authorization") String token,
                                                     @Query("customerUuid") String uuid);

    @GET("appointments")
    Observable<List<APIAppointment>> getAppointmentsSpecilialist(@Header("Authorization") String token,
                                                     @Query("specialistUuid") String uuid);

    @PUT("appointments/{uuid}/schedule")
    Observable<APIAppointment> scheduleAppointment(@Header("Authorization") String token,
                                                   @Path("uuid") String uuid,
                                                   @Body ScheduleAppointmentRequest scheduleAppointmentRequest);

    @PUT("appointments/{uuid}/cancel")
    Observable<APIAppointment> cancelAppointment(@Header("Authorization") String token,
                                                 @Path("uuid") String uuid);

    @POST("appointments/{uuid}/ratings")
    Observable<APIAppointment> rateAppointment(@Header("Authorization") String token,
                                               @Path("uuid") String uuid,
                                               @Body RateAppointmentRequest rateAppointmentRequest);


    @PUT("appointments/{uuid}/start")
    Observable<APIAppointment> startAppointment(@Header("Authorization") String token,
                                                @Path("uuid") String uuid);

    @PUT("appointments/{uuid}/deliver")
    Observable<APIAppointment> finishAppointment(@Header("Authorization") String token,
                                                @Path("uuid") String uuid);
}
