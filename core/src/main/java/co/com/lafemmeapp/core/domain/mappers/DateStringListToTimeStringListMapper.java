package co.com.lafemmeapp.core.domain.mappers;

import com.google.common.collect.Collections2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import co.com.lafemmeapp.dataprovider.Constants;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 5/9/17.
 */

public class DateStringListToTimeStringListMapper implements Function<List<String>, List<String>> {

    private static final DateStringListToTimeStringListMapper ourInstance =
            new DateStringListToTimeStringListMapper();

    private SimpleDateFormat mSimpleDateFormat;

    private  final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";

    public static DateStringListToTimeStringListMapper getInstance() {
        return ourInstance;
    }


    private DateStringListToTimeStringListMapper() {
        mSimpleDateFormat= new SimpleDateFormat(DATE_TIME_FORMAT,Locale.getDefault());
    }

    @Override
    public List<String> apply(List<String> strings) throws Exception {
        return new ArrayList<>(Collections2.transform(strings,
                new com.google.common.base.Function<String, String>() {

                    @Override
                    public String apply(String input) {
                        Calendar calendar =
                                Calendar.getInstance();
                        try {
                            calendar.setTime(mSimpleDateFormat.parse(input));
                            return String.format("%s:%s",
                                    calendar.get(Calendar.HOUR_OF_DAY),
                                    calendar.get(Calendar.MINUTE) < 10 ?
                                            String.format(Locale.getDefault(),
                                                    "0%s", calendar.get(Calendar.MINUTE)) :
                                            calendar.get(Calendar.MINUTE));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                }));

    }
}
