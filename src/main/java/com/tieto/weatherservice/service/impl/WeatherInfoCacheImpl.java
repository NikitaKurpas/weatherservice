package com.tieto.weatherservice.service.impl;

import com.tieto.weatherservice.connector.RestConnector;
import com.tieto.weatherservice.service.model.Location;
import com.tieto.weatherservice.service.model.WeatherInfoInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by Nikita on 5. 11. 2014.
 */
@Service
@Scope("singleton")
public class WeatherInfoCacheImpl implements com.tieto.weatherservice.service.WeatherInfoCache {

    public RestConnector connector;

    private Logger logger = Logger.getLogger(WeatherInfoCacheImpl.class.getName());

    @Autowired
    public WeatherInfoCacheImpl(RestConnector connector) {
        this.connector = connector;
    }

    @Override
    @Cacheable(value = "weatherInfoCache", key = "#location.cityName")
    public WeatherInfoInternal getWeatherInfoForLocationFromCache(Location location) {
        logger.info(" -- getWeatherInfoForLocationFromCache " + location);
        return getWeatherInfoForLocation(location);
    }

    @Override
    @CachePut(value = "weatherInfoCache", key = "#location.cityName")
    public WeatherInfoInternal updateWeatherInfoForLocationInCache(Location location) {
        logger.info(" -- updateWeatherInfoForLocationInCache " + location);
        WeatherInfoInternal weather = getWeatherInfoForLocation(location);
        if(weather == null) {
            return getWeatherInfoForLocationFromCache(location);
        }
        else {
            return getWeatherInfoForLocation(location);
        }
    }

    private WeatherInfoInternal getWeatherInfoForLocation(Location location) {
        final String cityName = location.getCityName();
        final String country = location.getCountry();

        return connector.getWeatherInfoForCity(cityName, country);
    }
}
