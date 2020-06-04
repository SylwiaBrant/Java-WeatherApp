module it.sylwiabrant.weather_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens it.sylwiabrant.weather_app to javafx.fxml;
    opens it.sylwiabrant.weather_app.controller to javafx.fxml;
    exports it.sylwiabrant.weather_app;
}