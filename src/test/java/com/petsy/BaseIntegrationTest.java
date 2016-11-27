package com.petsy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest extends AbstractTestNGSpringContextTests {
    @Value("${local.server.port}")
    private int port;

    private String baseUrl;

    private RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public final void init() {
        baseUrl = "http://localhost:" + port + "/pets";
    }

    RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    String getBaseUrl() {
        return this.baseUrl;
    }
}
