package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CitySearchResult;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;


/**
 * Created by Sylwia Brant
 */
public class WeatherFetchingCoordinator {

    private WeatherDataCollection weatherData;
    private OWMWebServiceClient client;
    private FromJsonConverter converter;

    public WeatherFetchingCoordinator(OWMWebServiceClient client, FromJsonConverter converter) {
        this.client = client;
        this.converter = converter;
    }

    /**
     * Checks if the overall process of connecting and obtaining response from OWM API
     * was successful. This function is used at the start of the program to fetch data
     * for the first time. Catches exceptions and converts them into appropriate CitySearchResult.
     * @return CitySearchResult
     */
    public CitySearchResult fetchCityWeatherData(String city, int index) {
        try {
            List<String> allFutures = client.queryAPI(city);
            JSONObject currentWeather = new JSONObject(allFutures.get(0));
            JSONArray forecasts = new JSONObject(allFutures.get(1)).getJSONArray("list");
            CitySearchResult result = validateFetchedData(currentWeather);
            if(result == CitySearchResult.SUCCESS){
                weatherData.loadCityData(converter.toCurrentWeatherObject(currentWeather),
                        converter.toForecastsArray(forecasts), index);
            }
            return result;
        } catch (RuntimeException e){
            e.printStackTrace();
            System.out.println("Cause=" + e.getCause());
            return CitySearchResult.FAILED_BY_NETWORK;
        }
    }

    /**
     * Checks if the overall process of connecting and obtaining response from OWM API
     * was successful. This function is used when changing one the already obtained locations.
     * @return CitySearchResult
     */
    public CitySearchResult refetchCityWeatherData(String city, int index) {
        try {
            List<String> allFutures = client.queryAPI(city);
            JSONObject currentWeather = new JSONObject(allFutures.get(0));
            JSONArray forecasts = new JSONObject(allFutures.get(1)).getJSONArray("list");
            CitySearchResult result = validateFetchedData(currentWeather);
            if(result == CitySearchResult.SUCCESS){
                weatherData.updateCityData(converter.toCurrentWeatherObject(currentWeather),
                        converter.toForecastsArray(forecasts), index);
            }
            return result;
        } catch (RuntimeException e){
            e.printStackTrace();
            System.out.println("Cause=" + e.getCause());
            return CitySearchResult.FAILED_BY_UNEXPECTED_ERROR;
        }
    }
     /**
     * Checks if the status code in JSON from OWM API was successful 200 or other which
     *  indicates something's wrong with location string provided by the user
     * @return CitySearchResult
     */
    private CitySearchResult validateFetchedData(JSONObject currentWeather) {
        System.out.println("Validowanie sfetchowanych danych i przesyłanie do WeatherDataCollection.");
        try{
            int result = toInt(currentWeather.get("cod"));
            if (result == 200) {
                return CitySearchResult.SUCCESS;
            } else {
                System.out.println("Nie ma takiej miejscowości. Error 404.");
                return CitySearchResult.FAILED_BY_CITYNAME;
            }
        } catch(JSONException e) {
            e.printStackTrace();
            return CitySearchResult.FAILED_BY_UNEXPECTED_ERROR;
        }
    }
    /**
     * Openweathermap returns result code as string or as integer depending on code
     * Function checks if code is an instance of an int or a string and returns always
     * an int.
     * @return int - status code
     */
    private int toInt(Object object){
        if (object instanceof Integer)
            return (Integer) object;
        else
            return Integer.parseInt(object.toString());
    }

    /**
     * Creates a two-way connection between WeatherDataCollection and WeatherFetchingCoordinator classes
     */
    public void setDataCollection(WeatherDataCollection weatherDataCollection) {
        this.weatherData = weatherDataCollection;
    }
}
