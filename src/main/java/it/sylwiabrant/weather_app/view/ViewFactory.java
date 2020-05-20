package it.sylwiabrant.weather_app.view;

import it.sylwiabrant.weather_app.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Sylwia Brant
 */
public class ViewFactory {

    public void showMainWindowView() throws IOException {
        MainWindowController controller = new MainWindowController("/it/sylwiabrant/weather_app" +
                "/MainWindowFXML.fxml");
        initializeStage(controller);
    }

    private void initializeStage(MainWindowController controller) throws IOException {
        System.out.println(getClass().getResource(controller.getFxmlName()));
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
   //     scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
