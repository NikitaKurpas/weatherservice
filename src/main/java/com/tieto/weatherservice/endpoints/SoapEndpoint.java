package com.tieto.weatherservice.endpoints;

import com.tieto.weatherservice.endpoints.generated.SoapWeatherRequest;
import com.tieto.weatherservice.endpoints.generated.SoapWeatherResponse;
import com.tieto.weatherservice.exceptions.CityNotFoundException;

/**
 * Created by Nikita on 5. 11. 2014.
 */
public interface SoapEndpoint {

    public SoapWeatherResponse getWeather(SoapWeatherRequest request) throws CityNotFoundException;
}
