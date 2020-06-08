package it.sylwiabrant.weather_app.model;

import it.sylwiabrant.weather_app.controller.WeatherFetcherService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sylwia Brant
 */
public class WeatherDataCollection {
    private ArrayList<CurrentWeather> currentWeatherList;
    private ArrayList<ArrayList<ForecastWeather>> forecastList;
    private WeatherFetcherService fetcher;

    public WeatherDataCollection() {
        this.currentWeatherList = new ArrayList<CurrentWeather>();
        this.forecastList = new ArrayList<ArrayList<ForecastWeather>>();
        this.fetcher = new WeatherFetcherService(this);
        System.out.println("Tworzenie WeatherDataCollection.");
    }

    public void loadCurrentData(JSONObject jsonObject) {
      //  System.out.println("ładowanie danych do WeatherDataCollection.");
        double snow = 0, rain = 0, temp = 0, windChill = 0; int clouds = 0; String windDirection = "";

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
        cond.setVisibility(jsonObject.get("visibility").toString());
        cond.setClouds(String.valueOf(clouds));
        cond.setIcon(getConditionsIcon(cond.getMain(), clouds));
        System.out.println(cond);
        currentWeatherList.add(cond);
    }

    private String roundDoubleToString(double doubleValue, int decimalPlaces) {
        String strValue = "";
     //   DecimalFormat df;
        if (decimalPlaces == 1) {
            DecimalFormat df = new DecimalFormat("#.#");
            strValue = df.format(doubleValue);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            strValue = df.format(doubleValue);
        }
        return strValue;
    }

    public void loadForecast(JSONArray jsonArray) throws IOException {
        /*API response provides 40 data sets of every 3h weather forecast
        * Extract only data sets for next 4 days */
        int startingSet = ((24-LocalTime.now().getHour())/3);
        int endingSet = startingSet+(4*8);
        System.out.println("startingSet=" + startingSet + ", time = "+ LocalTime.now().getHour());

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

    private ForecastWeather extractDailyConditions(ArrayList<JSONObject> day) {
        double tempTemp, minTemp = 100, maxTemp = -100, rain = 0.00, snow = 0.00; int clouds= 0; long timestamp = 0;
        String main = "", description = "", pressure ="", windSpeed="", windDirection="", date="",
                icon="";

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
                System.out.println(rain);
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

    public ArrayList<CurrentWeather> getCurrentWeathers() {
        return currentWeatherList;
    }

    public ArrayList<ArrayList<ForecastWeather>> getForecasts() {
        return forecastList;
    }

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
}
