package it.sylwiabrant.weather_app.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.FileReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Sylwia Brant
 */
class FromJsonConverterTest {

    @Test
    void floatingPointStringDeserializesToDoubleValue() {
        JSONObject jsonObject = new JSONObject("{\"main\":{\"temp\":18.38}}");
        assertThat(((Number) jsonObject.getJSONObject("main").get("temp")).doubleValue(), instanceOf(Double.class));
        assertThat(((Number) jsonObject.getJSONObject("main").get("temp")).doubleValue(), is(equalTo(18.38)));
    }
}