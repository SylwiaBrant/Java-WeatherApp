package it.sylwiabrant.weather_app.model;

import static it.sylwiabrant.weather_app.controller.Messages.DEFAULT_DATA_PARAMETER;

/**
 * Created by Sylwia Brant
 */
public class CurrentWeather extends WeatherConditions {
    private String temp;
    private String windChill;
    private String visibility;
    private String humidity;
    private String clouds;

    public CurrentWeather() {
        this.temp = DEFAULT_DATA_PARAMETER;
        this.windChill = DEFAULT_DATA_PARAMETER;
        this.visibility = DEFAULT_DATA_PARAMETER;
        this.humidity = DEFAULT_DATA_PARAMETER;
        this.clouds = DEFAULT_DATA_PARAMETER;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getWindChill() {
        return windChill;
    }

    public void setWindChill(String windChill) {
        this.windChill = windChill;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
