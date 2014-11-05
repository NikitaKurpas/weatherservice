package com.tieto.weatherservice.service;

import com.tieto.weatherservice.service.model.Location;
import com.tieto.weatherservice.service.model.WeatherInfoInternal;

/**
 * Created by Nikita on 5. 11. 2014.
 */
public interface WeatherInfoCache {

    public WeatherInfoInternal getWeatherInfoForLocationFromCache(Location location);

    public WeatherInfoInternal updateWeatherInfoForLocationInCache(Location location);
}
