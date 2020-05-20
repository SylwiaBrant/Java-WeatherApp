package it.sylwiabrant.weather_app;

import it.sylwiabrant.weather_app.controller.MainWindowController;
import it.sylwiabrant.weather_app.model.WeatherConditions;
import it.sylwiabrant.weather_app.view.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        ViewFactory viewFactory = new ViewFactory();
     //   MainWindowController controller = new MainWindowController("MainWindowFXML");
        viewFactory.showMainWindowView();

    /*    FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("MainWindowFXML.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }

}