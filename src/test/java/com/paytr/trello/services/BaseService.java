package com.paytr.trello.services;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseService {

    private static final Dotenv dotenv = Dotenv.load(); // .env dosyasını yükler
    protected static RequestSpecification spec;

    static {
        // RequestSpecification yapılandırması
        spec = new RequestSpecBuilder()
                .setBaseUri(dotenv.get("BASE_URI")) // BASE_URI'yi .env dosyasından al
                .addQueryParam("key", dotenv.get("TRELLO_API_KEY")) // API Key'i .env dosyasından al
                .addQueryParam("token", dotenv.get("TRELLO_API_TOKEN")) // API Token'i .env dosyasından al
                .addFilter(new RequestLoggingFilter()) // Request logları
                .addFilter(new ResponseLoggingFilter()) // Response logları
                .build();
    }

    protected static RequestSpecification prepareRequest() {
        return given().spec(spec).header("Content-Type", "application/json");
    }
}
