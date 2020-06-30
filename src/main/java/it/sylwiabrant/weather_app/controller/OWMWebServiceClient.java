package it.sylwiabrant.weather_app.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by Sylwia Brant
 */
public class OWMWebServiceClient {
    private static HttpClient client;
    private final String apiKey = "c8be7af25cecaa14bc28e7439a4a4130";

    /** Openweathermap returns result code as string or as integer depending on code
     * Function checks if code is an instance of an int or a string and returns always
     * an int.
     * @return int - status code
     */
    public List<String> queryAPI(String location) {
        client = HttpClient.newHttpClient();
        List<URI> dataURIs =
                List.of(
                        URI.create(getCurrentWeatherLink(location)),
                        URI.create(getForecastWeatherLink(location)));
        try {
            List<CompletableFuture<String>> futures = dataURIs.stream()
                    .map(dataURI -> client
                            .sendAsync(
                                    HttpRequest.newBuilder(dataURI).GET().build(),
                                    HttpResponse.BodyHandlers.ofString())
                            .thenApply(HttpResponse::body)
                            .exceptionally(e -> {
                                throw (RuntimeException) e;
                            }))
                    .collect(Collectors.toList());

            return CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]))
                    .thenApply(v -> futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList())
                    ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /** @return API call to fetch current weather for desired location */
    private String getCurrentWeatherLink(String location){
        return "http://api.openweathermap.org/data/2.5/weather?q="+location+"&appid" +
                "="+apiKey+"&units=metric&lang=pl";
    }

    /** @return API call to fetch forecast weather for desired location */
    private String getForecastWeatherLink(String location){
        return "http://api.openweathermap.org/data/2.5/forecast?q="+location+"&appid" +
                "="+apiKey+"&units=metric&lang=pl";
    }
}
