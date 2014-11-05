package com.tieto.weatherservice.service.model;

/**
 * Created by Nikita on 5. 11. 2014.
 */
public class Location {

    private final String cityName;
    private final String country;

    public Location(String cityName, String country) {
        this.cityName = cityName;
        this.country = country;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public int hashCode() {
        // TODO: Explain this code
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Location other = (Location) obj;
        if (cityName == null) {
            if (other.cityName != null) return false;
        } else
        if (!cityName.equals(other.cityName)) return false;
        if (country == null) {
            if (other.country != null) return false;
        } else
        if (!country.equals(other.country)) return false;

        return true;
    }

    @Override
    public String toString() {
        return "country: " + this.country + ", city: " + this.cityName;
    }
}
