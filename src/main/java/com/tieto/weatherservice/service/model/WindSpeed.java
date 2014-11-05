package com.tieto.weatherservice.service.model;

/**
 * Created by Nikita on 5. 11. 2014.
 */
public class WindSpeed {

    private final int kph;

    public WindSpeed(int kph) {
        this.kph = kph;
    }

    public int getKph() {
        return kph;
    }

    public int getMps() {
        return (int) (kph / 3.6);
    }

    public int getMph() {
        return (int) (kph / 1.61);
    }
}
