package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CitySearchResult;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
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

    public WeatherFetchingCoordinator(WeatherDataCollection weatherData,
                                      OWMWebServiceClient client,
                                      FromJsonConverter converter) {
        this.weatherData = weatherData;
        this.client = client;
        this.converter = converter;
    }

    /**
     * Checks if the overall process of connecting and obtaining response from OWM API
     * was successful. This function is used at the start of the program to fetch data
     * for the first time. Catches exceptions and converts them into appropriate CitySearchResult.
     *
     * @return CitySearchResult
     */
    public CitySearchResult fetchCityWeatherData(String city, int index) {
        try {
            List<String> allFutures = client.queryAPI(city);
            JSONObject currentWeather = new JSONObject(allFutures.get(0));
            JSONObject forecasts = new JSONObject(allFutures.get(1));
            CitySearchResult result = validateFetchedData(currentWeather);
            if (result == CitySearchResult.SUCCESS) {
                weatherData.loadCityData(converter.toCurrentWeatherObject(currentWeather),
                        converter.toForecastsArray(forecasts), index);
            }
            return result;
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Cause=" + e.getCause());
            return CitySearchResult.FAILED_BY_UNEXPECTED_ERROR;
        }
    }

    /**
     * Checks if the overall process of connecting and obtaining response from OWM API
     * was successful. This function is used when changing one the already obtained locations.
     *
     * @return CitySearchResult
     */
    public CitySearchResult refetchCityWeatherData(String city, int index) {
        try {
            List<String> allFutures = client.queryAPI(city);
            JSONObject currentWeather = new JSONObject(allFutures.get(0));
            JSONObject forecasts = new JSONObject(allFutures.get(1));
            CitySearchResult result = validateFetchedData(currentWeather);
            if (result == CitySearchResult.SUCCESS) {
                weatherData.updateCityData(converter.toCurrentWeatherObject(currentWeather),
                        converter.toForecastsArray(forecasts), index);
            }
            return result;
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Cause=" + e.getCause());
            return CitySearchResult.FAILED_BY_UNEXPECTED_ERROR;
        }
    }

    private CitySearchResult validateFetchedData(JSONObject currentWeather) {
        try {
            int result = toInt(currentWeather.get("cod"));
            if (result == 200) {
                return CitySearchResult.SUCCESS;
            } else {
                return CitySearchResult.FAILED_BY_CITY_NAME;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return CitySearchResult.FAILED_BY_UNEXPECTED_ERROR;
        }
    }

    private int toInt(Object object) {
        if (object instanceof Integer) {
            return (Integer) object;
        } else {
            return Integer.parseInt(object.toString());
        }
    }
}
