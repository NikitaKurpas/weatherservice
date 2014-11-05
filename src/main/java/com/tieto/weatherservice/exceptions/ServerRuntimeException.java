package com.tieto.weatherservice.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/**
 * Created by Nikita on 5. 11. 2014.
 */
@SoapFault(faultCode = FaultCode.SERVER)
public class ServerRuntimeException extends RuntimeException {

    public ServerRuntimeException(String message) {
        super(message);
    }
}
