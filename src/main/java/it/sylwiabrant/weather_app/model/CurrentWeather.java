package it.sylwiabrant.weather_app.model;

/**
 * Created by Sylwia Brant
 */
public class CurrentWeather extends WeatherConditions{
    private double temp;
    private String windChill;
    private String visibility;
    private String humidity;
    private double clouds;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp =  Math.round(temp*2)/2.0;
    }

    public double getClouds() {
        return clouds;
    }

    public void setClouds(double clouds) {
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
