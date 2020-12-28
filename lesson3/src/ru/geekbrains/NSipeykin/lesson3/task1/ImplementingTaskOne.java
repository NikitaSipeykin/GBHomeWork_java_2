package ru.geekbrains.NSipeykin.lesson3.task1;

import java.util.*;

public class ImplementingTaskOne {
    //TODO:
    // 1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся). Найти и вывести список уникальных
    // слов, из которых состоит массив (дубликаты не считаем). Посчитать сколько раз встречается каждое слово.

    public ImplementingTaskOne() {
        ArrayList<String> alcohol = new ArrayList<>();
        alcohol.add("Brandy");
        alcohol.add("Whiskey");
        alcohol.add("Absent");
        alcohol.add("Tequila");
        alcohol.add("Rum");
        alcohol.add("Gin");
        alcohol.add("Vermouth");
        alcohol.add("Liquor");
        alcohol.add("Vodka");
        alcohol.add("Mezcal");
        alcohol.add("Vine");
        alcohol.add("Brandy");
        alcohol.add("Absent");
        alcohol.add("Rum");
        alcohol.add("Vermouth");
        alcohol.add("Vine");
        alcohol.add("Gin");
        alcohol.add("Vermouth");
        alcohol.add("Liquor");
        alcohol.add("Vodka");
        alcohol.add("Mezcal");
        alcohol.add("Vine");
        alcohol.add("Brandy");
        alcohol.add("Absent");
        alcohol.add("Rum");
        alcohol.add("Vermouth");
        alcohol.add("Vine");
        System.out.println("Tis is default value - " + alcohol);

        Map<String, Integer> a = new TreeMap<>();
        for(String word : alcohol){
            a.merge(word,1, Integer::sum);
        }

        for(Map.Entry<String, Integer> words : a.entrySet()){
            System.out.println("Word \"" + words.getKey() + "\" comes across " + words.getValue() + ";");
        }
    }
}