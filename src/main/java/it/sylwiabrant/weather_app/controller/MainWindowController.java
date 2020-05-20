package it.sylwiabrant.weather_app.controller;

import com.google.gson.Gson;
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
       // InputStream WEATHER_FILE = new FileInputStream("today.json");
     //   JsonReader jsonReader = Json.createReader(WEATHER_FILE);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new FileReader("today.json"), JsonObject.class);

     //   BufferedReader br = new BufferedReader(new FileReader(WEATHER_FILE));
     //   JsonObject jobj = new Gson().fromJson(myJSONString, JsonObject.class);
      //  double result = jobj.get("test").getAsDouble();


    //    JsonObject jsonObject = jsonReader.readObject();
        System.out.println(jsonObject);

        WeatherConditions cond = new WeatherConditions();
        cond.setCity(jsonObject.get("name").getAsString());
        cond.setCountry(jsonObject.getAsJsonObject("sys").get("country").getAsString());
        cond.setCurrentTemp(jsonObject.getAsJsonObject("main").get("temp").getAsDouble());
  /*      cond.setWindChill(Double.valueOf(jsonObject.getJsonObject("main").getJsonObject("feels_like").get(
                "day").toString()));
        cond.setPressure(Double.valueOf(jsonObject.getJsonObject("main").get("pressure").toString()));
        cond.setHumidity(Double.valueOf(jsonObject.getJsonObject("main").get("humidity").toString()));
        cond.setWindSpeed(Double.valueOf(jsonObject.getJsonObject("wind").get("speed").toString()));
        cond.setWindDirection(Double.valueOf(jsonObject.getJsonObject("wind").get("deg").toString()));
*/
        System.out.println(cond.getCity() + " " + cond.getCountry() + " " + cond.getCurrentTemp());

        return cond;
    }

    public void setCurrentWeatherView() throws IOException {


        WeatherConditions cond = this.fetchCurrentWeather();
        System.out.println(cond.getCity() + " " + cond.getCountry() + " " + cond.getCurrentTemp());
        locationLabel.setText("Lolo");
      //  tempLabel.setText(String.valueOf(cond.getCurrentTemp()) + " °C");
      //  windChillLabel.setText(String.valueOf(cond.getWindChill()) + " °C");
    /*    pressureLabel1.setText(String.valueOf(cond.getPressure()) + " hPa");
        windSpeedLabel1.setText(String.valueOf(cond.getWindSpeed()) + " m/s");
        windDirectionLabel1.setText(String.valueOf(cond.getWindDirection()));
        humidityLabel1.setText(String.valueOf(cond.getHumidity()) + " %");*/

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
