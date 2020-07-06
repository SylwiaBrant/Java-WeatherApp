package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CurrentWeather;
import it.sylwiabrant.weather_app.model.ForecastWeather;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by Sylwia Brant
 */
public class FromJsonConverter {

    /**
     * Load current weather data into CurrentWeather object and push it into
     * currentWeatherList. Checking if data fetched from API contains all needed fields,
     * set them as the properties of the object
     *
     * @param jsonObject - current weather data set downloaded from API
     */

    public CurrentWeather toCurrentWeatherObject(JSONObject jsonObject) {
        double snow = 0, rain = 0, temp, windChill;
        int clouds;
        String windDirection = "", visibility = "";

        CurrentWeather cond = new CurrentWeather();

        temp = ((Number) jsonObject.getJSONObject("main").get("temp")).doubleValue();
        windChill = ((Number) jsonObject.getJSONObject("main").get("feels_like")).doubleValue();

        if (jsonObject.has("snow")) {
            if (jsonObject.getJSONObject("snow").has("1h"))
                snow = ((Number) jsonObject.getJSONObject("snow").get("1h")).intValue();
            else
                snow = ((Number) jsonObject.getJSONObject("snow").get("3h")).intValue();
        }

        if (jsonObject.has("rain")) {
            if (jsonObject.getJSONObject("rain").has("1h"))
                rain += ((Number) jsonObject.getJSONObject("rain").get("1h")).doubleValue();
            else
                rain += ((Number) jsonObject.getJSONObject("rain").get("3h")).doubleValue();
        }

        if (jsonObject.has("visibility")) {
            visibility = jsonObject.get("visibility").toString();
        }

        if (jsonObject.getJSONObject("wind").has("deg")) {
            windDirection = windDirToLetters(((Number) jsonObject.getJSONObject("wind").get("deg")).intValue());
        }
        clouds = ((Number) jsonObject.getJSONObject("clouds").get("all")).intValue();

        cond.setCity(jsonObject.get("name").toString());
        cond.setCountry(jsonObject.getJSONObject("sys").get("country").toString());
        cond.setMain(jsonObject.getJSONArray("weather").getJSONObject(0).get("main").toString());
        cond.setDescription(jsonObject.getJSONArray("weather").getJSONObject(0).get("description").toString());
        cond.setTemp(roundDoubleToString(temp, 1));
        cond.setWindChill(roundDoubleToString(windChill, 1));
        cond.setSnow(roundDoubleToString(snow, 2));
        cond.setRain(roundDoubleToString(rain, 2));
        cond.setPressure(jsonObject.getJSONObject("main").get("pressure").toString());
        cond.setHumidity(jsonObject.getJSONObject("main").get("humidity").toString());
        cond.setWindSpeed(jsonObject.getJSONObject("wind").get("speed").toString());
        cond.setWindDirection(windDirection);
        cond.setVisibility(visibility);
        cond.setClouds(String.valueOf(clouds));
        cond.setIcon(getConditionsIcon(cond.getMain(), clouds));

        return cond;
    }

    /**
     * Takes main description of conditions and cloudiness and returns
     * corresponding icon path
     *
     * @param conditions - main description of conditions
     * @param clouds     - cloudiness in %
     * @return icon path
     */
    private String getConditionsIcon(String conditions, int clouds) {
        String imgPath;
        switch (conditions) {
            case "Clear":
                imgPath = "sun.png";
                break;
            case "Clouds":
                if (clouds <= 25)
                    imgPath = "suncloud.png";
                else
                    imgPath = "clouds.png";
                break;
            case "Rain":
            case "Drizzle":
                imgPath = "rain.png";
                break;
            case "Thunderstorm":
                imgPath = "storm.png";
                break;
            case "Snow":
                imgPath = "snow.png";
                break;
            case "Mist":
            case "Fog":
            case "Haze":
                imgPath = "mist.png";
                break;
            default:
                imgPath = "clouds.png";
        }

        return String.valueOf(FromJsonConverter.class.getResource("/it/sylwiabrant/weather_app" +
                "/Icons/" + imgPath));
    }

    /**
     * Takes degrees at which wind blows and converts them to intercardinal directions
     *
     * @param deg - degrees at which wind blows
     * @return intercardinal direction string
     */
    private String windDirToLetters(int deg) {
        if (deg >= 112.5 && deg < 247.5) {
            if (deg >= 112.5 && deg < 157.5) return "SE";
            else if (deg >= 157.5 && deg < 202.5) return "S";
            else return "SW";
        } else if (deg >= 247.5 && deg < 292.5) return "W";
        else if (deg >= 67.5 && deg < 112.5) return "E";
        else {
            if (deg >= 292.5 && deg < 337.5) return "NW";
            if (deg >= 22.5 && deg < 67.5) return "NE";
            else return "N";
        }
    }

    /**
     * Convert double value to string with 1 or 2 decimal places
     *
     * @param doubleValue   - conditions double value fetched from API
     * @param decimalPlaces - number of decimalPlaces wanted in string
     * @return String - double converted to String
     */
    private String roundDoubleToString(double doubleValue, int decimalPlaces) {
        if (decimalPlaces == 1) {
            DecimalFormat df = new DecimalFormat("#.#");
            return df.format(doubleValue);
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(doubleValue);
        }
    }

    /**
     * Load forecast weather data to ForecastWeather object.
     * API response provides 40 data sets of every 3h weather forecast.
     * Extract only data sets for next 4 days: 8 measurement sets per day
     *
     * @param jsonArray - array of 40 data sets downloaded from API
     */
    public ArrayList<ForecastWeather> toForecastsArray(JSONArray jsonArray) {
        int startingSet = ((24 - LocalTime.now().getHour()) / 3);
        int endingSet = startingSet + (4 * 8);

        ArrayList<ForecastWeather> forecastsPerCity = new ArrayList<>();
        while (startingSet != endingSet) {
            ArrayList<JSONObject> dayForecast = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                JSONObject forecastChunk = (JSONObject) jsonArray.get(startingSet);
                dayForecast.add(forecastChunk);
                startingSet++;
            }
            ForecastWeather cond = extractDailyConditions(dayForecast);
            forecastsPerCity.add(cond);
        }
        return forecastsPerCity;
    }

    /**
     * From 8 measurements every 3 hours in a day, get highest and lowest temperature,
     * cumulative rain and snow, rest of required values: pressure, wind speed and direction,
     * date, icon, conditions description main and detailed for noon and set them on ForecastWeather object.
     *
     * @param day - array of 8 data sets for 1 whole day
     * @return ForecastWeather - object containing forecast weather for 1 whole day
     */
    private ForecastWeather extractDailyConditions(ArrayList<JSONObject> day) {
        double tempTemp, minTemp = 100, maxTemp = -100, rain = 0.00, snow = 0.00;
        int clouds = 0;
        long timestamp;
        String main = "", description = "", pressure = "", windSpeed = "", windDirection = "", date = "",
                icon = "";

        for (int i = 0; i < 8; i++) {
            JSONObject forecastChunk = day.get(i);
            /*get highest and lowest temp per day*/
            tempTemp = ((Number) forecastChunk.getJSONObject("main").get("temp")).doubleValue();
            if (tempTemp < minTemp)
                minTemp = tempTemp;
            if (tempTemp > maxTemp)
                maxTemp = tempTemp;
            /*get cumulative rain and snow per day*/
            if (forecastChunk.has("snow")) {
                snow += ((Number) forecastChunk.getJSONObject("snow").get("3h")).doubleValue();
            }
            if (forecastChunk.has("rain")) {
                rain += ((Number) forecastChunk.getJSONObject("rain").get("3h")).doubleValue();
            }
            /*get rest of conditions for noon*/
            if (i % 6 == 0) {
                timestamp = ((Number) forecastChunk.get("dt")).longValue();
                LocalDate localDate = Instant.ofEpochMilli(timestamp * 1000).atZone(ZoneId.systemDefault()).toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
                date = localDate.format(formatter);
                main = forecastChunk.getJSONArray("weather").getJSONObject(0).get("main").toString();
                description = forecastChunk.getJSONArray("weather").getJSONObject(0).get("description").toString();
                pressure = forecastChunk.getJSONObject("main").get("pressure").toString();
                /*get wind speed and direction*/
                windSpeed = forecastChunk.getJSONObject("wind").get("speed").toString();
                windDirection =
                        windDirToLetters(((Number) forecastChunk.getJSONObject("wind").get("deg")).intValue());
                clouds = ((Number) forecastChunk.getJSONObject("clouds").get("all")).intValue();
                icon = getConditionsIcon(main, clouds);
            }
        }
        ForecastWeather forecast = new ForecastWeather();
        forecast.setIcon(icon);
        forecast.setMain(main);
        forecast.setDescription(description);
        forecast.setMaxTemp(roundDoubleToString(maxTemp, 1));
        forecast.setMinTemp(roundDoubleToString(minTemp, 1));
        forecast.setDate(date);
        forecast.setDescription(description);
        forecast.setRain(roundDoubleToString(rain, 2));
        forecast.setSnow(roundDoubleToString(snow, 2));
        forecast.setPressure(pressure);
        forecast.setWindSpeed(windSpeed);
        forecast.setWindDirection(windDirection);
        forecast.setClouds(String.valueOf(clouds));

        return forecast;
    }
}
