package com.tieto.weatherservice.endpoints;

import com.tieto.weatherservice.endpoints.generated.RestWeatherResponse;
import com.tieto.weatherservice.endpoints.generated.SoapWeatherRequest;
import com.tieto.weatherservice.endpoints.generated.SoapWeatherResponse;
import com.tieto.weatherservice.exceptions.CityNotFoundException;

import java.util.List;

/**
 * Created by Nikita on 5. 11. 2014.
 */
public interface RestController {


    public RestWeatherResponse getWeatherInfoForLocation(String city) throws CityNotFoundException;

    public List<RestWeatherResponse> getWeatherInfoForMultipleLocations();



}
