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

import com.tieto.weatherservice.WeatherResponse;

/**
 * 
 * @version $Id$
 */
public interface WeatherService {
    /**
     * 
     * @param location
     * @return
     */
    // public HashMap<String, String> getWeatherInfo(String location);

    /**
     * Returns the information about current weather conditions in the city
     * 
     * @param city
     *            The city you want to have weather conditions for
     * @param countryCode
     *            The city's country code (2 letters)
     * @return WeatherResponse
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ParseException
     * @throws DatatypeConfigurationException
     */
    public WeatherResponse getWeatherInfo(String city, String countryCode);

    @Cacheable("weather")
    public WeatherResponse getWeatherInfoFromCache(String city,
	    String countryCode);

    @CachePut("weather")
    public WeatherResponse setWeatherInfoInCache(String city, String countryCode);
}
