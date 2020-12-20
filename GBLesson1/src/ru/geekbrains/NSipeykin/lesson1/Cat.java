package ru.geekbrains.NSipeykin.lesson1;

public class Cat implements Activity{

    public int objectRunDistance = 150;
    public int objectJumpHeight = 5;

    @Override
    public boolean jump(int height) {
        if (height<objectJumpHeight) {
            System.out.println("Cat, jump over obstacle!");
            return true;
        }
        else {
            System.out.println("Cat, didn't jump over obstacle!");
            return false;
        }
    }

    @Override
    public boolean run(int distance) {
        if (distance<objectRunDistance) {
            System.out.println("Cat, ran the distance!");
            return true;
        }
        else {
            System.out.println("Cat, didn't ran the distance!");
            return false;
        }
    }
}
