package it.sylwiabrant.weather_app;

import it.sylwiabrant.weather_app.controller.WeatherFetcherService;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        ViewFactory viewFactory = new ViewFactory(new WeatherDataCollection(new WeatherFetcherService()));
        viewFactory.showBothCitiesChoiceWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}