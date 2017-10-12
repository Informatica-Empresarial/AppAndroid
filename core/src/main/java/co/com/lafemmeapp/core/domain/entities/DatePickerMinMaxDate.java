package co.com.lafemmeapp.core.domain.entities;

import java.util.Calendar;

/**
 * Created by oscargallon on 5/9/17.
 */

public class DatePickerMinMaxDate {

    private final long minDate;

    private final long maxDate;

    private  Calendar[] selectableDates;

    public DatePickerMinMaxDate(long minDate, long maxDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    public DatePickerMinMaxDate(long minDate, long maxDate, Calendar[] selectableDates) {
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.selectableDates = selectableDates;
    }

    public long getMinDate() {
        return minDate;
    }

    public long getMaxDate() {
        return maxDate;
    }

    public Calendar[] getSelectableDates() {
        return selectableDates;
    }

    public void setSelectableDates(Calendar[] selectableDates) {
        this.selectableDates = selectableDates;
    }
}
