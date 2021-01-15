package ru.geekBrains.nikSipeykin.lesson5;

public class Main {
//:
// 1. Необходимо написать два метода, которые делают следующее:
//            1) Создают одномерный длинный массив, например:
//
    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];
//
//
//          2) Заполняют этот массив единицами;
//:
//          3) Засекают время выполнения: long a = System.currentTimeMillis();
//:
//          4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
//    arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//:
//          5) Проверяется время окончания метода System.currentTimeMillis();
//TODO:
//          6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
//
//TODO:   Отличие первого метода от второго:
//    Первый просто бежит по массиву и вычисляет значения.
//    Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы
//    обратно в один.
//
//    Пример деления одного массива на два:
//
//            System.arraycopy(arr, 0, a1, 0, h);
//System.arraycopy(arr, h, a2, 0, h);
//
//    Пример обратной склейки:
//
//            System.arraycopy(a1, 0, arr, 0, h);
//System.arraycopy(a2, 0, arr, h, h);
//
//TODO:    Примечание:
//            System.arraycopy() – копирует данные из одного массива в другой:
//            System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение,
//            откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
//    По замерам времени:
//    Для первого метода надо считать время только на цикл расчета:
//
//            for (int i = 0; i < size; i++) {
//        arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//    }
//
//TODO:    Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.

    static float[] arrCreator(float[] arr){

        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1f;
        }
        return arr;
    }

    static float[] arrCalculation(float[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return arr;
    }

    static void arr1(float[] arr){

    arr = arrCreator(arr);

    long startTime = System.currentTimeMillis();

    arrCalculation(arr);
    long endTime = System.currentTimeMillis();
    long output = endTime - startTime;
    System.out.println("Array 1 was completed in "+ output + " milisec." );
}

    static void arr2(float[]arr){
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        arrCreator(arr);
        Thread t1 = new Thread(() -> {
            arrCalculation(a1);
        });

        Thread t2 = new Thread(() ->{
            arrCalculation(a2);
        });

        long startTime = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        t1.start();
        arrCalculation(a2);

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        long endTime = System.currentTimeMillis();

        long output = endTime - startTime;
        System.out.println("Array 2 was completed in "+ output + " milisec." );
    }

    public static void main(String[] args) {
        arr1(arr);
        arr2(arr);

    }

}
