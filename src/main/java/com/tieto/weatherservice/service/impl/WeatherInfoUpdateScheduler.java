package com.tieto.weatherservice.service.impl;

import com.tieto.weatherservice.service.WeatherInfoCache;
import com.tieto.weatherservice.service.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Nikita on 5. 11. 2014.
 */
@Service("weatherInfoUpdateScheduler")
public class WeatherInfoUpdateScheduler {

    private CityManager cityManager;

    private WeatherInfoCache cache;

    @Autowired
    public WeatherInfoUpdateScheduler(CityManager cityManager, WeatherInfoCache cache) {
        this.cityManager = cityManager;
        this.cache = cache;
    }

    public void updateWeatherInfo() {
        cityManager.getLocations().forEach(cache::updateWeatherInfoForLocationInCache);

    }
}
