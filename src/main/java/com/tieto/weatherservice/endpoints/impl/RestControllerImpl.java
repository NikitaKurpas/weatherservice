package com.tieto.weatherservice.endpoints.impl;

import com.tieto.weatherservice.endpoints.RestController;
import com.tieto.weatherservice.endpoints.generated.RestWeatherResponse;
import com.tieto.weatherservice.exceptions.CityNotFoundException;
import com.tieto.weatherservice.exceptions.ServerRuntimeException;
import com.tieto.weatherservice.service.WeatherInfoManager;
import com.tieto.weatherservice.service.impl.CityManager;
import com.tieto.weatherservice.service.model.Location;
import com.tieto.weatherservice.service.model.WeatherInfoInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nikita on 5. 11. 2014.
 */
@org.springframework.web.bind.annotation.RestController
//@Controller TODO: Decide which annotation to use
public class RestControllerImpl implements RestController {

    private WeatherInfoManager weatherInfo;

    private CityManager cityManager;

    @Autowired
    public RestControllerImpl(WeatherInfoManager weatherInfo, CityManager cityManager) {
        this.weatherInfo = weatherInfo;
        this.cityManager = cityManager;
    }

    @Override
    @RequestMapping(value = "/weather/{city}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public RestWeatherResponse getWeatherInfoForLocation(@PathVariable String city) throws CityNotFoundException {
        Location location = cityManager.getLocationByCityName(city);
        if(location == null) {
            throw new CityNotFoundException("City '" + city + "' not found!");
        }
        return new ResponseMapper().mapRestWeatherResponse(weatherInfo.getWeatherForLocation(location));

    }

    @Override
    @RequestMapping(value = "/weather", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<RestWeatherResponse> getWeatherInfoForMultipleLocations() {
        List<WeatherInfoInternal> weatherInfoList = weatherInfo.getWeatherInfoForAllLocations();

        return new ResponseMapper().mapMultipleRestWeatherResponses(weatherInfoList);
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<String> handleCityNotFoundException(CityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServerRuntimeException.class)
    public ResponseEntity<String> handleServerRuntimeException(ServerRuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAnyException(Exception e) {
        return new ResponseEntity<>("An unpredicted error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public class ResponseMapper {

        public RestWeatherResponse mapRestWeatherResponse(WeatherInfoInternal weather) {
            RestWeatherResponse response = new RestWeatherResponse();

            response.setLocation(weather.getLocation().getCityName());
            response.setHumidity(String.valueOf(weather.getHumidity().toString()));
            response.setTempCelsius(weather.getTemperature().getCelsius());
            response.setTimestamp(weather.getDateTimeStr());
            response.setWeatherDescription(weather.getWeatherDescription());
            response.setWindDescription(weather.getWindDescription());
            response.setWindDirection(weather.getWindDirection().toString());
            response.setWindSpeedMPerS(weather.getWindSpeed().getMps());

            return response;
        }

        public List<RestWeatherResponse> mapMultipleRestWeatherResponses(List<WeatherInfoInternal> weatherList) {
            return weatherList.stream().map(this::mapRestWeatherResponse).collect(Collectors.toList());
        }

    }
}
