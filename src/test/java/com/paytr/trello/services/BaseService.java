package com.paytr.trello.services;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseService {

    private static final Dotenv dotenv; // .env dosyasını yüklemek için değişken
    protected static RequestSpecification spec;

    static {
        try {
            // .env dosyasını yükle
            dotenv = Dotenv.configure()
                    .directory(System.getProperty("user.dir")) // Projenin kök dizininden yükle
                    .load();

            // Yüklenen değerleri kontrol et
            System.out.println("BASE_URI: " + dotenv.get("BASE_URI"));
            System.out.println("TRELLO_API_KEY: " + dotenv.get("TRELLO_API_KEY"));
            System.out.println("TRELLO_API_TOKEN: " + dotenv.get("TRELLO_API_TOKEN"));

            // RequestSpecification yapılandırması
            spec = new RequestSpecBuilder()
                    .setBaseUri(getEnvVar("BASE_URI"))
                    .addQueryParam("key", getEnvVar("TRELLO_API_KEY"))
                    .addQueryParam("token", getEnvVar("TRELLO_API_TOKEN"))
                    .addFilter(new RequestLoggingFilter())
                    .addFilter(new ResponseLoggingFilter())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error loading .env file or initializing BaseService: " + e.getMessage(), e);
        }
    }

    private static String getEnvVar(String key) {
        String value = dotenv.get(key);
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Environment variable '" + key + "' not found in .env file.");

        }
        return value;
    }

    protected static RequestSpecification prepareRequest() {
        return given().spec(spec).header("Content-Type", "application/json");

    }

}
