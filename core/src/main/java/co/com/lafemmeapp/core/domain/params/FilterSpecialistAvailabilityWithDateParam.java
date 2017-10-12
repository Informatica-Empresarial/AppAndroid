package co.com.lafemmeapp.core.domain.params;

import java.util.Date;
import java.util.List;

/**
 * Created by oscargallon on 5/9/17.
 */

public class FilterSpecialistAvailabilityWithDateParam {

    private final List<String> datesAvalaible;

    private final Date date;

    public FilterSpecialistAvailabilityWithDateParam(List<String> datesAvalaible, Date date) {
        this.datesAvalaible = datesAvalaible;
        this.date = date;
    }

    public List<String> getDatesAvalaible() {
        return datesAvalaible;
    }

    public Date getDate() {
        return date;
    }
}
