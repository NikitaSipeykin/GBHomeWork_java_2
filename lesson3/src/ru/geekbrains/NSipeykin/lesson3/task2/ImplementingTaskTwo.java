package ru.geekbrains.NSipeykin.lesson3.task2;

public class ImplementingTaskTwo {
//TODO:
// 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
// В этот телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать номер
// телефона по фамилии. Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
// тогда при запросе такой фамилии должны выводиться все телефоны.
    private static PhoneDirectory phoneDirectory = new PhoneDirectory();
    public ImplementingTaskTwo() {

        add("Иванов","89999999999");
        add("Петров","89999999998");
        add("Сидоров","89999999997");
        add("Хачатурян","89999999996");
        add("Петров","89999999995");
        add("Иванов","89999999994");
        add("Сидоров","89999999993");
        add("Петров","89999999992");
        add("Хачатурян","89999999991");
        add("Иванов","89999999990");
        add("Хачатурян","89999999989");
        add("Хачатурян","89999999988");


        show("Иванов");
        show("Петров");
        show("Сидоров");
        show("Хачатурян");

    }
    private static void add(String name, String number){
        phoneDirectory.add(name, number);
    }

    private static void show(String name){
        System.out.println(name + " phone numbers:");
        for(String s : phoneDirectory.get(name)){
            System.out.println(s);
        }
    }
}
