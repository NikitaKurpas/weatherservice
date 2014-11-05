package com.tieto.weatherservice.service.model;

/**
 * Created by Nikita on 5. 11. 2014.
 */
public class Humidity {

    private final int relativeHumidity;

    public Humidity(int relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    @Override
    public String toString() {
        return String.valueOf(this.relativeHumidity) + "%";
    }
}
