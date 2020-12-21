package ru.geekbrains.NSipeykin.lesson2;

import java.util.Arrays;

public class Main {
//TODO:
// 1. Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4, при подаче массива
// другого размера необходимо бросить исключение MyArraySizeException.
//TODO:
// 2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать. Если в каком-то
// элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа), должно быть
// брошено исключение MyArrayDataException, с детализацией в какой именно ячейке лежат неверные данные.
//TODO:
// 3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException и
// MyArrayDataException, и вывести результат расчета.

    static int[][] converter(String[][] stringArr){

        sizeControl(stringArr);

        int[][] intArr = new int[4][4];
        for (int i = 0; i < stringArr.length; i++) {
            for (int j = 0; j < stringArr[i].length; j++) {
                if(!validElementsControl(i, j, stringArr[i][j])) {
                    intArr[i][j] = Integer.parseInt(stringArr[i][j]);
                }
            }
        }
        return intArr;
    }

    static int sumUpAllInArray(int[][] intArr){

        int SUM = 0;

        for (int i = 0; i < intArr.length; i++) {
            for (int j = 0; j < intArr[i].length; j++) {
                SUM += intArr[i][j];
            }
        }
        return SUM;
    }

    static String[][] sizeControl(String[][] arr) throws MyArraySizeException{

        if (arr.length!=4){
            throw new MyArraySizeException();
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length!=4){
                throw new MyArraySizeException();
            }
        }
        return arr;
    }

    static boolean validElementsControl(int x, int y, String arr) throws MyArrayDataException{

        try {
            Integer.parseInt(arr);
        }catch (NumberFormatException e){
            System.err.println("Exception was catch, in such an array cell ["+x+"]["+y+"]. For input string: \""+arr+"\"");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
       ArrCreater arr = new ArrCreater(); // the created class contains various arrays
       String[][] arr1 = arr.incorrectArrCreator_IncElements();

        try {
            System.out.println(sumUpAllInArray(converter(arr1)));
        }catch (MyArraySizeException e){
            System.err.println("MyArraySizeException was catch!");
        }
   }
}
