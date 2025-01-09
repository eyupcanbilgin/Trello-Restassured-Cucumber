package com.paytr.trello.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {

    /**
     * Bir listeden rastgele bir öğe seçer.
     *
     * @param list Rastgele seçim yapılacak liste
     * @param <T> Listenin türü
     * @return Listeden rastgele bir öğe
     */
    public static <T> T getRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Liste boş olamaz veya null olamaz.");
        }
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}
