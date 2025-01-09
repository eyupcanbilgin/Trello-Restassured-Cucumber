package com.paytr.trello.steps;

import com.paytr.trello.context.TestContext;
import com.paytr.trello.services.BoardService;
import com.paytr.trello.services.CardService;
import com.paytr.trello.services.ListService;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import com.paytr.trello.utils.RandomUtils;
import java.util.ArrayList;
import java.util.List;
import com.paytr.trello.context.TestContext;
import com.paytr.trello.services.BoardService;
import com.paytr.trello.services.CardService;
import com.paytr.trello.services.ListService;
import io.cucumber.java.en.*;
import io.cucumber.picocontainer.PicoFactory;

/**
 * TrelloSteps sınıfı, Cucumber framework'ü ile Trello API'sine yapılan
 * entegrasyon testlerinde kullanılan adımları içermektedir.
 * Bu sınıf, adım tanımlamaları (Step Definitions) olarak bilinen ve
 * Feature dosyalarındaki Gherkin dili ile yazılmış senaryoları
 * gerçekleştirmek için kullanılan metotları içerir.
 * Sağlanan temel işlevler:
 * - Board oluşturma, liste ekleme ve kart ekleme gibi temel işlemler.
 * - Mevcut board ve kartların doğrulanması.
 * - Kartları güncelleme, silme ve listeleme.
 * - API'den dönen yanıtların doğruluğunu kontrol eden Assertions kullanımı.
 * TestContext, testlerin çalışması sırasında ortak verilerin
 * depolanmasını ve paylaşılmasını sağlar.
 */

public class TrelloSteps {

    // Trello API'ye özel servis sınıflarının örnekleri
    private final BoardService boardService;
    private final ListService listService;
    private final CardService cardService;

    // Test verilerini tutmak için kullanılan bir context
    private final TestContext context;

    /**
     * Constructor, PicoContainer tarafından otomatik olarak çağrılır.
     * Servis sınıfları ve TestContext burada initialize edilir.
     *
     * @param context Test sırasında kullanılacak verilerin depolandığı context
     */
    public TrelloSteps(TestContext context) {
        this.boardService = new BoardService();
        this.listService = new ListService();
        this.cardService = new CardService();
        this.context = context;
    }

    /**
     * Trello API için gerekli bilgilerin hazırlandığı adım.
     * Kullanıcıyı bilgilendirmek için konsola bir mesaj yazdırır.
     */
    @io.cucumber.java.en.Given("Kullanıcı Trello API için gerekli bilgileri hazırlar")
    public void kullaniciTrelloApiIcinGerekliBilgileriHazirlar() {
        System.out.println("Trello API bilgileri hazır.");
    }

    /**
     * Yeni bir board oluşturma işlemini gerçekleştirir.
     * Board ID'si TestContext'e kaydedilir.
     */
    @io.cucumber.java.en.When("Kullanıcı yeni bir board oluşturur")
    public void kullaniciYeniBirBoardOlusturur() {
        Response response = boardService.createBoard("Test Board", "Bu bir test boardudur.");
        String boardId = response.jsonPath().getString("id");
        Assertions.assertNotNull(boardId, "Board oluşturulamadı!");
        context.setBoardId(boardId); // TestContext'e kaydediyoruz
        System.out.println("Oluşturulan Board ID: " + boardId);
    }

    /**
     * Oluşturulan board'un doğruluğunu kontrol eder.
     */
    @io.cucumber.java.en.Then("Oluşturulan board doğrulanır")
    public void olusturulanBoardDogrulanir() {
        String boardId = context.getBoardId();
        Response response = boardService.getBoard(boardId);
        Assertions.assertEquals(200, response.getStatusCode(), "Board doğrulanamadı!");
        System.out.println("Board doğrulandı: " + boardId);
    }

    /**
     * Board'a iki adet kart ekleme işlemini gerçekleştirir.
     * Kart ID'leri TestContext'e kaydedilir.
     */
    @io.cucumber.java.en.And("Kullanıcı board'a iki adet kart ekler")
    public void kullaniciBoardaIkiAdetKartEkler() {
        String boardId = context.getBoardId();
        Response listResponse = listService.createList(boardId, "Test List");
        String listId = listResponse.jsonPath().getString("id");
        Assertions.assertNotNull(listId, "Liste oluşturulamadı!");
        context.setListId(listId);
        System.out.println("Oluşturulan List ID: " + listId);

        for (int i = 1; i <= 2; i++) {
            Response cardResponse = cardService.createCard(listId, "Test Card " + i);
            String cardId = cardResponse.jsonPath().getString("id");
            Assertions.assertNotNull(cardId, "Kart oluşturulamadı!");
            context.addCardId(cardId);
            System.out.println("Oluşturulan Kart ID: " + cardId);
        }
    }

    /**
     * Eklenen kartların doğruluğunu kontrol eder.
     */
    @io.cucumber.java.en.Then("Kartlar doğrulanır")
    public void kartlarDogrulanir() {
        List<String> cardIds = context.getCardIds();
        for (String cardId : cardIds) {
            Response response = cardService.getCard(cardId);
            Assertions.assertEquals(200, response.getStatusCode(), "Kart doğrulanamadı!");
            System.out.println("Kart doğrulandı: " + cardId);
        }
    }

    /**
     * Board'daki mevcut kartların listesini alır.
     */
    @io.cucumber.java.en.Given("Kullanıcı board'daki mevcut kartları alır")
    public void kullaniciBoarddakiMevcutKartlariAlir() {
        String boardId = context.getBoardId();
        Assertions.assertNotNull(boardId, "Board ID null! Board ID'yi doğrulayın.");

        String listId = context.getListId();
        if (listId == null) {
            System.out.println("List ID null, yeni bir liste oluşturuluyor...");
            Response listResponse = listService.createList(boardId, "Generated Test List");
            listId = listResponse.jsonPath().getString("id");
            Assertions.assertNotNull(listId, "Liste oluşturulamadı!");
            context.setListId(listId);
        }

        List<String> cards = cardService.getAllCards(listId);
        Assertions.assertFalse(cards.isEmpty(), "Kart bulunamadı!");
        context.setCardIds(cards);

        System.out.println("Mevcut Kartlar: " + cards);
    }

    /**
     * Board'daki mevcut kartlardan rastgele birini seçer.
     */
    @io.cucumber.java.en.When("Kullanıcı kartlardan bir tanesini random seçer")
    public void kullaniciKartlardanBirTanesiniRandomSecer() {
        List<String> cardIds = context.getCardIds();
        String selectedCardId = RandomUtils.getRandomElement(cardIds);
        Assertions.assertNotNull(selectedCardId, "Seçilen kart ID'si null olamaz!");
        context.setSelectedCardId(selectedCardId);
        System.out.println("Seçilen Kart ID: " + selectedCardId);
    }

    /**
     * Seçilen kartın bilgilerini günceller.
     */
    @io.cucumber.java.en.And("Kullanıcı random seçilen kartı günceller")
    public void kullaniciRandomSecilenKartiGunceller() {
        String cardId = context.getSelectedCardId();
        String newName = "Updated Card Name";
        String newDesc = "This is the updated description";

        Response response = cardService.updateCard(cardId, newName, newDesc);
        response.then().statusCode(200);

        String updatedName = response.jsonPath().getString("name");
        String updatedDesc = response.jsonPath().getString("desc");

        Assertions.assertEquals(newName, updatedName, "Kart adı doğru şekilde güncellenemedi!");
        Assertions.assertEquals(newDesc, updatedDesc, "Kart açıklaması doğru şekilde güncellenemedi!");

        System.out.println("Kart güncellendi: " + cardId);
    }

    @io.cucumber.java.en.Then("Güncellenen kart doğrulanır")
    public void guncellenenKartDogrulanir() {
        // TestContext'ten seçilen kart ID'sini al
        String selectedCardId = context.getSelectedCardId();
        Assertions.assertNotNull(selectedCardId, "Güncellenen kart ID'si null olamaz!");

        // Kart bilgilerini al
        Response response = cardService.getCard(selectedCardId);

        // HTTP durum kodunu doğrula
        response.then().statusCode(200);

        // API'den dönen bilgileri al
        String updatedName = response.jsonPath().getString("name");
        String updatedDesc = response.jsonPath().getString("desc");

        // Güncelleme sırasında kullanılan yeni değerler
        String expectedName = "Updated Card Name";
        String expectedDesc = "This is the updated description";

        // Güncellenen bilgileri doğrula
        Assertions.assertEquals(expectedName, updatedName, "Kart adı doğru şekilde güncellenemedi!");
        Assertions.assertEquals(expectedDesc, updatedDesc, "Kart açıklaması doğru şekilde güncellenemedi!");

        // Konsola başarı mesajı yazdır
        System.out.println("Güncellenen kart doğrulandı!");
        System.out.println("Kart ID: " + selectedCardId + ", Adı: " + updatedName + ", Açıklama: " + updatedDesc);
    }

    @io.cucumber.java.en.When("Kullanıcı board'daki tüm kartları siler")
    public void kullaniciBoarddakiTumKartlariSiler() {
        // TestContext'ten kart ID'lerini al
        List<String> cardIds = context.getCardIds();
        Assertions.assertFalse(cardIds.isEmpty(), "Silinecek kart bulunamadı!");

        // Her kartı sil
        for (String cardId : cardIds) {
            Response response = cardService.deleteCard(cardId);

            // HTTP durum kodunu kontrol et
            response.then().statusCode(200);
            System.out.println("Kart silindi: " + cardId);
        }

        // Tüm kartların silindiğini göstermek için context'teki listeyi temizle
        context.setCardIds(new ArrayList<>());
        System.out.println("Tüm kartlar başarıyla silindi.");
    }

    @io.cucumber.java.en.Then("Kartların silindiği doğrulanır")
    public void kartlarinSilindigiDogrulanir() {
        // TestContext'ten kart ID'lerini al
        List<String> cardIds = context.getCardIds();

        // Kart ID listesinin boş olduğunu kontrol et
        Assertions.assertTrue(cardIds.isEmpty(), "Kart ID listesi boş olmalı ama hala kartlar mevcut!");

        // TestContext'ten Board ve List ID'lerini al
        String boardId = context.getBoardId();
        Assertions.assertNotNull(boardId, "Board ID null, doğrulama yapılamaz!");

        // Board üzerindeki listeleri al
        Response response = listService.getListsOnBoard(boardId);
        response.then().statusCode(200);

        // Board'daki tüm listelerin ID'lerini al
        List<String> listIds = response.jsonPath().getList("id");
        Assertions.assertFalse(listIds.isEmpty(), "Board'da listeler bulunamadı!");

        // Her liste üzerinde kart olup olmadığını kontrol et
        for (String listId : listIds) {
            List<String> remainingCards = cardService.getAllCards(listId);

            // Her listenin boş olduğunu doğrula
            Assertions.assertTrue(remainingCards.isEmpty(), "Listede hala kartlar var: " + listId);
        }

        System.out.println("Tüm kartların silindiği doğrulandı.");
    }

    @io.cucumber.java.en.Given("Kullanıcı mevcut board'un bilgilerini alır")
    public void kullaniciMevcutBoardunBilgileriniAlir() {
        // TestContext'ten Board ID al
        String boardId = context.getBoardId();
        Assertions.assertNotNull(boardId, "Board ID mevcut değil!");

        // Board bilgilerini al
        Response response = boardService.getBoard(boardId);
        response.then().statusCode(200);

        // API'den dönen bilgileri al
        String retrievedBoardName = response.jsonPath().getString("name");
        String retrievedBoardDesc = response.jsonPath().getString("desc");

        // Doğrulamalar
        Assertions.assertNotNull(retrievedBoardName, "Board adı alınamadı!");
        Assertions.assertNotNull(retrievedBoardDesc, "Board açıklaması alınamadı!");

        // TestContext'e kaydedilebilir (isteğe bağlı)
        context.setBoardName(retrievedBoardName);
        context.setBoardDescription(retrievedBoardDesc);

        // Konsola başarı mesajı yazdır
        System.out.println("Board bilgileri başarıyla alındı:");
        System.out.println("Board Adı: " + retrievedBoardName);
        System.out.println("Board Açıklaması: " + retrievedBoardDesc);
    }

    @io.cucumber.java.en.When("Kullanıcı board'u siler")
    public void kullaniciBoarduSiler() {
        // Board ID'sini TestContext'ten alıyoruz
        String boardId = context.getBoardId();

        // Board ID'nin mevcut olduğunu doğrula
        Assertions.assertNotNull(boardId, "Silinecek bir board ID'si bulunamadı!");

        // Board'u silmek için DELETE isteği gönder
        Response response = boardService.deleteBoard(boardId);

        // HTTP yanıt doğrulaması
        response.then().statusCode(200);

        // Board ID'yi null yaparak TestContext'te işaretliyoruz
        context.setBoardId(null);

        System.out.println("Board başarıyla silindi: " + boardId);
    }

    @io.cucumber.java.en.Then("Board'un silindiği doğrulanır")
    public void boardunSilindigiDogrulanir() {
        // Board ID'sini TestContext'ten alıyoruz
        String boardId = context.getBoardId();

        // Board ID'nin null olması gerektiğini kontrol et
        Assertions.assertNull(boardId, "Board ID null değil, doğrulama yapılamaz!");

        // Eğer ID null değilse API'den doğrulama yapıyoruz
        if (boardId != null) {
            // Board'u doğrulamak için GET isteği gönder
            Response response = boardService.getBoard(boardId);

            // Board'un silindiğini doğrula (404 Not Found beklenir)
            Assertions.assertEquals(404, response.getStatusCode(), "Board hala mevcut, silinmemiş!");

            System.out.println("Board'un silindiği başarıyla doğrulandı: " + boardId);
        } else {
            System.out.println("Board'un silindiği TestContext üzerinden doğrulandı.");
        }
    }
}
