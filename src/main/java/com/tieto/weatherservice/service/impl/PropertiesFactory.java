package com.tieto.weatherservice.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nikita on 5. 11. 2014.
 */
@Service
@Scope("singleton")
public class PropertiesFactory {

    public Map<String, String> loadProperties(String fileName) {
        Resource resource = new ClassPathResource(fileName);
        Map<String, String> _result = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
            String _line = reader.readLine();
            while (_line != null) {
                // TODO: Handle incorrect properties file
                _result.put(_line.split("=")[0], _line.split("=")[1]);
            }
            return _result;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
