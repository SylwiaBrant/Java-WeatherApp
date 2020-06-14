package it.sylwiabrant.weather_app.controller;

import it.sylwiabrant.weather_app.model.WeatherDataCollection;
import it.sylwiabrant.weather_app.view.ViewFactory;

/**
 * Created by Sylwia Brant
 */
public abstract class BaseController {
    protected WeatherDataCollection weatherData;
    protected ViewFactory viewFactory;
    private String fxmlName;

    public BaseController(WeatherDataCollection weatherData, ViewFactory viewFactory, String fxmlName) {
        this.weatherData = weatherData;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return this.fxmlName;
    }
}
