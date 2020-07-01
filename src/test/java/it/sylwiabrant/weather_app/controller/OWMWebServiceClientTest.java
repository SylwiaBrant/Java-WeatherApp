package it.sylwiabrant.weather_app.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.net.http.HttpResponse;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

/**
 * Created by Sylwia Brant
 */
class OWMWebServiceClientTest {

    @Test
    void validQueryAPICallShouldReturnTwoObjects() {
        OWMWebServiceClient client = new OWMWebServiceClient();
        //when
        List<String> objects = client.queryAPI("Londyn");
        //then
        assertThat(objects.size(), equalTo(2));
    }

    @Test
    void queryAPIShouldThrowRuntimeException() {
        //given
        OWMWebServiceClient client = spy(new OWMWebServiceClient());
        given(client.queryAPI("Londyn")).willThrow(RuntimeException.class);
        //when
        // then
        assertThrows(RuntimeException.class, () -> client.queryAPI("Londyn"));
    }
 }