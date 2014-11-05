package com.tieto.weatherservice.service.model;


import java.time.LocalDateTime;

/**
 * Created by Nikita on 5. 11. 2014.
 */
public class WeatherInfoInternal {

    private final Location location;
    private final LocalDateTime dateTime;
    private final Temperature temperature;
    private final Humidity humidity;
    private final WindSpeed windSpeed;
    private final WindDirection windDirection;
    private final String windDescription;
    private final String weatherDescription;

    private WeatherInfoInternal(Builder builder) {
        this.location = builder.location;
        this.dateTime = builder.dateTime;
        this.humidity = builder.humidity;
        this.temperature = builder.temperature;
        this.weatherDescription = builder.weatherDescription;
        this.windDescription = builder.windDescription;
        this.windDirection = builder.windDirection;
        this.windSpeed = builder.windSpeed;

    }

    public Location getLocation() {
        return location;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDateTimeStr() {
        return dateTime.getDayOfMonth()+"."+dateTime.getMonthValue()+"."+dateTime.getYear()+" "+
                dateTime.getHour()+":"+dateTime.getMinute();
    }


    public Temperature getTemperature() {
        return temperature;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public WindSpeed getWindSpeed() {
        return windSpeed;
    }

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public String getWindDescription() {
        return windDescription;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public static class Builder {
        private Location location;
        private LocalDateTime dateTime;
        private Temperature temperature;
        private Humidity humidity;
        private WindSpeed windSpeed;
        private WindDirection windDirection;
        private String windDescription;
        private String weatherDescription;

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder setTemperature(Temperature temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder setHumidity(Humidity humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder setWindSpeed(WindSpeed windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public Builder setWindDirection(WindDirection windDirection) {
            this.windDirection = windDirection;
            return this;
        }

        public Builder setWindDescription(String windDescription) {
            this.windDescription = windDescription;
            return this;
        }

        public Builder setWeatherDescription(String weatherDescription) {
            this.weatherDescription = weatherDescription;
            return this;
        }

        public WeatherInfoInternal build() {
            return new WeatherInfoInternal(this);
        }
    }
}
