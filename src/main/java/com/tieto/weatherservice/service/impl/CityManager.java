package com.tieto.weatherservice.service.impl;

import com.tieto.weatherservice.service.model.Location;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikita on 5. 11. 2014.
 */
@Service
@Scope("singleton")
public class CityManager {

    private List<String> cities;
    private Map<String, String> citiesToCountries;
    private List<Location> locations;

    public CityManager() {
        loadCities();
    }

    public void loadCities() {
        cities = new ArrayList<>();
        citiesToCountries = new HashMap<>();
        locations = new ArrayList<>();

        try {
            Resource resource = new ClassPathResource("cities.xml");

            File xmlFile = resource.getFile();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            NodeList list = document.getElementsByTagName("city");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE) continue;

                Element element = (Element) node;
                String cityName = element.getElementsByTagName("name").item(0).getTextContent();
                String country = element.getElementsByTagName("country").item(0).getTextContent();

                cities.add(cityName);
                citiesToCountries.put(cityName, country);
                locations.add(new Location(cityName, country));
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<String> getCities() {
        return cities;
    }

    public Map<String, String> getCitiesToCountries() {
        return citiesToCountries;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public String getCountryByCity(String cityName) {
        return citiesToCountries.get(cityName);
    }

    public Location getLocationByCityName(String cityName) {
        for (Location location: locations) {
            if (location.getCityName().equals(cityName)) return location;
        }
        return null;
    }
}
