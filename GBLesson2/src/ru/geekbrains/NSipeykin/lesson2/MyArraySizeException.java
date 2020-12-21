package ru.geekbrains.NSipeykin.lesson2;

public class MyArraySizeException extends ArrayIndexOutOfBoundsException{

    public MyArraySizeException() {
        super("Array index out of range: ");
    }

}
