package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Created by Sylwia Brant
 */
class WeatherViewControllerTest {

    private static ViewFactory viewFactoryMock = mock(ViewFactory.class);
    private static WeatherDataCollection weatherDataMock = mock(WeatherDataCollection.class);

    private static WeatherViewController controller = new WeatherViewController(
            weatherDataMock,
            viewFactoryMock,
                "MainWindowFXML.fxml");

    @Test
    void singleChoiceWindowShouldBeShownUpon1stCitySearchBtnClick() {
        //given
        //controller and viewFactory mock initialized in attributes
        doNothing().when(viewFactoryMock).showSingleCityChoiceWindow(0);
        //when
        controller.change1stCityAction();
        //then
        verify(viewFactoryMock, times(1)).showSingleCityChoiceWindow(0);
    }

    @Test
    void singleChoiceWindowShouldBeShownUpon2ndCitySearchBtnClick() {
        //given
        //controller and viewFactory mock initialized in attributes
        doNothing().when(viewFactoryMock).showSingleCityChoiceWindow(1);
        //when
        controller.change2ndCityAction();
        //then
        verify(viewFactoryMock, times(1)).showSingleCityChoiceWindow(1);
    }
}