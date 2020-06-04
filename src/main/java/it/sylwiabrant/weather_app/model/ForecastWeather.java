package it.sylwiabrant.weather_app.model;

/**
 * Created by Sylwia Brant
 */
public class ForecastWeather extends WeatherConditions{
    private double maxTemp;
    private double minTemp;

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = Math.round(maxTemp*2)/2.0;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = Math.round(minTemp*2)/2.0;
    }
}
