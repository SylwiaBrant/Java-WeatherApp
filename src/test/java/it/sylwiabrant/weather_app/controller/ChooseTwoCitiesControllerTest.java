package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static it.sylwiabrant.weather_app.controller.Messages.ERROR;
import static it.sylwiabrant.weather_app.model.CitySearchResult.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Sylwia Brant
 */
class ChooseTwoCitiesControllerTest {
    private static TextField cityField1;
    private static TextField cityField2;
    private static Label errorLabel1;
    private static Label errorLabel2;

    private final ViewFactory viewFactory = mock(ViewFactory.class);
    private final WeatherDataCollection weatherData = mock(WeatherDataCollection.class);
    private final WeatherFetchingCoordinator coordinator = mock(WeatherFetchingCoordinator.class);

    ChooseTwoCitiesController controller = new ChooseTwoCitiesController(
            coordinator,
            weatherData,
            viewFactory,
            "MainWindowFXML.fxml",
            cityField1,
            cityField2,
            errorLabel1,
            errorLabel2) {
        @Override
        public void closeChoiceWindow() {
        }
    };

    @BeforeAll
    public static void setUpToolkit() {
        Platform.startup(() -> System.out.println("Toolkit initialized ..."));
        cityField1 = new TextField();
        cityField2 = new TextField();
        errorLabel1 = new Label();
        errorLabel2 = new Label();
    }

    @AfterEach
    void unsetLabelsText() {
        errorLabel1.setText("");
        errorLabel2.setText("");
    }

    @Test
    void mainViewShouldBeInitializedAfterSuccessfulDataFetch() {
        //given
        cityField1.setText("City1");
        cityField2.setText("City2");
        when(coordinator.fetchCityWeatherData(cityField1.getText(), 0)).thenReturn(SUCCESS);
        when(coordinator.fetchCityWeatherData(cityField2.getText(), 1)).thenReturn(SUCCESS);
        doNothing().when(viewFactory).showWeatherView();
        //when
        controller.fetchDataAction();
        //then
        verify(viewFactory).showWeatherView();
        assertEquals(errorLabel1.getText(), "");
        assertEquals(errorLabel2.getText(), "");
    }

    @Test
    void firstNoResultsLabelShouldBeSetUponUnsuccessfulFirstCityFetch() {
        //given
        cityField1.setText("City1");
        cityField2.setText("City2");

        when(coordinator.fetchCityWeatherData(cityField1.getText(), 0)).thenReturn(FAILED_BY_CITY_NAME);
        when(coordinator.fetchCityWeatherData(cityField2.getText(), 1)).thenReturn(SUCCESS);
        //when
        controller.fetchDataAction();
        //then
        assertEquals(errorLabel1.getText(), Messages.NO_RESULTS_FOR_CITY);
        assertEquals(errorLabel2.getText(), "");
    }

    @Test
    void secondNoResultsLabelShouldBeSetUponUnsuccessfulSecondCityFetch() {
        //given
        cityField1.setText("City1");
        cityField2.setText("City2");

        when(coordinator.fetchCityWeatherData(cityField1.getText(), 0)).thenReturn(SUCCESS);
        when(coordinator.fetchCityWeatherData(cityField2.getText(), 1)).thenReturn(FAILED_BY_CITY_NAME);
        //when
        controller.fetchDataAction();
        //then
        assertEquals(errorLabel1.getText(), "");
        assertEquals(errorLabel2.getText(), Messages.NO_RESULTS_FOR_CITY);
    }

    @Test
    void bothNoResultsLabelsShouldBeSetUponUnsuccessfulBothCitiesFetch() {
        //given
        cityField1.setText("City1");
        cityField2.setText("City2");

        when(coordinator.fetchCityWeatherData(cityField1.getText(), 0)).thenReturn(FAILED_BY_CITY_NAME);
        when(coordinator.fetchCityWeatherData(cityField2.getText(), 1)).thenReturn(FAILED_BY_CITY_NAME);
        //when
        controller.fetchDataAction();
        //then
        assertEquals(errorLabel1.getText(), Messages.NO_RESULTS_FOR_CITY);
        assertEquals(errorLabel2.getText(), Messages.NO_RESULTS_FOR_CITY);
    }

    @Test
    void errorLabelShouldBeSetUponErrorDuringFetch() {
        //given
        cityField1.setText("City1");
        cityField2.setText("City2");

        when(coordinator.fetchCityWeatherData(cityField1.getText(), 0)).thenReturn(FAILED_BY_UNEXPECTED_ERROR);
        when(coordinator.fetchCityWeatherData(cityField2.getText(), 1)).thenReturn(FAILED_BY_UNEXPECTED_ERROR);
        //when
        controller.fetchDataAction();
        //then
        assertEquals(errorLabel1.getText(), "");
        assertEquals(errorLabel2.getText(), ERROR);
    }
}