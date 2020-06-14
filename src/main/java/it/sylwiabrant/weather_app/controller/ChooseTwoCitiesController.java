package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.CitySearchResult;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sylwia Brant
 */
public class ChooseTwoCitiesController extends BaseController{

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
        try {
            WeatherFetcherService weatherFetcherService = new WeatherFetcherService(weatherData);
            CitySearchResult citySearchResult1 = weatherFetcherService.fetchWeatherData(city1);
            CitySearchResult citySearchResult2 = weatherFetcherService.fetchWeatherData(city2);
            if (citySearchResult1 == CitySearchResult.SUCCESS && citySearchResult2 == CitySearchResult.SUCCESS) {
                System.out.println("Pomyślnie pobrano dane pogodowe.");
                if (!viewFactory.isWeatherViewInitialized()) {
                    try {
                        viewFactory.showWeatherView();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return;
            } else if (citySearchResult1 == CitySearchResult.FAILED_BY_CITYNAME){
                errorLabel1.setText("Brak wyników dla tej miejscowości.");
                weatherData.removeData();
            } else if (citySearchResult2 == CitySearchResult.FAILED_BY_CITYNAME){
                errorLabel2.setText("Brak wyników dla tej miejscowości.");
                weatherData.removeData();
            } else
                errorLabel2.setText("Wystąpił błąd. Przepraszamy.");
                weatherData.removeData();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ChooseTwoCitiesController(WeatherDataCollection weatherData, ViewFactory viewFactory, String fxmlName) {
        super(weatherData, viewFactory, fxmlName);
    }

}
