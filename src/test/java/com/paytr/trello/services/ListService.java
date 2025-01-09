package com.paytr.trello.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * ListService sınıfı, Trello API kullanarak listelerle ilgili işlemleri gerçekleştirmek için tasarlanmıştır.
 * Bu sınıf, bir board üzerine liste eklemek ve bir board üzerindeki mevcut listeleri almak gibi işlevleri sağlar.
 * BaseService sınıfından türediği için ortak API yapılandırmalarını kullanır.
 */
public class ListService extends BaseService {

    /**
     * Belirtilen board üzerine yeni bir liste oluşturur.
     *
     * @param boardId Liste eklenecek board'un ID'si.
     * @param listName Oluşturulacak listenin adı.
     * @return API'den dönen Response nesnesi.
     */
    public Response createList(String boardId, String listName) {
        return given()
                .spec(spec) // Ortak yapılandırma
                .header("Content-Type", "application/json") // JSON gövdesi için içerik türü belirtir
                .body("{\"name\": \"" + listName + "\", \"idBoard\": \"" + boardId + "\"}") // JSON gövdesi ile liste adı ve board ID'si gönderilir
                .when()
                .post("/lists"); // Trello API'nin "/lists" endpoint'ine POST isteği gönderilir
    }

    /**
     * Belirli bir board üzerindeki mevcut tüm listeleri getirir.
     *
     * @param boardId Listeleri alınacak board'un ID'si.
     * @return API'den dönen Response nesnesi.
     */
    public Response getListsOnBoard(String boardId) {
        return given(spec) // Ortak yapılandırma
                .when()
                .get("/boards/" + boardId + "/lists"); // Trello API'nin "/boards/{id}/lists" endpoint'ine GET isteği gönderilir
    }
}
