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
        AnchorPane root = new AnchorPane();
   //     ViewFactory viewFactory = new ViewFactory();
     //   MainWindowController controller = new MainWindowController("MainWindowFXML");
   //     viewFactory.showMainWindowView();

 /*       stage.setTitle("Aplikacja pogodowa");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/sylwiabrant/weather_app" +
                "/MainWindowFXML.fxml"));
        WeatherDataCollection weatherData = new WeatherDataCollection();
        WeatherFetcherService fetcher = new WeatherFetcherService(weatherData);
        WeatherViewController weatherViewController = new WeatherViewController(weatherData, "/it/sylwiabrant/weather_app" +
                "/MainWindowFXML.fxml");


;
        fxmlLoader.setController(weatherViewController);

        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/it/sylwiabrant/weather_app/style.css").toExternalForm());
     //   Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();*/
        ViewFactory viewFactory = new ViewFactory(new WeatherDataCollection());
        viewFactory.showWeatherView();
    }


    public static void main(String[] args) {
        launch(args);
    }

}