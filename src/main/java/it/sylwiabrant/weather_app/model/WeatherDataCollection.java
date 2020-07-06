package it.sylwiabrant.weather_app.model;

import it.sylwiabrant.weather_app.controller.WeatherViewController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sylwia Brant
 */
public class WeatherDataCollection {
    private Map<Integer, CurrentWeather> currentWeatherList;
    private Map<Integer, List<ForecastWeather>> forecastList;
    private WeatherViewController observer = null;

    public WeatherDataCollection() {
        this.currentWeatherList = new HashMap<>();
        this.forecastList = new HashMap<>();
    }

    public void loadCityData(CurrentWeather currentWeather, ArrayList<ForecastWeather> forecasts, int index) {
        addCurrentWeather(currentWeather, index);
        addForecasts(forecasts, index);
    }

    /**
     * @param index - index in the HashMap,corresponding to upper (0) or lower (1)
     */
    public void addCurrentWeather(CurrentWeather currentWeather, int index) {
        System.out.println("ładowanie danych do WeatherDataCollection.");
        currentWeatherList.put(index, currentWeather);
    }

    /**
     * Load forecast weather data into ForecastList.
     *
     * @param index - index in the HashMap,corresponding to upper (0) or lower (1)
     *              part of the window
     */
    public void addForecasts(ArrayList<ForecastWeather> forecastsPerCity, int index) {
        forecastList.put(index, forecastsPerCity);
    }

    /**
     * Removes old current weather object and forecastsObject from collections at
     * <index> position and invokes loading new data object to said position
     */
    public void updateCityData(CurrentWeather currentWeather, ArrayList<ForecastWeather> forecasts, int index) {

        currentWeatherList.remove(index);
        forecastList.remove(index);

        addCurrentWeather(currentWeather, index);
        addForecasts(forecasts, index);
        notifyAboutDataUpdate(index);
    }

    /**
     * Removes all data from CurrentWeatherList and ForecastList if it's not already empty
     */
    public void removeData() {
        if (!currentWeatherList.isEmpty())
            currentWeatherList.clear();
        if (!forecastList.isEmpty())
            forecastList.clear();
    }

    private void notifyAboutDataUpdate(int index) {
        observer.updateCityView(index);
    }

    /**
     * Removes old current weather object from HashMap at <index> position and invokes
     * loding new data object to said position
     */
    public void registerObserver(WeatherViewController controller) {
        if (observer == null)
            this.observer = controller;
    }

    /**
     * @return ArrayList<CurrentWeather> currentWeatherList
     */
    public Map<Integer, CurrentWeather> getCurrentWeathers() {
        return currentWeatherList;
    }

    /**
     * @return ArrayList<ArrayList < CurrentWeather>> forecastList
     */
    public Map<Integer, List<ForecastWeather>> getForecasts() {
        return forecastList;
    }

    /**
     * @return CurrentWeather currentWeatherList
     */
    public CurrentWeather getSingleCityWeather(int index) {
        return currentWeatherList.get(index);
    }

    /**
     * @return ArrayList<CurrentWeather> forecastList
     */
    public List<ForecastWeather> getSingleCityForecasts(int index) {
        return forecastList.get(index);
    }
}
