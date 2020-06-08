package it.sylwiabrant.weather_app.model;

/**
 * Created by Sylwia Brant
 */
public class ForecastWeather extends WeatherConditions{
    private String maxTemp;
    private String minTemp;

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
