package ru.geekbrains.NSipeykin.lesson1;

public class Human implements Activity{
    public int objectRunDistance = 300;
    public int objectJumpHeight = 10;

    @Override
    public boolean jump(int height) {
        if (height<objectJumpHeight) {
            System.out.println("Human, jump over obstacle!");
            return true;
        }
        else {
            System.out.println("Human, didn't jump over obstacle!");
            return false;
        }
    }

    @Override
    public boolean run(int distance) {
        if (distance<objectRunDistance) {
            System.out.println("Human, ran the distance!");
            return true;
        }
        else {
            System.out.println("Human, didn't ran the distance!");
            return false;
        }
    }
}
