package co.com.lafemmeapp.providers.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by oscargallon on 5/9/17.
 */

public interface IDateProvider {

    Calendar getCalendar(int year, int month, int day);

    String getMaxDateFromDatesString(List<String> dateStringList);

    List<String> filterDatesWithMonthAndDayFromStringDateList(List<String> dateStringList,
                                                              Date date);

    Calendar[] getSelectableDates(List<String> dates) throws ParseException;
}
