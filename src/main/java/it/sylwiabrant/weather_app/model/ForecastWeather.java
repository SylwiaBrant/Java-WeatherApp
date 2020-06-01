package it.sylwiabrant.weather_app.model;

/**
 * Created by Sylwia Brant
 */
public class ForecastWeather extends WeatherConditions{
    private double morningTemp;
    private double nightTemp;

    public double getMorningTemp() {
        return morningTemp;
    }

    public void setMorningTemp(double morningTemp) {
        this.morningTemp = morningTemp;
    }

    public double getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(double nightTemp) {
        this.nightTemp = nightTemp;
    }
}
