package ru.geekbrains.NSipeykin.lesson1;

public class Robot implements Activity{
    public int objectRunDistance = 1000;
    public int objectJumpHeight = 15;

    @Override
    public boolean jump(int height) {
        if (height<objectJumpHeight) {
            System.out.println("Robot, jump over obstacle!");
            return true;
        }
        else {
            System.out.println("Robot, didn't jump over obstacle!");
            return false;
        }
    }

    @Override
    public boolean run(int distance) {
        if (distance<objectRunDistance) {
            System.out.println("Robot, ran the distance!");
            return true;
        }
        else {
            System.out.println("Robot, didn't ran the distance!");
            return false;
        }
    }
}
