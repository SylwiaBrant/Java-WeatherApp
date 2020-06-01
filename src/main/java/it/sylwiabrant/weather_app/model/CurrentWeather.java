package it.sylwiabrant.weather_app.model;

/**
 * Created by Sylwia Brant
 */
public class CurrentWeather extends WeatherConditions{
    private double windChill;
    private int visibility;
    private double humidity;

    public double getWindChill() {
        return windChill;
    }

    public void setWindChill(double windChill) {
        this.windChill = windChill;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
