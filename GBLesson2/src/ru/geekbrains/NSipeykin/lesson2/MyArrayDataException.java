package ru.geekbrains.NSipeykin.lesson2;

public class MyArrayDataException extends NumberFormatException{

    public MyArrayDataException() {
    }

    public MyArrayDataException(String s) {

        super(s);
        //return new MyArrayDataException("Exception was catch, in such an array cell ["+x+"]["+y+"]. For input string: \""+s+"\"");
    }
}
