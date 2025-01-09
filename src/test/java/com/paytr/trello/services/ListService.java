package com.paytr.trello.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ListService extends BaseService {
    public Response createList(String boardId, String listName) {
        return given()
                .spec(spec)
                .header("Content-Type", "application/json")
                .body("{\"name\": \"" + listName + "\", \"idBoard\": \"" + boardId + "\"}")
                .when()
                .post("/lists");
    }

    public Response getListsOnBoard(String boardId) {
        return given(spec)
                .when()
                .get("/boards/" + boardId + "/lists");
    }
}
