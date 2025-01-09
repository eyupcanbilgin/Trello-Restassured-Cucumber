package com.paytr.trello.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * BoardService sınıfı, Trello API ile board (tahta) işlemlerini gerçekleştirmek için
 * kullanılan bir servis sınıfıdır.
 * Bu sınıf, BaseService sınıfından türetilmiştir ve API isteklerini daha kolay yönetmek
 * için sağlanan ortak işlevleri kullanır.
 * Sağlanan temel işlevler:
 * - Yeni bir board oluşturma.
 * - Mevcut bir board'un bilgilerini getirme.
 * - Bir board'u silme.
 */
public class BoardService extends BaseService {

    /**
     * Yeni bir board oluşturur.
     *
     * @param name Oluşturulacak board'un adı.
     * @param desc Oluşturulacak board'un açıklaması.
     * @return API'den dönen Response nesnesi.
     */
    public Response createBoard(String name, String desc) {
        return prepareRequest()
                .body("{\"name\": \"" + name + "\", \"desc\": \"" + desc + "\"}")
                .when()
                .post("/boards");
    }

    /**
     * Belirtilen board'un bilgilerini getirir.
     *
     * @param boardId Bilgileri alınacak board'un ID'si.
     * @return API'den dönen Response nesnesi.
     */
    public Response getBoard(String boardId) {
        return given(spec)
                .when()
                .get("/boards/" + boardId);
    }

    /**
     * Belirtilen board'u siler.
     *
     * @param boardId Silinecek board'un ID'si.
     * @return API'den dönen Response nesnesi.
     */
    public Response deleteBoard(String boardId) {
        return given(spec)
                .when()
                .delete("/boards/" + boardId); // Trello API DELETE endpoint'i
    }


}
