package it.sylwiabrant.weather_app.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

/**
 * Created by Sylwia Brant
 */
class WeatherFetchingCoordinatorTest {

    @Mock
//    WeatherFetchingCoordinator coordinator = spy(new WeatherFetchingCoordinator(client, fromJsonConverter));
    private OWMWebServiceClient client = spy(new OWMWebServiceClient());
    @Mock
    private FromJsonConverter converter;

    private static final String API_KEY = "";

    @Test
    void fetchCityWeatherData() {

        WeatherFetchingCoordinator coordinator = new WeatherFetchingCoordinator(client, converter);
        given(client.queryAPI("Londyn")).willThrow(RuntimeException.class);
        //when
        //then
        assertThrows(RuntimeException.class, () -> client.queryAPI("Londyn"));
    }

    @Test
    void refetchCityWeatherData() {
    }

    @Test
    void setDataCollection() {
    }
}