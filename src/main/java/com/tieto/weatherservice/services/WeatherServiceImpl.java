/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.tieto.weatherservice.services;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tieto.weatherservice.CurrentObservation;
import com.tieto.weatherservice.Response;
import com.tieto.weatherservice.WeatherResponse;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final String API_KEY = "9a9995a3b844c2e6";

    private String uriString = "http://api.wunderground.com/api/9a9995a3b844c2e6/conditions/q/{countryCode}/{city}.xml";

    private RestTemplate restTemplate;

    /**
     * {@inheritDoc}
     * 
     * @see com.tieto.weatherservice.services.WeatherService#getWeatherInfo(java.lang.String)
     */
    // public HashMap<String, String> getWeatherInfo(String location) {
    // // TODO Auto-generated method stub
    // return null;
    // }

    // public WeatherResponse getWeatherInfo(String city, String countryCode)
    // throws ClientProtocolException, IOException, ParseException,
    // DatatypeConfigurationException {
    //
    // String _uriString = String.format(uriString, countryCode,
    // city.replaceAll(" ", "_"));
    //
    // String response = Request.Get(_uriString).connectTimeout(2000)
    // .socketTimeout(2000).execute().returnContent().asString();
    //
    // JsonNode root = mapper.readTree(response);
    //
    // // If the specified city was not found, return null
    // if (root.get("current_observation") == null)
    // return null;
    //
    // WeatherResponse weatherResponse = new WeatherResponse();
    //
    // // Populate the model with parameters
    // weatherResponse.setLocation(root.get("current_observation")
    // .get("display_location").get("full").asText());
    // weatherResponse.setHumidity(root.get("current_observation")
    // .get("relative_humidity").asText());
    // weatherResponse.setObservationTime(root.get("current_observation")
    // .get("observation_time").asText());
    // weatherResponse.setTemerature(root.get("current_observation")
    // .get("temperature_string").asText());
    // weatherResponse.setWeatherDescription(root.get("current_observation")
    // .get("weather").asText());
    // weatherResponse.setWindDescription(root.get("current_observation")
    // .get("wind_string").asText());
    // weatherResponse.setWindDirection(root.get("current_observation")
    // .get("wind_dir").asText());
    // weatherResponse.setWindSpeed(root.get("current_observation")
    // .get("wind_kph").asText());
    //
    // return weatherResponse;
    // }

    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     * @throws ClientProtocolException
     * @throws ParseException
     * @throws DatatypeConfigurationException
     * 
     * @see com.tieto.weatherservice.services.WeatherService#getWeatherInfo(java.lang.String,
     *      java.lang.String)
     */
    public WeatherResponse getWeatherInfo(String city, String countryCode) {

	Response response = restTemplate.getForObject(uriString,
		Response.class, countryCode, city);

	CurrentObservation currentObservation = response
		.getCurrentObservation();

	WeatherResponse weatherResponse = new WeatherResponse();

	weatherResponse.setHumidity(currentObservation.getRelativeHumidity());
	weatherResponse.setLocation(currentObservation.getObservationLocation()
		.getFull());
	weatherResponse.setObservationTime(currentObservation
		.getObservationTime());
	weatherResponse
		.setTemerature(currentObservation.getTemperatureString());
	weatherResponse.setWeatherDescription(currentObservation.getWeather());
	weatherResponse.setWindDescription(currentObservation.getWindString());
	weatherResponse.setWindDirection(currentObservation.getWindDir());
	weatherResponse.setWindSpeed(currentObservation.getWindMph() + " MPH");

	return weatherResponse;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatatypeConfigurationException
     * @throws ParseException
     * @throws IOException
     * @throws ClientProtocolException
     * 
     * @see com.tieto.weatherservice.services.WeatherService#getWeatherInfoFromCache(java.lang.String,
     *      java.lang.String)
     */
    @Cacheable("weather")
    public WeatherResponse getWeatherInfoFromCache(String city,
	    String countryCode) {
	return getWeatherInfo(city, countryCode);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DatatypeConfigurationException
     * @throws ParseException
     * @throws IOException
     * @throws ClientProtocolException
     * 
     * @see com.tieto.weatherservice.services.WeatherService#setWeatherResponseInCache(com.tieto.weatherservice.WeatherResponse)
     */
    @CachePut("weather")
    public WeatherResponse setWeatherInfoInCache(String city, String countryCode) {
	return getWeatherInfo(city, countryCode);
    }

    /**
     * @return the restTemplate
     */
    public RestTemplate getRestTemplate() {
	return restTemplate;
    }

    /**
     * @param restTemplate
     *            the restTemplate to set
     */
    public void setRestTemplate(RestTemplate restTemplate) {
	this.restTemplate = restTemplate;
    }
}
