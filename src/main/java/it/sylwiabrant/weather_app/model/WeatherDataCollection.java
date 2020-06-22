package it.sylwiabrant.weather_app.model;

import it.sylwiabrant.weather_app.controller.WeatherFetcherService;
import it.sylwiabrant.weather_app.controller.WeatherViewController;
import java.util.ArrayList;
import java.util.HashMap;

/** Created by Sylwia Brant */
public class WeatherDataCollection {
    private HashMap<Integer, CurrentWeather> currentWeatherList;
    private HashMap<Integer, ArrayList<ForecastWeather>> forecastList;
    private WeatherViewController observer = null;
    private WeatherFetcherService weatherFetcherService;

    public WeatherDataCollection(WeatherFetcherService weatherFetcherService) {
        this.currentWeatherList = new HashMap<>();
        this.forecastList = new HashMap<>();
        this.weatherFetcherService = weatherFetcherService;
        weatherFetcherService.setDataCollection(this);
        System.out.println("Tworzenie WeatherDataCollection.");
    }

    public CitySearchResult fetchCityWeatherData(String city, int index){
        return weatherFetcherService.fetchCityWeatherData(city, index);
    }

    public CitySearchResult changeCityWeatherData(String city, int index){
        return weatherFetcherService.changeCityWeatherData(city, index);
    }

    public void loadCityData(CurrentWeather currentWeather, ArrayList<ForecastWeather> forecasts, int index){
        addCurrentWeather(currentWeather, index);
        addForecasts(forecasts, index);
    }

     /** @param index - index in the HashMap,corresponding to upper (0) or lower (1) */
    public void addCurrentWeather(CurrentWeather currentWeather, int index) {
        System.out.println("ładowanie danych do WeatherDataCollection.");
        currentWeatherList.put(index, currentWeather);
    }

    /** Load forecast weather data into ForecastList.
    * @param index - index in the HashMap,corresponding to upper (0) or lower (1)
    * part of the window
    */
    public void addForecasts(ArrayList<ForecastWeather> forecastsPerCity, int index) {
        forecastList.put(index, forecastsPerCity);
    }

    /**
     * Removes old current weather object and forecastsObject from collections at
     * <index> position and invokes loading new data object to said position
     */
    public void updateCityData(CurrentWeather currentWeather, ArrayList<ForecastWeather> forecasts, int index){
        if(currentWeatherList.size() == 2)
            currentWeatherList.remove(index);
        if(forecastList.size() == 2)
            forecastList.remove(index);

        addCurrentWeather(currentWeather, index);
        addForecasts(forecasts, index);
        notifyAboutDataUpdate(index);
    }

    /** Removes all data from CurrentWeatherList and ForecastList if it's not already empty */
    public void removeData() {
        if(!currentWeatherList.isEmpty())
            currentWeatherList.clear();
        if(!forecastList.isEmpty())
            forecastList.clear();
    }

    /**
     * Removes old current weather object from HashMap at <index> position and invokes
     * loding new data object to said position
     */
    private void notifyAboutDataUpdate(int index){
        observer.updateCityView(index);
    }

    /**
     * Removes old current weather object from HashMap at <index> position and invokes
     * loding new data object to said position
     */
    public void registerObserver(WeatherViewController controller){
        if (observer == null)
            this.observer = controller;
    }

    /** @return ArrayList<CurrentWeather> currentWeatherList */
    public HashMap<Integer, CurrentWeather> getCurrentWeathers() {
        return currentWeatherList;
    }

    /** @return ArrayList<ArrayList<CurrentWeather>> forecastList */
    public HashMap<Integer, ArrayList<ForecastWeather>> getForecasts() {
        return forecastList;
    }

    /** @return CurrentWeather currentWeatherList */
    public CurrentWeather getSingleCityWeather(int index) {
        return currentWeatherList.get(index);
    }

    /** @return ArrayList<CurrentWeather> forecastList */
    public ArrayList<ForecastWeather> getSingleCityForecasts(int index) {
        return forecastList.get(index);
    }
}
