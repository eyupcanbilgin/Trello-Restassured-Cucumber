# Web Servis Otomasyon Projesi

Bu proje, Trello API'si üzerinde otomasyon testleri gerçekleştirmek için tasarlanmıştır. Projede **Rest-Assured** kütüphanesi kullanılarak çeşitli CRUD işlemleri gerçekleştirilmiştir.

## Gereksinimler
- **Programlama Dili:** Java
- **Proje Yapısı:** Maven tabanlı proje
- **Kütüphane:** Rest-Assured
- **Paylaşım:** Proje GitHub üzerinden paylaşılmaktadır: [Proje Linki](https://github.com/eyupcanbilgin/Trello-Restassured-Cucumber)
- **Kod Yapısı:**
  - OOP (Object Oriented Programming) prensiplerine uygun olarak yazılmıştır.
  - Page Object Pattern (POP) kullanılmıştır.

## Kullanılan Teknolojiler
- **Java 8+**
- **Maven** (Bağımlılık yönetimi)
- **Rest-Assured** (API test otomasyonu için)
- **Cucumber** (BDD yapısı için)

## API Bilgileri
- **API Key ve Token:** 
  - Trello API Key ve Token bilgileri kullanılmaktadır.
  - Token bilgilerine Trello’da oturum açarak [Token URL](https://trello.com/app-key) üzerinden erişebilirsiniz.
- **Trello API Dokümantasyonu:**
  - Resmi dokümantasyona [Trello REST API](https://developer.atlassian.com/cloud/trello/guides/rest-api/api-introduction/) üzerinden erişebilirsiniz.

## Senaryo Adımları
Proje aşağıdaki adımları gerçekleştirmek için tasarlanmıştır:

1. **Trello üzerinde bir board oluşturun.**
2. **Oluşturduğunuz board’a iki adet kart ekleyin.**
3. **Bu iki karttan rastgele birini güncelleyin.**
4. **Oluşturduğunuz tüm kartları silin.**
5. **Oluşturduğunuz board’u silin.**

## Proje Yapısı
- **src/main/java:** İşlevsel kodların bulunduğu dizin
  - `services`: Trello API işlemlerini gerçekleştiren metodlar
  - `utils`: Yardımcı sınıflar ve ortak işlemler
- **src/test/java:** Test senaryoları
  - `features`: Cucumber senaryo dosyaları (.feature)
  - `steps`: Cucumber adım tanımlamaları
- **pom.xml:** Maven bağımlılık yönetimi dosyası

## Projeyi Çalıştırma
1. Projeyi klonlayın:
   ```bash
   git clone https://github.com/eyupcanbilgin/Trello-Restassured-Cucumber.git
   cd Trello-Restassured-Cucumber
Maven bağımlılıklarını yükleyin:
bash
Kodu kopyala
mvn clean install
Testleri çalıştırın:
bash
Kodu kopyala
mvn test
