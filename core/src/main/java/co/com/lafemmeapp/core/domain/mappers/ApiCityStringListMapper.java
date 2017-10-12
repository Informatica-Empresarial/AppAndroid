package co.com.lafemmeapp.core.domain.mappers;

import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.List;

import co.com.lafemmeapp.dataprovider.network.entities.APICity;
import io.reactivex.functions.Function;

/**
 * Created by Stephys on 7/05/17.
 */

public class ApiCityStringListMapper implements Function<List<APICity>, List<String>> {
    private static final ApiCityStringListMapper ourInstance = new ApiCityStringListMapper();

    public static ApiCityStringListMapper getInstance() {
        return ourInstance;
    }

    private ApiCityStringListMapper() {
    }


    @Override
    public List<String> apply(List<APICity> apiCities) throws Exception {
        return new ArrayList<String>(Collections2.transform(apiCities,
                new com.google.common.base.Function<APICity, String>() {
                    @Override
                    public String apply(APICity input) {
                        return input.getName();
                    }
                }));
    }
}
