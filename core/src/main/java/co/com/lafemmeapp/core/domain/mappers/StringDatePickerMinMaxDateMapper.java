package co.com.lafemmeapp.core.domain.mappers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import co.com.lafemmeapp.core.domain.entities.DatePickerMinMaxDate;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 5/9/17.
 */

public class StringDatePickerMinMaxDateMapper implements Function<String, DatePickerMinMaxDate> {

    private SimpleDateFormat mSimpleDateFormat;

    private final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";


    private static final StringDatePickerMinMaxDateMapper ourInstance = new StringDatePickerMinMaxDateMapper();

    public static StringDatePickerMinMaxDateMapper getInstance() {
        return ourInstance;
    }

    private StringDatePickerMinMaxDateMapper() {
        mSimpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
    }

    @Override
    public DatePickerMinMaxDate apply(String s) throws Exception {
        return new DatePickerMinMaxDate(Calendar.getInstance().getTime().getTime(),
                mSimpleDateFormat.parse(s).getTime());
    }
}
