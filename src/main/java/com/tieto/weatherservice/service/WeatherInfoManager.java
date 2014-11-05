package com.tieto.weatherservice.service;

import com.tieto.weatherservice.service.model.Location;
import com.tieto.weatherservice.service.model.WeatherInfoInternal;

import java.util.List;

/**
 * Created by Nikita on 5. 11. 2014.
 */
public interface WeatherInfoManager {

    public WeatherInfoInternal getWeatherForLocation(Location location);

    public List<WeatherInfoInternal> getWeatherInfoForAllLocations();
}
