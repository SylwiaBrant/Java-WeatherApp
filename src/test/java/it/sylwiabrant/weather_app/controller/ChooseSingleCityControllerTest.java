package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.sylwiabrant.weather_app.controller.Messages.ERROR;
import static it.sylwiabrant.weather_app.controller.Messages.NO_RESULTS_FOR_CITY;
import static it.sylwiabrant.weather_app.model.CitySearchResult.*;
import static it.sylwiabrant.weather_app.model.CitySearchResult.FAILED_BY_UNEXPECTED_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * Created by Sylwia Brant
 */
class ChooseSingleCityControllerTest {
    private static int index;
    private static TextField cityField;
    private static Label errorLabel;

    private final ViewFactory viewFactory = mock(ViewFactory.class);
    private final WeatherDataCollection weatherData = mock(WeatherDataCollection.class);
    private final WeatherFetchingCoordinator coordinator = mock(WeatherFetchingCoordinator.class);

    ChooseSingleCityController controller = new ChooseSingleCityController(
            coordinator,
            weatherData,
            viewFactory,
            "ChooseCityFXML.fxml",
            index,
            cityField,
            errorLabel) {
        @Override
        public void closeChoiceWindow() {
        }
    };

    @BeforeAll
    public static void setUpToolkit() {
        Platform.startup(() -> System.out.println("Toolkit initialized ..."));
        cityField = new TextField();
        errorLabel = new Label();
    }

    @BeforeEach
    void setLabelsText() {
        errorLabel.setText("");
    }
    @AfterEach
    void unsetLabelsText() {
        errorLabel.setText("");
    }

    @Test
    void errorLabelShouldRemainEmptyAfterSuccessfulDataFetch() {
        //given
        cityField.setText("City1");
        when(coordinator.refetchCityWeatherData(cityField.getText(), 0)).thenReturn(SUCCESS);
        //when
        controller.searchCityAction();
        //then
        assertEquals("", errorLabel.getText());
    }

    @Test
    void noResultsLabelShouldBeSetUponUnsuccessfulDataFetch() {

        //given
        cityField.setText("City1");
        System.out.println(errorLabel.getText());
        when(coordinator.refetchCityWeatherData(cityField.getText(), 0)).thenReturn(FAILED_BY_CITY_NAME);
        //when
        controller.searchCityAction();
        //then
        assertEquals(NO_RESULTS_FOR_CITY, errorLabel.getText());
    }

    @Test
    void errorLabelShouldBeSetUponErrorDuringFetch() {
        //given
        cityField.setText("City1");
        when(coordinator.refetchCityWeatherData(cityField.getText(), 0)).thenReturn(FAILED_BY_UNEXPECTED_ERROR);
        //when
        controller.searchCityAction();
        //then
        assertEquals(errorLabel.getText(), ERROR);
    }
}