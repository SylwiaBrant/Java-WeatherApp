package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CitySearchResult;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static it.sylwiabrant.weather_app.controller.Messages.ERROR;
import static it.sylwiabrant.weather_app.controller.Messages.NO_RESULTS_FOR_CITY;

/**
 * Created by Sylwia Brant
 */
public class ChooseTwoCitiesController extends BaseController {

    WeatherFetchingCoordinator fetchingCoordinator;
    @FXML
    private TextField cityField1;

    @FXML
    private Label cityLabel;

    @FXML
    private Label errorLabel1;

    @FXML
    private TextField cityField2;

    @FXML
    private Label errorLabel2;

    @FXML
    void fetchDataAction() {
        String city1 = cityField1.getText();
        String city2 = cityField2.getText();

        CitySearchResult citySearchResult1 = fetchingCoordinator.fetchCityWeatherData(city1, 0);
        CitySearchResult citySearchResult2 = fetchingCoordinator.fetchCityWeatherData(city2, 1);

        if (citySearchResult1 == CitySearchResult.SUCCESS && citySearchResult2 == CitySearchResult.SUCCESS) {
            if (!viewFactory.isWeatherViewInitialized()) {
                viewFactory.showWeatherView();
                viewFactory.closeStage((Stage) errorLabel1.getScene().getWindow());
            }
            return;
        } else {
            if (citySearchResult1 == CitySearchResult.FAILED_BY_CITY_NAME) {
                errorLabel1.setText(NO_RESULTS_FOR_CITY);
                weatherData.removeData();
            } else if (citySearchResult2 == CitySearchResult.FAILED_BY_CITY_NAME) {
                errorLabel2.setText(NO_RESULTS_FOR_CITY);
                weatherData.removeData();
            } else
                errorLabel2.setText(ERROR);
            weatherData.removeData();
        }
    }

    public ChooseTwoCitiesController(WeatherFetchingCoordinator fetchingCoordinator,
                                     WeatherDataCollection weatherData,
                                     ViewFactory viewFactory,
                                     String fxmlName) {
        super(weatherData, viewFactory, fxmlName);
        this.fetchingCoordinator = fetchingCoordinator;
    }
}
