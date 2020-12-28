package ru.geekbrains.NSipeykin.lesson3.task2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PhoneDirectory {
    Map<String,Set<String>> phoneDirectory = new HashMap<>();

    public void add(String name, String number){
        Set<String> phones = get(name);
        phones.add(number);
    }

    public Set<String> get(String name){
        Set<String> phones;
        phones = phoneDirectory.getOrDefault(name, new TreeSet<>());
        if( phones.isEmpty()){
            phoneDirectory.put(name, phones);
        }
        return phones;
    }
}
