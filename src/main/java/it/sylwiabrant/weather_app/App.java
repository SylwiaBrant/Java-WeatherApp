package it.sylwiabrant.weather_app;

import it.sylwiabrant.weather_app.controller.WeatherFetcherService;
import it.sylwiabrant.weather_app.controller.WeatherViewController;
import it.sylwiabrant.weather_app.model.CurrentWeather;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ViewFactory viewFactory = new ViewFactory(new WeatherDataCollection());
        viewFactory.showChoiceWindow();
    }


    public static void main(String[] args) {
        launch(args);
    }

}