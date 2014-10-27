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

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.SoapHeader;

import com.tieto.weatherservice.services.WeatherService;
import com.tieto.weatherservice.utils.SOAPException;

@Endpoint
public class WeatherEndpoint {

    private static final String NAMESPACE_URI = "http://tieto.com/ws/schemas";

    private WeatherService ws;

    // @Autowired
    // public WeatherEndpoint(WeatherService ws) {
    // this.ws = ws;
    // }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "WeatherRequest")
    @ResponsePayload
    public JAXBElement<WeatherResponse> handleWeatherRequest(
	    @RequestPayload WeatherRequest request, SoapHeader header)
	    throws SOAPException {

	try {
	    return new JAXBElement<WeatherResponse>(new QName(NAMESPACE_URI,
		    "WeatherResponse"), WeatherResponse.class,
		    ws.getWeatherInfoFromCache(request.getCity(),
			    request.getCountryCode()));
	} catch (Exception e) {
	    throw new SOAPException(e.getMessage());
	}
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
