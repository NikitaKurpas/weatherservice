package com.tieto.weatherservice.service.impl;

import com.tieto.weatherservice.service.WeatherInfoCache;
import com.tieto.weatherservice.service.model.Location;
import com.tieto.weatherservice.service.model.WeatherInfoInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nikita on 5. 11. 2014.
 */
@Service
@Scope("singleton")
public class WeatherInfoManagerImpl implements com.tieto.weatherservice.service.WeatherInfoManager {

    private WeatherInfoCache cache;

    private CityManager cityManager;

    @Autowired
    public WeatherInfoManagerImpl(WeatherInfoCache cache, CityManager cityManager) {
        this.cache = cache;
        this.cityManager = cityManager;
    }

    @Override
    public WeatherInfoInternal getWeatherForLocation(Location location) {
        return cache.getWeatherInfoForLocationFromCache(location);
    }

    @Override
    public List<WeatherInfoInternal> getWeatherInfoForAllLocations() {
        List<WeatherInfoInternal> weatherList = new ArrayList<>();
        List<Location> cities = cityManager.getLocations();

        weatherList.addAll(cities.stream().map(cache::getWeatherInfoForLocationFromCache).collect(Collectors.toList()));

        return weatherList;
    }
}
