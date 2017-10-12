package co.com.lafemmeapp.providers.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by oscargallon on 5/9/17.
 */

public class DateProvider implements IDateProvider {

    private Calendar mCurrentCalendar;

    private Calendar mUtilCalendar;

    private final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";

    private SimpleDateFormat mSimpleDateFormat;

    public DateProvider() {
        mSimpleDateFormat =
                new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
    }

    @Override
    public Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar;
    }

    @Override
    public String getMaxDateFromDatesString(List<String> dateStringList) {
        String maxDate = "";
        for (String time : dateStringList) {
            maxDate = maxDate.compareTo(time) < 0 ?
                    time : maxDate;
        }
        return maxDate;
    }

    @Override
    public List<String> filterDatesWithMonthAndDayFromStringDateList(List<String> dateStringList,
                                                                     final Date date) {

        final Calendar dateProvidedCalendar = Calendar.getInstance();
        dateProvidedCalendar.setTime(date);
        final Calendar comparableCalendar = Calendar.getInstance();
        return new ArrayList<>(Collections2.filter(dateStringList,
                new Predicate<String>() {
                    @Override
                    public boolean apply(String input) {
                        try {
                            comparableCalendar.setTime(mSimpleDateFormat.parse(input));
                            return comparableCalendar.get(Calendar.MONTH)
                                    == dateProvidedCalendar.get(Calendar.MONTH)
                                    && comparableCalendar.get(Calendar.DAY_OF_MONTH)
                                    == dateProvidedCalendar.get(Calendar.DAY_OF_MONTH);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return false;

                    }
                }));

    }

    @Override
    public Calendar[] getSelectableDates(List<String> dates) throws ParseException {
        List<Calendar> calendarList = new ArrayList<>();
        boolean canAdd = true;
        Calendar calendar = null;
        for (String date : dates) {
            calendar = Calendar.getInstance();
            calendar.setTime(mSimpleDateFormat.parse(date));

            for (Calendar calendar1 : calendarList) {
                if (calendar1.get(Calendar.MONTH) ==
                        calendar.get(Calendar.MONTH)
                        && calendar1.get(Calendar.DAY_OF_MONTH)
                        == calendar.get(Calendar.DAY_OF_MONTH)) {
                    canAdd = false;
                    break;
                }
            }

            if (canAdd) {
                calendarList.add(calendar);
            }
            canAdd = true;
        }

        Calendar[] calendars = new Calendar[calendarList.size()];

        return calendarList.toArray(calendars);
    }
}
