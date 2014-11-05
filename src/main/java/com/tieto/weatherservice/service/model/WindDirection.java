package com.tieto.weatherservice.service.model;

/**
 * Created by Nikita on 5. 11. 2014.
 */
public enum WindDirection {
    // TODO: Explain this code
    EAST("East"),
    ENE("ENE"),
    ESE("ESE"),
    NE("NE"),
    NNE("NNE"),
    NNW("NNW"),
    NORTH("North"),
    NW("NW"),
    SE("SE"),
    SOUTH("South"),
    SSE("SSE"),
    SSW("SSW"),
    SW("SW"),
    VARIABLE("Variable"),
    WEST("West"),
    WNW("WNW"),
    WSW("WSW");

    private final String value;

    private WindDirection(String value) {
        this.value = value;
    }

    public static WindDirection fromValue(String value) {
        for (WindDirection direction : WindDirection.values()) {
            if (direction.value.equals(value)) {
                return direction;
            }
        }
        throw new IllegalArgumentException(value);
    }

    public String value() {
        return value;
    }

}

