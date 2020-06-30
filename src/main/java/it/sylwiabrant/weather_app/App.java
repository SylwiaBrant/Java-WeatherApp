package it.sylwiabrant.weather_app;

import it.sylwiabrant.weather_app.controller.FromJsonConverter;
import it.sylwiabrant.weather_app.controller.OWMWebServiceClient;
import it.sylwiabrant.weather_app.controller.WeatherFetchingCoordinator;
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
        FromJsonConverter converter = new FromJsonConverter();
        OWMWebServiceClient client = new OWMWebServiceClient();
        WeatherFetchingCoordinator coordinator = new WeatherFetchingCoordinator(client, converter);
        WeatherDataCollection weatherDataCollection = new WeatherDataCollection(coordinator);
        ViewFactory viewFactory = new ViewFactory(weatherDataCollection);
        viewFactory.showBothCitiesChoiceWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}