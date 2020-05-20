package it.sylwiabrant.weather_app.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.sylwiabrant.weather_app.model.WeatherConditions;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sylwia Brant
 */
public class MainWindowController implements Initializable {

    @FXML
    private Label downfallLabel1;

    @FXML
    private Label pressureLabel1;

    @FXML
    private Label humidityLabel1;

    @FXML
    private Label windLabel1;

    @FXML
    private Label cloudsLabel1;

    @FXML
    private ImageView day1Icon;

    @FXML
    private Label downfallLabel2;

    @FXML
    private Label pressureLabel2;

    @FXML
    private Label humidityLabel2;

    @FXML
    private Label windLabel2;

    @FXML
    private Label cloudsLabel2;

    @FXML
    private ImageView day2Icon;

    @FXML
    private Label downfallLabel3;

    @FXML
    private Label pressureLabel3;

    @FXML
    private Label humidityLabel3;

    @FXML
    private Label windLabel3;

    @FXML
    private Label cloudsLabel3;

    @FXML
    private ImageView day3Icon;

    @FXML
    private Label downfallLabel4;

    @FXML
    private Label pressureLabel4;

    @FXML
    private Label humidityLabel4;

    @FXML
    private Label windLabel4;

    @FXML
    private Label cloudsLabel4;

    @FXML
    private ImageView day4Icon;

    @FXML
    private HBox locationsMenu;

    @FXML
    private Label tempLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label windChillLabel;

    @FXML
    private Label downfallLabel;

    @FXML
    private Label pressureLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label windLabel;

    @FXML
    private Label cloudsLabel;

    @FXML
    private Label visibilityLabel;

    @FXML
    private ImageView currentIcon;

    private String fxmlName;

    public MainWindowController( String fxmlName) {
        this.fxmlName = fxmlName;
    }

    public WeatherConditions fetchCurrentWeather() throws IOException {

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new FileReader("today.json"), JsonObject.class);

        WeatherConditions cond = new WeatherConditions();
        cond.setCity(jsonObject.get("name").getAsString());
        JsonArray arr = jsonObject.getAsJsonArray("weather");

        for(int i = 0; i < arr.size(); i++){
            JsonObject obj1 = (JsonObject) arr.get(i);
            cond.setDescription(obj1.get("main").getAsString());
        }
      //  cond.setDescription(jsonObject.getAsJsonArray("weather").getAsJsonObject().get("main").getAsString());
        cond.setCountry(jsonObject.getAsJsonObject("sys").get("country").getAsString());
        cond.setCurrentTemp(jsonObject.getAsJsonObject("main").get("temp").getAsDouble());
        cond.setWindChill(jsonObject.getAsJsonObject("main").get("feels_like").getAsDouble());
        cond.setPressure(jsonObject.getAsJsonObject("main").get("pressure").getAsDouble());
        cond.setHumidity(jsonObject.getAsJsonObject("main").get("humidity").getAsDouble());
        cond.setWindSpeed(jsonObject.getAsJsonObject("wind").get("speed").getAsDouble());
        cond.setWindDirection(jsonObject.getAsJsonObject("wind").get("deg").getAsDouble());
        cond.setVisibility(jsonObject.get("visibility").getAsInt());
        cond.setClouds(jsonObject.getAsJsonObject("clouds").get("all").getAsInt());

        return cond;
    }

    public WeatherConditions fetchCurrentWeather() throws IOException {

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new FileReader("today.json"), JsonObject.class);

        WeatherConditions cond = new WeatherConditions();
        cond.setCity(jsonObject.get("name").getAsString());
        JsonArray arr = jsonObject.getAsJsonArray("weather");

        for(int i = 0; i < arr.size(); i++){
            JsonObject obj1 = (JsonObject) arr.get(i);
            cond.setDescription(obj1.get("main").getAsString());
        }
        //  cond.setDescription(jsonObject.getAsJsonArray("weather").getAsJsonObject().get("main").getAsString());
        cond.setCountry(jsonObject.getAsJsonObject("sys").get("country").getAsString());
        cond.setCurrentTemp(jsonObject.getAsJsonObject("main").get("temp").getAsDouble());
        cond.setWindChill(jsonObject.getAsJsonObject("main").get("feels_like").getAsDouble());
        cond.setPressure(jsonObject.getAsJsonObject("main").get("pressure").getAsDouble());
        cond.setHumidity(jsonObject.getAsJsonObject("main").get("humidity").getAsDouble());
        cond.setWindSpeed(jsonObject.getAsJsonObject("wind").get("speed").getAsDouble());
        cond.setWindDirection(jsonObject.getAsJsonObject("wind").get("deg").getAsDouble());
        cond.setVisibility(jsonObject.get("visibility").getAsInt());
        cond.setClouds(jsonObject.getAsJsonObject("clouds").get("all").getAsInt());

        return cond;
    }
    
    public void setCurrentWeatherView() throws IOException {

        WeatherConditions cond = this.fetchCurrentWeather();
        locationLabel.setText(cond.getCity() + ", " + cond.getCountry());
        tempLabel.setText(String.valueOf(cond.getCurrentTemp()) + " °C");
        windChillLabel.setText(String.valueOf(cond.getWindChill()) + " °C");
        pressureLabel.setText(String.valueOf(cond.getPressure()) + " hPa");
        windLabel.setText(String.valueOf(cond.getWindSpeed()) + " m/s");
        humidityLabel.setText(String.valueOf(cond.getHumidity()) + " %");
        visibilityLabel.setText(String.valueOf(cond.getVisibility()) + " m");
        cloudsLabel.setText(String.valueOf(cond.getClouds()) + " %");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setCurrentWeatherView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFxmlName() {
        return this.fxmlName;
    }
}
