package com.paytr.trello.services;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * CardService sınıfı, Trello API ile kart işlemlerini gerçekleştirmek için kullanılan bir servis sınıfıdır.
 * Bu sınıf, BaseService sınıfından türetilmiştir ve API isteklerini daha kolay yönetmek
 * için sağlanan ortak işlevleri kullanır.
 * Sağlanan temel işlevler:
 * - Yeni bir kart oluşturma.
 * - Belirli bir kartın bilgilerini alma.
 * - Bir liste içindeki tüm kartları getirme.
 * - Mevcut bir kartı güncelleme.
 * - Bir kartı silme.
 */
public class CardService extends BaseService {

    /**
     * Yeni bir kart oluşturur.
     *
     * @param listId Kartın ekleneceği listenin ID'si.
     * @param cardName Oluşturulacak kartın adı.
     * @return API'den dönen Response nesnesi.
     */
    public Response createCard(String listId, String cardName) {
        return prepareRequest()
                .body("{\"name\": \"" + cardName + "\", \"idList\": \"" + listId + "\"}") // JSON gövdesi ile kart adı ve liste ID'si gönderilir
                .when()
                .post("/cards"); // Trello API'nin "/cards" endpoint'ine POST isteği gönderilir
    }

    /**
     * Belirtilen kartın bilgilerini getirir.
     *
     * @param cardId Bilgileri alınacak kartın ID'si.
     * @return API'den dönen Response nesnesi.
     */
    public Response getCard(String cardId) {
        return given(spec)
                .when()
                .get("/cards/" + cardId); // Trello API'nin "/cards/{id}" endpoint'ine GET isteği gönderilir
    }

    /**
     * Belirli bir listenin içindeki tüm kartları getirir.
     *
     * @param listId Kartların alınacağı listenin ID'si.
     * @return Listenin içindeki tüm kartların ID'lerini içeren bir liste.
     */
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
        return response.jsonPath().getList("id", String.class); // JSON yanıtından kart ID'lerini alır
    }

    /**
     * Belirli bir kartı günceller.
     *
     * @param cardId Güncellenecek kartın ID'si.
     * @param newName Kartın yeni adı.
     * @param newDesc Kartın yeni açıklaması.
     * @return API'den dönen Response nesnesi.
     */
    public Response updateCard(String cardId, String newName, String newDesc) {
        return given(spec)
                .queryParam("name", newName)
                .queryParam("desc", newDesc)
                .when()
                .put("/cards/" + cardId); // Trello API'nin "/cards/{id}" endpoint'ine PUT isteği gönderilir
    }

    /**
     * Belirtilen kartı siler.
     *
     * @param cardId Silinecek kartın ID'si.
     * @return API'den dönen Response nesnesi.
     */
    public Response deleteCard(String cardId) {
        return given(spec)
                .when()
                .delete("/cards/" + cardId); // Trello API'nin "/cards/{id}" endpoint'ine DELETE isteği gönderilir
    }
}
