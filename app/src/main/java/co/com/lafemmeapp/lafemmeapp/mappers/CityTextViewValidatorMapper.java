package co.com.lafemmeapp.lafemmeapp.mappers;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;

/**
 * Created by Stephys on 7/05/17.
 */

public class CityTextViewValidatorMapper implements ITextViewValidatorMapper {
    private static final CityTextViewValidatorMapper ourInstance = new CityTextViewValidatorMapper();
    private static Map<String, String> citiesMap = new HashMap<String, String>();
    private List<String> cities;

    public static CityTextViewValidatorMapper getInstance() {
        return ourInstance;
    }

    private CityTextViewValidatorMapper() {
    }

    @Override
    public TextChangedMappedEvent apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
        return new TextChangedMappedEvent(textViewTextChangeEvent.view().getId(),
                hasTheCity(textViewTextChangeEvent.text().toString()));
    }

    private Boolean hasTheCity(String cityName){
        String value = citiesMap.get(cityName);

        if(value != null){
            return true;
        }

        return false;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
        if(cities != null) {
            for (String i : cities) {
                citiesMap.put(i, i);
            }
        }
    }
}
