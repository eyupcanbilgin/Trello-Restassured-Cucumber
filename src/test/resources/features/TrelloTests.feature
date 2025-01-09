Feature: Trello API ile Board ve Kart İşlemleri

  Scenario: Tüm Trello Board ve Kart İşlemleri
    Given Kullanıcı Trello API için gerekli bilgileri hazırlar
    When Kullanıcı yeni bir board oluşturur
    Then Oluşturulan board doğrulanır
    And Kullanıcı board'a iki adet kart ekler
    Then Kartlar doğrulanır
    Given Kullanıcı board'daki mevcut kartları alır
    When Kullanıcı kartlardan bir tanesini random seçer
    And Kullanıcı random seçilen kartı günceller
    Then Güncellenen kart doğrulanır
    Given Kullanıcı board'daki mevcut kartları alır
    When Kullanıcı board'daki tüm kartları siler
    Then Kartların silindiği doğrulanır
    Given Kullanıcı mevcut board'un bilgilerini alır
    When Kullanıcı board'u siler
    Then Board'un silindiği doğrulanır
