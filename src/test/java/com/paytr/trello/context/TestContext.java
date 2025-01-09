package com.paytr.trello.context;

import java.util.ArrayList;
import java.util.List;

/**
 * TestContext sınıfı, test senaryolarında kullanılan veri akışını ve durum bilgisini yönetmek için tasarlanmıştır.
 * Sınıfın temel işlevleri:
 * - Test sırasında oluşturulan Board (Pano), List (Liste) ve Card (Kart) gibi öğelerin ID bilgilerini saklama.
 * - Seçilen kart (selectedCardId) veya oluşturulan öğelerle ilgili bağlamsal bilgileri tutma.
 * - Testlerde kullanılan board adı ve açıklaması gibi ek detayları saklama.
 * - Her test adımının bağımsız olmasını sağlamak için bağlamsal bilgilerin yönetimini kolaylaştırma.
 * Sağlanan özellikler:
 * - Oluşturulan board, liste ve kartların ID'lerini set/get metotlarıyla yönetme.
 * - Listelenen kart ID'lerini bir liste olarak saklama ve yeni kartları listeye ekleme.
 * - Rastgele seçilen kart gibi test senaryolarında ihtiyaç duyulan verileri yönetme.
 * Bu sınıf, testler arası veri taşımayı kolaylaştırır ve senaryoların birbirine bağımlı olmamasını sağlar.
 */

public class TestContext {
    private String boardId;
    private String listId;
    private List<String> cardIds = new ArrayList<>();
    private String selectedCardId;
    private String boardName;
    private String boardDescription;

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

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardDescription() {
        return boardDescription;
    }

    public void setBoardDescription(String boardDescription) {
        this.boardDescription = boardDescription;
    }
}
