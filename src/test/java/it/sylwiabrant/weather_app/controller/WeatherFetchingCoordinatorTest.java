package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CurrentWeather;
import it.sylwiabrant.weather_app.model.ForecastWeather;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/**
 * Created by Sylwia Brant
 */
@ExtendWith(MockitoExtension.class)
class WeatherFetchingCoordinatorTest {
    private WeatherDataCollection weatherData = mock(WeatherDataCollection.class);
    private FromJsonConverter converter = spy(new FromJsonConverter());
    private OWMWebServiceClient client = mock(OWMWebServiceClient.class);
    private WeatherFetchingCoordinator coordinator = new WeatherFetchingCoordinator(
            weatherData, client, converter);
    @Captor
    private ArgumentCaptor<CurrentWeather> currentWeatherCaptor;
    @Captor
    private ArgumentCaptor<ArrayList<ForecastWeather>> forecastsCaptor;
    @Captor
    private ArgumentCaptor<Integer> indexCaptor;

    @Test
    void successfullyFetchedDataShouldBeSentToLoading() throws IOException {
        //given
        when(client.queryAPI("City")).thenReturn(getValidAPIResponse());
        //when
        coordinator.fetchCityWeatherData("City", 0);
        //then
        then(weatherData).should().loadCityData(
                currentWeatherCaptor.capture(),
                forecastsCaptor.capture(),
                indexCaptor.capture());
        assertEquals("Londyn", currentWeatherCaptor.getValue().getCity());
        assertEquals("29,7", forecastsCaptor.getValue().get(0).getMaxTemp());
        assertEquals(4, forecastsCaptor.getValue().size());
        assertEquals(0, indexCaptor.getValue());
    }

    @Test
    void successfullyReFetchedDataShouldBeSentToLoading() throws IOException {
        //given
        when(client.queryAPI("City")).thenReturn(getValidAPIResponse());
        //when
        coordinator.fetchCityWeatherData("City", 0);
        //then
        then(weatherData).should().loadCityData(
                currentWeatherCaptor.capture(),
                forecastsCaptor.capture(),
                indexCaptor.capture());
        assertEquals("Londyn", currentWeatherCaptor.getValue().getCity());
        assertEquals("29,7", forecastsCaptor.getValue().get(0).getMaxTemp());
        assertEquals(4, forecastsCaptor.getValue().size());
        assertEquals(0, indexCaptor.getValue());
    }

    private List<String> getValidAPIResponse() throws IOException {
        List<String> jsonStrings = new ArrayList<>();
        String currentWeather = Files.readString(Paths.get("src/test/resources/LondonCurrent.json"),
                StandardCharsets.UTF_8);
        String forecastWeather = Files.readString(Paths.get("src/test/resources/LondonForecast.json"),
                StandardCharsets.UTF_8);
        jsonStrings.add(currentWeather);
        jsonStrings.add(forecastWeather);
        return jsonStrings;
    }
}