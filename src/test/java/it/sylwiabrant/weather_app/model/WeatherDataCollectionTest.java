package it.sylwiabrant.weather_app.model;

import it.sylwiabrant.weather_app.controller.FromJsonConverter;
import it.sylwiabrant.weather_app.controller.OWMWebServiceClient;
import it.sylwiabrant.weather_app.controller.WeatherFetchingCoordinator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.spy;

/**
 * Created by Sylwia Brant
 */
class WeatherDataCollectionTest {
  //  private static WeatherDataCollection weatherData;
    WeatherFetchingCoordinator coordinator = spy(new WeatherFetchingCoordinator(client, fromJsonConverter));
    WeatherDataCollection weatherData = new WeatherDataCollection(coordinator);
    @Mock
    private static FromJsonConverter fromJsonConverter;
    @Mock
    private static OWMWebServiceClient client;
    @BeforeEach
    void cleanUp(){
        weatherData.getCurrentWeathers().clear();
        weatherData.getForecasts().clear();
    }

    @Test
    void currentWeatherCollectionShouldBeEmptyAfterCreation(){
        //given
        //creation of class instance in private variable
        //when
        // then
        assertThat(weatherData.getCurrentWeathers().size(), equalTo(0));
    }

    @Test
    void forecastsCollectionShouldBeEmptyAfterCreation(){
        //given
    //    WeatherDataCollection weatherData = new WeatherDataCollection(coordinator);
        //when
        // then
        assertThat(weatherData.getForecasts().size(), equalTo(0));
    }

    @Test
    void currentWeatherCollectionShouldBeEmptyAfterRemovingElements() {
        //given
        CurrentWeather currentWeather = new CurrentWeather();
       // WeatherDataCollection weatherData = new WeatherDataCollection(coordinator);
        weatherData.addCurrentWeather(currentWeather,0);
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
     //   WeatherDataCollection weatherData = new WeatherDataCollection(coordinator);
        weatherData.addForecasts(forecasts,0);
        //when
        weatherData.removeData();
        //then
        assertThat(weatherData.getForecasts().size(), equalTo(0));
    }

    @Test
    void currentWeatherShouldBeAddedToCorrespondingIndex(){
        //given
        CurrentWeather currentWeather = new CurrentWeather();
        //when
        weatherData.addCurrentWeather(currentWeather,1);
        //then
        assertThat(weatherData.getSingleCityWeather(1), is(currentWeather));
    }

    @Test
    void forecastShouldBeAddedToCorrespondingIndex(){
        //given
        ArrayList<ForecastWeather> forecasts = new ArrayList<>();
        //when
        weatherData.addForecasts(forecasts,1);
        //then
        assertThat(weatherData.getSingleCityForecasts(1), is(forecasts));
    }
}