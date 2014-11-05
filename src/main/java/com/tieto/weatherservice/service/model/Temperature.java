package com.tieto.weatherservice.service.model;

/**
 * Created by Nikita on 5. 11. 2014.
 */
public class Temperature {

    private final float tempCelsius;

    public Temperature(float tempCelsius) {
        this.tempCelsius = tempCelsius;
    }

    public float getCelsius() {
        return tempCelsius;
    }

    public float getFahrenheit() {
        return (this.tempCelsius * 9 / 5.0f) + 32;
    }

    public float getKelvin() {
        return this.tempCelsius + 274.15f;
    }
}
