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
import static it.sylwiabrant.weather_app.model.CitySearchResult.FAILED_BY_CITY_NAME;
import static it.sylwiabrant.weather_app.model.CitySearchResult.SUCCESS;

/**
 * Created by Sylwia Brant
 */
public class ChooseTwoCitiesController extends BaseController {

    private final WeatherFetchingCoordinator fetchingCoordinator;
    @FXML
    private TextField cityField1;

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

        if (citySearchResult1 == SUCCESS && citySearchResult2 == SUCCESS) {
            if (!viewFactory.isWeatherViewInitialized()) {
                viewFactory.showWeatherView();
                closeChoiceWindow();
            }
        } else {
            if (citySearchResult1 == FAILED_BY_CITY_NAME && citySearchResult2 == FAILED_BY_CITY_NAME) {
                errorLabel1.setText(NO_RESULTS_FOR_CITY);
                errorLabel2.setText(NO_RESULTS_FOR_CITY);
                weatherData.removeData();
            } else if (citySearchResult1 == FAILED_BY_CITY_NAME) {
                errorLabel1.setText(NO_RESULTS_FOR_CITY);
                weatherData.removeData();
            } else if (citySearchResult2 == FAILED_BY_CITY_NAME) {
                errorLabel2.setText(NO_RESULTS_FOR_CITY);
                weatherData.removeData();
            } else
                errorLabel2.setText(ERROR);
            weatherData.removeData();
        }
    }

    protected void closeChoiceWindow() {
        viewFactory.closeStage((Stage) errorLabel1.getScene().getWindow());
    }

    public ChooseTwoCitiesController(WeatherFetchingCoordinator fetchingCoordinator,
                                     WeatherDataCollection weatherData,
                                     ViewFactory viewFactory,
                                     String fxmlName) {
        super(weatherData, viewFactory, fxmlName);
        this.fetchingCoordinator = fetchingCoordinator;
    }

    public ChooseTwoCitiesController(WeatherFetchingCoordinator fetchingCoordinator,
                                     WeatherDataCollection weatherData,
                                     ViewFactory viewFactory,
                                     String fxmlName,
                                     TextField cityField1,
                                     TextField cityField2,
                                     Label errorLabel1,
                                     Label errorLabel2) {
        super(weatherData, viewFactory, fxmlName);
        this.fetchingCoordinator = fetchingCoordinator;
        this.cityField1 = cityField1;
        this.cityField2 = cityField2;
        this.errorLabel1 = errorLabel1;
        this.errorLabel2 = errorLabel2;
    }
}
