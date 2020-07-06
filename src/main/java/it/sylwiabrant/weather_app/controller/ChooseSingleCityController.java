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
public class ChooseSingleCityController extends BaseController {

    private WeatherFetchingCoordinator fetchingCoordinator;
    private final int index;

    @FXML
    private TextField cityField;

    @FXML
    private Label errorLabel;

    @FXML
    void searchCityAction() {
        String newCityName = cityField.getText();
        CitySearchResult citySearchResult = fetchingCoordinator.refetchCityWeatherData(newCityName, index);
        if (citySearchResult == CitySearchResult.SUCCESS) {
            viewFactory.closeStage((Stage) errorLabel.getScene().getWindow());
            return;
        } else if (citySearchResult == CitySearchResult.FAILED_BY_CITY_NAME) {
            errorLabel.setText(NO_RESULTS_FOR_CITY);
        } else
            errorLabel.setText(ERROR);
        weatherData.removeData();
    }

    public ChooseSingleCityController(WeatherFetchingCoordinator fetchingCoordinator,
                                      WeatherDataCollection weatherData,
                                      ViewFactory viewFactory, String fxmlName,
                                      int index) {
        super(weatherData, viewFactory, fxmlName);
        this.fetchingCoordinator = fetchingCoordinator;
        this.index = index;
    }
}
