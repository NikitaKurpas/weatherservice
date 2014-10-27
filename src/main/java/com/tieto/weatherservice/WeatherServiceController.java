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
package com.tieto.weatherservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tieto.weatherservice.services.WeatherService;

@Controller
public class WeatherServiceController {

    private WeatherService ws;

    // public WeatherServiceController(WeatherService ws) {
    // this.ws = ws;
    // System.out
    // .println(" ----------- Initialized WS controller ----------- ");
    // }

    @RequestMapping(value = "/{countryCode}/{city}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public WeatherResponse getForecast(@PathVariable String countryCode,
	    @PathVariable String city) {

	return ws.getWeatherInfoFromCache(city, countryCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAnyException(Exception e) {
	return new ResponseEntity<String>("Ooops! Something bad happened: "
		+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * @return the ws
     */
    public WeatherService getWs() {
	return ws;
    }

    /**
     * @param ws
     *            the ws to set
     */
    public void setWs(WeatherService ws) {
	this.ws = ws;
    }
}
