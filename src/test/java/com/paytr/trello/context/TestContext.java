package com.paytr.trello.context;

import java.util.ArrayList;
import java.util.List;

public class TestContext {
    private String boardId;
    private String listId;
    private List<String> cardIds = new ArrayList<>();
    private String selectedCardId;
    private String boardName; // Yeni alan
    private String boardDescription; // Yeni alan

    // Getter ve Setter'lar
    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public List<String> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<String> cardIds) {
        this.cardIds = cardIds;
    }

    public void addCardId(String cardId) {
        this.cardIds.add(cardId);
    }

    public String getSelectedCardId() {
        return selectedCardId;
    }

    public void setSelectedCardId(String selectedCardId) {
        this.selectedCardId = selectedCardId;
    }

    public String getBoardName() { // Yeni getter
        return boardName;
    }

    public void setBoardName(String boardName) { // Yeni setter
        this.boardName = boardName;
    }

    public String getBoardDescription() { // Yeni getter
        return boardDescription;
    }

    public void setBoardDescription(String boardDescription) { // Yeni setter
        this.boardDescription = boardDescription;
    }
}
