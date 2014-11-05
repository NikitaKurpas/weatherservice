package com.tieto.weatherservice.endpoints.impl;

import com.tieto.weatherservice.endpoints.generated.SoapWeatherRequest;
import com.tieto.weatherservice.endpoints.generated.SoapWeatherResponse;
import com.tieto.weatherservice.endpoints.generated.SoapWeatherResponseItem;
import com.tieto.weatherservice.exceptions.CityNotFoundException;
import com.tieto.weatherservice.service.WeatherInfoManager;
import com.tieto.weatherservice.service.impl.CityManager;
import com.tieto.weatherservice.service.model.Location;
import com.tieto.weatherservice.service.model.WeatherInfoInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Nikita on 5. 11. 2014.
 */
@Endpoint
public class SoapEndpointImpl implements com.tieto.weatherservice.endpoints.SoapEndpoint {

    private static final String NAMESPACE_URI = "http://tieto.com/ws/schemas";

    private WeatherInfoManager weatherManager;

    private CityManager cityManager;

    @Autowired
    public SoapEndpointImpl(WeatherInfoManager weatherManager, CityManager cityManager) {
        this.weatherManager = weatherManager;
        this.cityManager = cityManager;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SoapWeatherRequest")
    @ResponsePayload
    public SoapWeatherResponse getWeather(@RequestPayload SoapWeatherRequest request) throws CityNotFoundException {
        SoapWeatherResponse response = new ResponseMapper().getWeatherInfoResponse(request);
        if (response == null) {
            throw new CityNotFoundException("Specified cities not found");
        }

        return response;
    }


    public class ResponseMapper {

        public SoapWeatherResponseItem mapWeatherInfoToResponseItem(WeatherInfoInternal weather) {
            SoapWeatherResponseItem item = new SoapWeatherResponseItem();

            item.setLocation(weather.getLocation().getCityName());
            item.setHumidity(String.valueOf(weather.getHumidity().toString()));
            item.setTemerature(weather.getTemperature().getCelsius());
            item.setObservationTime(weather.getDateTimeStr());
            item.setWeatherDescription(weather.getWeatherDescription());
            item.setWindDescription(weather.getWindDescription());
            item.setWindDirection(weather.getWindDirection().toString());
            item.setWindSpeed(weather.getWindSpeed().getMps());


            return item;
        }

        public SoapWeatherResponse getWeatherInfoResponse(SoapWeatherRequest request) {
            SoapWeatherResponse response = new SoapWeatherResponse();

            if (request.getCity().isEmpty()) {
                for (Location currentLocation : cityManager.getLocations()) {
                    final WeatherInfoInternal weather = weatherManager.getWeatherForLocation(currentLocation);
                    final SoapWeatherResponseItem weatherInfoItem = mapWeatherInfoToResponseItem(weather);
                    response.getSoapWeatherResponse().add(weatherInfoItem);
                }
            } else {
                for (String cityName : request.getCity()) {
                    Location location = cityManager.getLocationByCityName(cityName);
                    if (location == null) {
                        return null;
                    }
                    final WeatherInfoInternal weather = weatherManager.getWeatherForLocation(location);
                    final SoapWeatherResponseItem weatherResponseItem = mapWeatherInfoToResponseItem(weather);
                    if (weatherResponseItem != null) {
                        response.getSoapWeatherResponse().add(weatherResponseItem);
                    }
                }
            }
            return response;
        }

    }
}
