package com.tieto.weatherservice.connector.impl;

import com.tieto.weatherservice.connector.RestConnector;
import com.tieto.weatherservice.connector.generated.CurrentObservation;
import com.tieto.weatherservice.connector.generated.Response;
import com.tieto.weatherservice.service.impl.PropertiesFactory;
import com.tieto.weatherservice.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita on 5. 11. 2014.
 */
@Service
@Scope("singleton")
public class WundergroundRestConnector implements RestConnector {

    private final static String URL = "http://api.wunderground.com/api/";

    private RestTemplate restTemplate;

    private final String apiKey;

    @Autowired
    public WundergroundRestConnector(RestTemplate restTemplate, PropertiesFactory properties) {
        this.restTemplate = restTemplate;

        apiKey = properties.loadProperties("apikey.properties").get("apikey");
    }

    @Override
    public WeatherInfoInternal getWeatherInfoForCity(String city, String country) {
        String computedUrl = URL+"{apiKey}/conditions/q/{countryName}/{cityName}.xml";

        Response response = restTemplate.getForObject(computedUrl, Response.class, apiKey, country, city);

        if (response == null) return null;

        return new ResponseMapper().mapResponseToInternalWeatherInfo(response);
    }

//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        List<HttpMessageConverter<?>> converters = new ArrayList<>();
//
//        converters.add(marshallingMessageConverter());
//        restTemplate.setMessageConverters(converters);
//
//        return restTemplate;
//    }
//
//    @Bean
//    public MarshallingHttpMessageConverter marshallingMessageConverter() {
//        return new MarshallingHttpMessageConverter(
//                jaxb2Marshaller(),
//                jaxb2Marshaller()
//        );
//    }
//
//    @Bean
//    public Jaxb2Marshaller jaxb2Marshaller() {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        marshaller.setPackagesToScan(new String []{"com.tieto.weatherservice.connector.generated"});
//        return marshaller;
//    }

    public class ResponseMapper {

        public WeatherInfoInternal mapResponseToInternalWeatherInfo(Response response) {
            try {
                final CurrentObservation observation = response.getCurrentObservation();

                final Location location = new Location(observation.getDisplayLocation().getCity(), observation.getDisplayLocation().getCountry());

                final Humidity humidity = new Humidity(NumberFormat.getInstance().parse(observation.getRelativeHumidity()).intValue());

                final Temperature temperature = new Temperature(observation.getTempC());

                final int epoch = observation.getObservationEpoch();
                final ZoneOffset zone = ZoneOffset.ofHours(0);
                final LocalDateTime dateTime = LocalDateTime.ofEpochSecond(epoch, 0, zone);

                final String weatherDescription = observation.getWeather();

                final String wind = observation.getWindDir();
                final WindDirection windDirection = WindDirection.fromValue(wind);

                final String windDescription = observation.getWindString();

                final int windKph = (int) observation.getWindKph();
                final WindSpeed windSpeed = new WindSpeed(windKph);

                return new WeatherInfoInternal.Builder().
                        setLocation(location).
                        setDateTime(dateTime).
                        setHumidity(humidity).
                        setTemperature(temperature).
                        setWeatherDescription(weatherDescription).
                        setWindDescription(windDescription).
                        setWindDirection(windDirection).
                        setWindSpeed(windSpeed).
                        build();
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
    }
}
