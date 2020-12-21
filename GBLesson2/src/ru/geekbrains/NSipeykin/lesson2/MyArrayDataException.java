package ru.geekbrains.NSipeykin.lesson2;

public class MyArrayDataException extends NumberFormatException{

    public MyArrayDataException() {
    }

    public MyArrayDataException(int x, int y, String s) {

        super(s);
        System.err.println("Exception was catch, in such an array cell ["+x+"]["+y+"]. For input string: \""+s+"\"");
    }
}
