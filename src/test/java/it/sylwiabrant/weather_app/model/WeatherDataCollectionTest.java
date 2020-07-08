package it.sylwiabrant.weather_app.model;

import it.sylwiabrant.weather_app.controller.BaseController;
import it.sylwiabrant.weather_app.controller.WeatherViewController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by Sylwia Brant
 */
class WeatherDataCollectionTest {
    private final WeatherDataCollection weatherData = new WeatherDataCollection();

    @BeforeEach
    void cleanUp() {
        weatherData.getCurrentWeathers().clear();
        weatherData.getForecasts().clear();
    }

    @Test
    void currentWeatherCollectionShouldBeEmptyAfterRemovingElements() {
        //given
        CurrentWeather currentWeather = new CurrentWeather();
        weatherData.addCurrentWeather(currentWeather, 0);
        //when
        weatherData.removeData();
        //then
        assertThat(weatherData.getCurrentWeathers().size(), equalTo(0));
    }

    @Test
    void forecastsCollectionShouldBeEmptyAfterRemovingElements() {
        //given
        ForecastWeather forecastWeather = new ForecastWeather();
        ArrayList<ForecastWeather> forecasts = new ArrayList<>();
        forecasts.add(forecastWeather);
        weatherData.addForecasts(forecasts, 0);
        //when
        weatherData.removeData();
        //then
        assertThat(weatherData.getForecasts().size(), equalTo(0));
    }

    @Test
    void currentWeatherShouldBeAddedToCorrespondingIndex() {
        //given
        CurrentWeather currentWeather = new CurrentWeather();
        //when
        weatherData.addCurrentWeather(currentWeather, 1);
        //then
        assertThat(weatherData.getSingleCityWeather(1), is(currentWeather));
    }

    @Test
    void forecastShouldBeAddedToCorrespondingIndex() {
        //given
        ArrayList<ForecastWeather> forecasts = new ArrayList<>();
        //when
        weatherData.addForecasts(forecasts, 1);
        //then
        assertThat(weatherData.getSingleCityForecasts(1), is(forecasts));
    }

    @Test
    void observerShouldBeRegistered() {
        //given
        BaseController controller = mock(WeatherViewController.class);
        //when
        weatherData.registerObserver(controller);
        //then
        assertEquals(controller, weatherData.getObserver());
    }
}