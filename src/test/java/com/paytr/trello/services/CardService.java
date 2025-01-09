package com.paytr.trello.services;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CardService extends BaseService {
    public Response createCard(String listId, String cardName) {
        return prepareRequest()
                .body("{\"name\": \"" + cardName + "\", \"idList\": \"" + listId + "\"}")
                .when()
                .post("/cards");
    }

    public Response getCard(String cardId) {
        return given(spec)
                .when()
                .get("/cards/" + cardId);
    }

    public List<String> getAllCards(String listId) {
        // Null kontrolü
        Assertions.assertNotNull(listId, "List ID null! Kartlar alınamaz.");

        // API çağrısı
        Response response = given(spec)
                .when()
                .get("/lists/" + listId + "/cards");

        // Yanıt durum kodunu kontrol et
        Assertions.assertEquals(200, response.getStatusCode(), "Kartlar alınamadı!");

        // Kart ID'lerini döndür
        return response.jsonPath().getList("id", String.class);
    }

    // Kart güncelleme metodu
    public Response updateCard(String cardId, String newName, String newDesc) {
        return given(spec)
                .queryParam("name", newName) // Yeni isim
                .queryParam("desc", newDesc) // Yeni açıklama
                .when()
                .put("/cards/" + cardId); // Güncelleme için PUT endpoint
    }

    public Response deleteCard(String cardId) {
        return given(spec)
                .when()
                .delete("/cards/" + cardId);
    }




}
