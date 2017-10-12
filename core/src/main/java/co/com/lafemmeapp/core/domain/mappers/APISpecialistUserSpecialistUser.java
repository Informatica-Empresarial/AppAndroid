package co.com.lafemmeapp.core.domain.mappers;

import com.google.common.collect.Collections2;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.com.lafemmeapp.core.domain.entities.SpecialistUser;
import co.com.lafemmeapp.core.domain.entities.SpecialistUserBuilder;
import co.com.lafemmeapp.dataprovider.Constants;
import co.com.lafemmeapp.dataprovider.network.entities.APISpecialistUser;
import co.com.lafemmeapp.dataprovider.network.entities.APIValidStartDateTimes;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 5/22/17.
 */

public class APISpecialistUserSpecialistUser implements Function<APISpecialistUser, SpecialistUser> {
    private static final APISpecialistUserSpecialistUser ourInstance = new APISpecialistUserSpecialistUser();

    public static APISpecialistUserSpecialistUser getInstance() {
        return ourInstance;
    }


    private SimpleDateFormat mSimpleDateFormat;

    private APISpecialistUserSpecialistUser() {
        mSimpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
    }

    @Override
    public SpecialistUser apply(APISpecialistUser apiSpecialistUser) throws Exception {

        List<String> validStartDateTimes = apiSpecialistUser.getValidStartDateTimes() != null ?
                new ArrayList<>(Collections2.transform(apiSpecialistUser.getValidStartDateTimes()
                        , new com.google.common.base.Function<APIValidStartDateTimes, String>() {

                            @Override
                            public String apply(APIValidStartDateTimes input) {
                                DateTime date = DateTime.parse(input.getDate(),
                                        DateTimeFormat.forPattern(Constants.FORMAT_DATE_SERVER));
                                return mSimpleDateFormat.format(date.toDate());
                            }
                        })) : new ArrayList<String>();


        return new SpecialistUserBuilder()
                .setUuid(apiSpecialistUser.getUuid())
                .setAddress(apiSpecialistUser.getAddress())
                .setAvatar(apiSpecialistUser.getAvatar())
                .setDNI(apiSpecialistUser.getDNI())
                .setEmail(apiSpecialistUser.getEmail())
                .setLastName(apiSpecialistUser.getLastName())
                .setName(apiSpecialistUser.getName())
                .setPhoneNumber(apiSpecialistUser.getPhoneNumber())
                .setValidStartDateTimes(validStartDateTimes)
                .setSOS(apiSpecialistUser.isSOS())
                .setCity(apiSpecialistUser.getCity())
                .createSpecialistUser();
    }
}
