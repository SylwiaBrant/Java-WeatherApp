package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.ForecastWeather;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Sylwia Brant
 */
public class FromJsonConverterForecastWeatherTest {

    private static ArrayList<ForecastWeather> forecastWeather;

    @BeforeAll
    static void setJsonsForTesting() throws IOException {
        String forecastWeatherJsonStr = Files.readString(Paths.get("src/test/resources/LondonForecast.json"),
                StandardCharsets.UTF_8);
        FromJsonConverter converter = new FromJsonConverter();
        forecastWeather =
                converter.toForecastsArray(new JSONObject(forecastWeatherJsonStr));
    }

    @Test
    void windSpeedIsSetCorrectly() {
        //given //when //then
        assertThat(forecastWeather.get(0).getWindSpeed(), equalTo("1.94"));
        assertThat(forecastWeather.get(1).getWindSpeed(), equalTo("1.06"));
        assertThat(forecastWeather.get(2).getWindSpeed(), equalTo("2.43"));
        assertThat(forecastWeather.get(3).getWindSpeed(), equalTo("1.97"));
    }

    @Test
    void windDirectionIsSetCorrectly() {
        //given //when //then
        assertThat(forecastWeather.get(0).getWindDirection(), equalTo("NW"));
        assertThat(forecastWeather.get(1).getWindDirection(), equalTo("W"));
        assertThat(forecastWeather.get(2).getWindDirection(), equalTo("SW"));
        assertThat(forecastWeather.get(3).getWindDirection(), equalTo("NW"));
    }

    @Test
    void maxTemperatureIsSetCorrectly() {
        //given //when //then
        assertThat(forecastWeather.get(0).getMaxTemp(), equalTo("29,7"));
        assertThat(forecastWeather.get(1).getMaxTemp(), equalTo("28,7"));
        assertThat(forecastWeather.get(2).getMaxTemp(), equalTo("29,6"));
        assertThat(forecastWeather.get(3).getMaxTemp(), equalTo("27,8"));
    }

    @Test
    void minTemperatureIsSetCorrectly() {
        //given //when //then
        assertThat(forecastWeather.get(0).getMinTemp(), equalTo("21,9"));
        assertThat(forecastWeather.get(1).getMinTemp(), equalTo("20,2"));
        assertThat(forecastWeather.get(2).getMinTemp(), equalTo("20,2"));
        assertThat(forecastWeather.get(3).getMinTemp(), equalTo("20,1"));
    }

    @Test
    void snowIsSetCorrectly() {
        //given //when //then
        assertThat(forecastWeather.get(0).getSnow(), equalTo("0"));
        assertThat(forecastWeather.get(1).getSnow(), equalTo("0"));
        assertThat(forecastWeather.get(2).getSnow(), equalTo("0"));
        assertThat(forecastWeather.get(3).getSnow(), equalTo("0"));
    }

    @Test
    void rainIsSetCorrectly() {
        //given //when //then
        assertThat(forecastWeather.get(0).getRain(), equalTo("0"));
        assertThat(forecastWeather.get(1).getRain(), equalTo("1,29"));
        assertThat(forecastWeather.get(2).getRain(), equalTo("8,37"));
        assertThat(forecastWeather.get(3).getRain(), equalTo("5,7"));
    }

    @Test
    void cloudsAreSetCorrectly() {
        //given //when //then
        assertThat(forecastWeather.get(0).getClouds(), equalTo("44"));
        assertThat(forecastWeather.get(1).getClouds(), equalTo("48"));
        assertThat(forecastWeather.get(2).getClouds(), equalTo("88"));
        assertThat(forecastWeather.get(3).getClouds(), equalTo("0"));
    }

    @Test
    void pressureIsSetCorrectly() {
        //given //when //then
        assertThat(forecastWeather.get(0).getPressure(), equalTo("996.54"));
        assertThat(forecastWeather.get(1).getPressure(), equalTo("996.2"));
        assertThat(forecastWeather.get(2).getPressure(), equalTo("993.9"));
        assertThat(forecastWeather.get(3).getPressure(), equalTo("994.05"));
    }

    @Test
    void iconIsSetCorrectly() {
        //given //when //then
        String path0 = String.valueOf(FromJsonConverterCurrentWeatherTest.class.getResource(
                "/it/sylwiabrant/weather_app/Icons/clouds.png"));
        assertThat(forecastWeather.get(0).getIcon(), equalTo(path0));
        String path1 = String.valueOf(FromJsonConverterCurrentWeatherTest.class.getResource(
                "/it/sylwiabrant/weather_app/Icons/rain.png"));
        assertThat(forecastWeather.get(1).getIcon(), equalTo(path1));
        String path2 = String.valueOf(FromJsonConverterCurrentWeatherTest.class.getResource(
                "/it/sylwiabrant/weather_app/Icons/rain.png"));
        assertThat(forecastWeather.get(2).getIcon(), equalTo(path2));
        String path3 = String.valueOf(FromJsonConverterCurrentWeatherTest.class.getResource(
                "/it/sylwiabrant/weather_app/Icons/sun.png"));
        assertThat(forecastWeather.get(3).getIcon(), equalTo(path3));
    }
}

