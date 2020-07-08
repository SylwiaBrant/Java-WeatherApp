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
public class ChooseSingleCityController extends BaseController {

    private final WeatherFetchingCoordinator fetchingCoordinator;
    private final int index;

    @FXML
    private TextField cityField;

    @FXML
    private Label errorLabel;

    @FXML
    void searchCityAction() {
        String newCityName = cityField.getText();
        CitySearchResult citySearchResult = fetchingCoordinator.refetchCityWeatherData(newCityName, index);
        if (citySearchResult == SUCCESS) {
            closeChoiceWindow();
        } else if (citySearchResult == FAILED_BY_CITY_NAME) {
            errorLabel.setText(NO_RESULTS_FOR_CITY);
        } else {
            errorLabel.setText(ERROR);
            weatherData.removeData();
        }
    }

    protected void closeChoiceWindow() {
        viewFactory.closeStage((Stage) errorLabel.getScene().getWindow());
    }

    public ChooseSingleCityController(WeatherFetchingCoordinator fetchingCoordinator,
                                      WeatherDataCollection weatherData,
                                      ViewFactory viewFactory, String fxmlName,
                                      int index) {
        super(weatherData, viewFactory, fxmlName);
        this.fetchingCoordinator = fetchingCoordinator;
        this.index = index;
    }

    public ChooseSingleCityController(WeatherFetchingCoordinator fetchingCoordinator,
                                      WeatherDataCollection weatherData,
                                      ViewFactory viewFactory,
                                      String fxmlName,
                                      int index,
                                      TextField cityField,
                                      Label errorLabel) {
        super(weatherData, viewFactory, fxmlName);
        this.fetchingCoordinator = fetchingCoordinator;
        this.index = index;
        this.cityField = cityField;
        this.errorLabel = errorLabel;
    }
}
