package it.sylwiabrant.weather_app.model;

import static it.sylwiabrant.weather_app.controller.Messages.DEFAULT_DATA_PARAMETER;

/**
 * Created by Sylwia Brant
 */
public abstract class WeatherConditions {
    private String icon;
    private String main;
    private String description;
    private String date;
    private String pressure;
    private String windSpeed;
    private String windDirection;
    private String snow;
    private String rain;
    private String clouds;

    public WeatherConditions() {
        this.icon = DEFAULT_DATA_PARAMETER;
        this.main = DEFAULT_DATA_PARAMETER;
        this.description = DEFAULT_DATA_PARAMETER;
        this.date = DEFAULT_DATA_PARAMETER;
        this.pressure = DEFAULT_DATA_PARAMETER;
        this.windSpeed = DEFAULT_DATA_PARAMETER;
        this.windDirection = DEFAULT_DATA_PARAMETER;
        this.snow = DEFAULT_DATA_PARAMETER;
        this.rain = DEFAULT_DATA_PARAMETER;
        this.clouds = DEFAULT_DATA_PARAMETER;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getSnow() {
        return snow;
    }

    public void setSnow(String snow) {
        this.snow = snow;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }
}
