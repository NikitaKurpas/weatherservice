package com.tieto.weatherservice.connector;

import com.tieto.weatherservice.service.model.WeatherInfoInternal;

/**
 * Created by Nikita on 5. 11. 2014.
 */
public interface RestConnector {

    public WeatherInfoInternal getWeatherInfoForCity(String city, String country);
}
