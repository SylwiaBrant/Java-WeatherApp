package it.sylwiabrant.weather_app.model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/** Created by Sylwia Brant */
public class WeatherDataCollection {
    private ArrayList<CurrentWeather> currentWeatherList;
    private ArrayList<ArrayList<ForecastWeather>> forecastList;

    public WeatherDataCollection() {
        this.currentWeatherList = new ArrayList<CurrentWeather>();
        this.forecastList = new ArrayList<ArrayList<ForecastWeather>>();
        System.out.println("Tworzenie WeatherDataCollection.");
    }

    /** Load current weather data to CurrentWeather object and push it into
     * currentWeatherList. API response provides 40 data sets of every 3h
     * weather forecast. Extract only data sets for next 4 days: 8 measurement
     * sets per day
     * @param JSONArray jsonArray - array of 40 data sets downloaded from API
     */
    public void loadCurrentData(JSONObject jsonObject) {
      //  System.out.println("ładowanie danych do WeatherDataCollection.");
        double snow = 0, rain = 0, temp = 0, windChill = 0; int clouds = 0; String windDirection = "", visibility="";

        CurrentWeather cond = new CurrentWeather();

        temp = ((Number)jsonObject.getJSONObject("main").get("temp")).doubleValue();
        windChill = ((Number) jsonObject.getJSONObject("main").get("feels_like")).doubleValue();

        if (jsonObject.has("snow")) {
            if(jsonObject.getJSONObject("snow").has("1h"))
                snow = ((Number) jsonObject.getJSONObject("snow").get("1h")).intValue();
            else
                snow = ((Number) jsonObject.getJSONObject("snow").get("3h")).intValue();
        }

        if (jsonObject.has("rain")) {
            if(jsonObject.getJSONObject("rain").has("1h"))
                rain += ((Number) jsonObject.getJSONObject("rain").get("1h")).doubleValue();
            else
                rain += ((Number) jsonObject.getJSONObject("rain").get("3h")).doubleValue();
        }

        if(jsonObject.has("visibility")) {
            visibility = jsonObject.get("visibility").toString();
        }

        if(jsonObject.getJSONObject("wind").has("deg")){
            windDirection = windDirToLetters(((Number) jsonObject.getJSONObject("wind").get("deg")).intValue());
        }
        clouds = ((Number) jsonObject.getJSONObject("clouds").get("all")).intValue();

        cond.setCity(jsonObject.get("name").toString());
        cond.setCountry(jsonObject.getJSONObject("sys").get("country").toString());
        cond.setMain(jsonObject.getJSONArray("weather").getJSONObject(0).get("main").toString());
        cond.setDescription(jsonObject.getJSONArray("weather").getJSONObject(0).get("description").toString());
        cond.setTemp(roundDoubleToString(temp, 1));
        cond.setWindChill(roundDoubleToString(windChill,1));
        cond.setSnow(roundDoubleToString(snow,2));
        cond.setRain(roundDoubleToString(rain,2));
        cond.setPressure(jsonObject.getJSONObject("main").get("pressure").toString());
        cond.setHumidity(jsonObject.getJSONObject("main").get("humidity").toString());
        cond.setWindSpeed(jsonObject.getJSONObject("wind").get("speed").toString());
        cond.setWindDirection(windDirection);
        cond.setVisibility(visibility);
        cond.setClouds(String.valueOf(clouds));
        cond.setIcon(getConditionsIcon(cond.getMain(), clouds));
        System.out.println(cond);
        currentWeatherList.add(cond);
    }

    private String roundDoubleToString(double doubleValue, int decimalPlaces) {
        if (decimalPlaces == 1) {
            DecimalFormat df = new DecimalFormat("#.#");
            return df.format(doubleValue);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(doubleValue);
        }
    }

    /** Load forecast weather data to ForecastWeather object and push it into
    * ForecastList. API response provides 40 data sets of every 3h weather forecast.
    * Extract only data sets for next 4 days: 8 measurement sets per day
    * @param jsonArray - array of 40 data sets downloaded from API
    * @return void
    */
    public void loadForecast(JSONArray jsonArray) throws IOException {
        int startingSet = ((24-LocalTime.now().getHour())/3);
        int endingSet = startingSet+(4*8);

        ArrayList<ForecastWeather> forecastsPerCity = new ArrayList<ForecastWeather>();
        while(startingSet != endingSet){
            ArrayList <JSONObject> dayForecast = new ArrayList<JSONObject>();
            for(int i = 0; i < 8; i++) {
                JSONObject forecastChunk = (JSONObject) jsonArray.get(startingSet);
                dayForecast.add(forecastChunk);
                startingSet++;
            }
            ForecastWeather cond = extractDailyConditions(dayForecast);
            forecastsPerCity.add(cond);
        }
        forecastList.add(forecastsPerCity);
    }

    /** From 8 measurements every 3 hours in a day, get highest and lowest temperature,
    * cumulative rain and snow, rest of required values: pressure, wind speed and direction,
    * date, icon, conditions description main and detailed for noon and set them on ForecastWeather object.
    * @param day - array of 8 data sets for 1 whole day
    * @return ForecastWeather - object containing forecast weather for 1 whole day
    */
    private ForecastWeather extractDailyConditions(ArrayList<JSONObject> day) {
        double tempTemp, minTemp = 100, maxTemp = -100, rain = 0.00, snow = 0.00; int clouds = 0; long timestamp = 0;
        String main = "", description = "", pressure = "", windSpeed = "", windDirection = "", date = "",
                icon = "";

        for(int i = 0; i < 8; i++) {
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
            if (i % 4 == 0) {
                timestamp = ((Number) forecastChunk.get("dt")).longValue();
                LocalDate localDate = Instant.ofEpochMilli(timestamp*1000).atZone(ZoneId.systemDefault()).toLocalDate();;
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
        forecast.setMaxTemp(roundDoubleToString(maxTemp,1));
        forecast.setMinTemp(roundDoubleToString(minTemp,1));
        forecast.setDate(date);
        forecast.setDescription(description);
        forecast.setRain(roundDoubleToString(rain,2));
        forecast.setSnow(roundDoubleToString(snow, 2));
        forecast.setPressure(pressure);
        forecast.setWindSpeed(windSpeed);
        forecast.setWindDirection(windDirection);
        forecast.setClouds(String.valueOf(clouds));

        return forecast;
    }

    /** @return ArrayList<CurrentWeather> currentWeatherList */
    public ArrayList<CurrentWeather> getCurrentWeathers() {
        return currentWeatherList;
    }

    /** @return ArrayList<ArrayList<CurrentWeather>> forecastList */
    public ArrayList<ArrayList<ForecastWeather>> getForecasts() {
        return forecastList;
    }
    /**
     * Takes degrees at which wind blows and converts them to intercardinal directions
     * @param deg - degrees at which wind blows
     * @return intercardinal direction string
     */
    private String windDirToLetters(int deg) {
        if (deg >= 0.0 && deg < 22.5) return "N";
        if (deg >= 337.5 && deg < 360) return "N";
        if (deg >= 22.5 && deg < 67.5) return "NE";
        if (deg >= 67.5 && deg < 112.5) return "E";
        if (deg >= 112.5 && deg < 157.5) return "SE";
        if (deg >= 157.5 && deg < 202.5) return "S";
        if (deg >= 202.5 && deg < 247.5) return "SW";
        if (deg >= 247.5 && deg < 292.5) return "W";
        if (deg >= 292.5 && deg < 337.5) return "NW";
        return "";
    }

    /**
     * Takes main description of conditions and cloudiness and returns
     * corresponding icon path
     * @param conditions - main description of conditions
     * @param clouds - cloudiness in %
     * @return icon path
     */
    private String getConditionsIcon(String conditions, int clouds){
        String imgPath;
        switch (conditions){
            case "Clear":
                imgPath = "sun.png";
                break;
            case "Clouds":
                if(clouds <= 25)
                    imgPath = "sunclouds.png";
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
        return String.valueOf(this.getClass().getResource("/it/sylwiabrant/weather_app/Icons/"+imgPath));
    }

    /**
     * Removes all data from CurrentWeatherList and ForecastList if it's not already empty
     * @return void
     */
    public void removeData() {
        if(!currentWeatherList.isEmpty())
            currentWeatherList.clear();
        if(!forecastList.isEmpty())
            forecastList.clear();
    }
}
