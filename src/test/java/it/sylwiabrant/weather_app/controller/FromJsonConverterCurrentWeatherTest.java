package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CurrentWeather;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Sylwia Brant
 */
class FromJsonConverterCurrentWeatherTest {

    private static CurrentWeather currentWeather;

    @BeforeAll
    static void setJsonsForTesting() throws IOException {
        String currentWeatherJsonStr = Files.readString(Paths.get("src/test/resources/LondonCurrent.json"),
                StandardCharsets.UTF_8);
        FromJsonConverter converter = new FromJsonConverter();
        currentWeather = converter.toCurrentWeatherObject(new JSONObject(currentWeatherJsonStr));
    }

    @Test
    void cityIsSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getCity(), equalTo("Londyn"));
    }

    @Test
    void countryIsSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getCountry(), equalTo("EN"));
    }

    @Test
    void windSpeedIsSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getWindSpeed(), equalTo("1.8"));
    }

    @Test
    void windDirectionIsSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getWindDirection(), equalTo("W"));
    }

    @Test
    void temperatureIsSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getTemp(), equalTo("18"));
    }

    @Test
    void windChillIsSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getWindChill(), equalTo("15"));
    }

    @Test
    void snowIsSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getSnow(), equalTo("0"));
    }

    @Test
    void rainIsSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getRain(), equalTo("0"));
    }

    @Test
    void cloudsAreSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getClouds(), equalTo("1"));
    }

    @Test
    void visibilityIsSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getVisibility(), equalTo("13044"));
    }

    @Test
    void humidityIsSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getHumidity(), equalTo("84"));
    }

    @Test
    void pressureIsSetCorrectly() {
        //given //when //then
        assertThat(currentWeather.getPressure(), equalTo("1011"));
    }

    @Test
    void iconIsSetCorrectly() {
        //given //when //then
        String path = String.valueOf(FromJsonConverterCurrentWeatherTest.class.getResource(
                "/it/sylwiabrant/weather_app/Icons/rain.png"));
        assertThat(currentWeather.getIcon(), equalTo(path));
    }
}
