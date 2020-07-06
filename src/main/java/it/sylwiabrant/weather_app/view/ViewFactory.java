package it.sylwiabrant.weather_app.view;

import it.sylwiabrant.weather_app.controller.*;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Sylwia Brant
 */
public class ViewFactory {
    private WeatherDataCollection weatherData;
    private WeatherFetchingCoordinator fetchingCoordinator;
    private boolean weatherViewInitialized = false;

    public ViewFactory(WeatherFetchingCoordinator fetchingCoordinator, WeatherDataCollection weatherData) {
        this.fetchingCoordinator = fetchingCoordinator;
        this.weatherData = weatherData;
    }

    public void showWeatherView() {
        BaseController controller = new WeatherViewController(weatherData, this, "/it/sylwiabrant" +
                "/weather_app/FXML/MainWindowFXML.fxml");
        initializeStage(controller);
        weatherViewInitialized = true;
    }

    public void showBothCitiesChoiceWindow() {
        BaseController controller = new ChooseTwoCitiesController(fetchingCoordinator,
                weatherData, this, "/it/sylwiabrant" +
                "/weather_app/FXML" +
                "/ChooseCitiesFXML.fxml");
        initializeStage(controller);
    }

    public void showSingleCityChoiceWindow(int index) {
        BaseController controller = new ChooseSingleCityController(fetchingCoordinator,
                weatherData, this, "/it" +
                "/sylwiabrant" +
                "/weather_app" +
                "/FXML" +
                "/ChooseCityFXML.fxml", index);
        initializeStage(controller);
    }

    private void initializeStage(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/it/sylwiabrant/weather_app/Styles/style.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public boolean isWeatherViewInitialized() {
        return weatherViewInitialized;
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
