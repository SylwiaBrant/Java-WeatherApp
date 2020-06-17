package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CitySearchResult;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Sylwia Brant
 */
public class ChooseSingleCityController extends BaseController {

    private final int cityIndex;

    @FXML
    private TextField cityField;

    @FXML
    private Label errorLabel;

    @FXML
    void searchCityAction() {
        String city = cityField.getText();
        CitySearchResult citySearchResult = weatherData.changeCityWeatherData(city, cityIndex);
        if (citySearchResult == CitySearchResult.SUCCESS) {
            System.out.println("Pomyślnie pobrano dane pogodowe.");
                viewFactory.closeStage((Stage) errorLabel.getScene().getWindow());
            return;
        } else if (citySearchResult == CitySearchResult.FAILED_BY_CITYNAME){
            errorLabel.setText("Brak wyników dla tej miejscowości.");
        } else
            errorLabel.setText("Wystąpił błąd. Przepraszamy.");
        weatherData.removeData();
    }

    public ChooseSingleCityController(WeatherDataCollection weatherData, ViewFactory viewFactory, String fxmlName,
                                      int index) {
        super(weatherData, viewFactory, fxmlName);
        this.cityIndex = index;
    }
}
