package it.sylwiabrant.weather_app.model;

import static it.sylwiabrant.weather_app.controller.Messages.DEFAULT_DATA_PARAMETER;

/**
 * Created by Sylwia Brant
 */
public class ForecastWeather extends WeatherConditions {
    private String maxTemp;
    private String minTemp;

    public ForecastWeather() {
        this.maxTemp = DEFAULT_DATA_PARAMETER;
        this.minTemp = DEFAULT_DATA_PARAMETER;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }
}
