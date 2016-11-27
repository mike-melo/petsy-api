package com.petsy;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest extends AbstractTestNGSpringContextTests {
    @Value("${local.server.port}")
    private int port;

    private String baseUrl;

    private RestTemplate restTemplate;

    @PostConstruct
    public final void init() {
        baseUrl = "http://localhost:" + port + "/pets";
        restTemplate = initializeRestTemplate();
    }

    private RestTemplate initializeRestTemplate() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);
        return new RestTemplate(Collections.singletonList(converter));
    }


    RestTemplate getRestTemplate() {
        return restTemplate;
    }

    String getBaseUrl() {
        return this.baseUrl;
    }
}
