package it.sylwiabrant.weather_app.view;

import it.sylwiabrant.weather_app.controller.WeatherViewController;
import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Sylwia Brant
 */
public class ViewFactory {
    private WeatherDataCollection weatherData;

    public ViewFactory(WeatherDataCollection weatherData) {
        this.weatherData = weatherData;
    }

    public void showWeatherView() throws IOException {
        WeatherViewController controller = new WeatherViewController(weatherData,this,"/it/sylwiabrant" +
                "/weather_app/FXML/MainWindowFXML.fxml");
        initializeStage(controller);
    }

    private void initializeStage(WeatherViewController controller) {
        System.out.println("Inicjalizacja view w ViewFactory.");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/it/sylwiabrant/weather_app/Styles/style.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
/*
    public void updateView(){
        updateIcons();
    }

    private void updateIcons(){

    }*/
}
