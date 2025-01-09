package com.paytr.trello.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BoardService extends BaseService {
    public Response createBoard(String name, String desc) {
        return prepareRequest()
                .body("{\"name\": \"" + name + "\", \"desc\": \"" + desc + "\"}")
                .when()
                .post("/boards");
    }

    public Response getBoard(String boardId) {
        return given(spec)
                .when()
                .get("/boards/" + boardId);
    }

    public Response deleteBoard(String boardId) {
        return given(spec)
                .when()
                .delete("/boards/" + boardId); // Trello API DELETE endpoint'i
    }


}
