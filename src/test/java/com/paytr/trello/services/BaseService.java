package com.paytr.trello.services;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * BaseService sınıfı, RestAssured kullanarak API çağrılarında ortak olarak kullanılan
 * yapılandırmaları ve yardımcı işlevleri sağlayan soyut bir sınıftır.
 * Bu sınıfın temel amacı:
 * - Tüm servislere ortak olan yapılandırma işlemlerini bir arada tutmak.
 * - `.env` dosyasından API anahtarı, token ve temel URL gibi bilgileri yüklemek ve yönetmek.
 * - RequestSpecification nesnesini önceden tanımlayarak, tüm API isteklerinde tekrarlanan
 * kod yazma gerekliliğini ortadan kaldırmak.
 * Sağlanan özellikler:
 * - `.env` dosyasından ortam değişkenlerini dinamik olarak yükler.
 * - Tüm API çağrıları için geçerli olacak bir `RequestSpecification` oluşturur.
 * - Request ve Response loglama işlemlerini otomatik olarak ekler.
 * - Hatalı veya eksik ortam değişkenleri durumunda anlamlı hata mesajları döndürür.
 * Bu sınıf soyut bir sınıf olarak tasarlanmıştır, dolayısıyla doğrudan kullanılamaz.
 * Ancak diğer servis sınıfları (örneğin `BoardService`, `CardService`) bu sınıftan
 * türetilerek API çağrıları için gerekli özellikleri devralabilir.
 */

public abstract class BaseService {

    // .env dosyasındaki değişkenleri yüklemek için kullanılan Dotenv nesnesi
    private static final Dotenv dotenv;

    // API istekleri için ortak yapılandırmayı tutan RequestSpecification nesnesi
    protected static RequestSpecification spec;

    static {
        try {
            // .env dosyasını projenin kök dizininden yükler
            dotenv = Dotenv.configure()
                    .directory(System.getProperty("user.dir")) // Projenin kök dizininden yükle
                    .load();

            // Yüklenen değerleri kontrol et
            System.out.println("BASE_URI: " + dotenv.get("BASE_URI"));
            System.out.println("TRELLO_API_KEY: " + dotenv.get("TRELLO_API_KEY"));
            System.out.println("TRELLO_API_TOKEN: " + dotenv.get("TRELLO_API_TOKEN"));

            // RequestSpecification yapılandırması
            spec = new RequestSpecBuilder()
                    .setBaseUri(getEnvVar("BASE_URI"))
                    .addQueryParam("key", getEnvVar("TRELLO_API_KEY"))
                    .addQueryParam("token", getEnvVar("TRELLO_API_TOKEN"))
                    .addFilter(new RequestLoggingFilter())
                    .addFilter(new ResponseLoggingFilter())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error loading .env file or initializing BaseService: " + e.getMessage(), e);
        }
    }

    /**
     * Ortam değişkenlerini güvenli bir şekilde almak için yardımcı bir metot.
     * Eğer istenen değişken bulunamazsa anlamlı bir hata mesajı döner.
     *
     * @param key Ortam değişkeninin anahtarı
     * @return Ortam değişkeninin değeri
     */

    private static String getEnvVar(String key) {
        String value = dotenv.get(key);
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Environment variable '" + key + "' not found in .env file.");

        }
        return value;
    }

    /**
     * API isteklerini hazırlamak için bir RequestSpecification döner.
     * Bu metot, JSON formatında istek göndermek için gerekli başlıkları içerir.
     *
     * @return API isteği hazırlamak için yapılandırılmış bir RequestSpecification
     */

    protected static RequestSpecification prepareRequest() {
        return given().spec(spec).header("Content-Type", "application/json");

    }

}
