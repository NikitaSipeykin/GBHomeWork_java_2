package ru.geekbrains.NSipeykin.lesson1;

public class Wall implements Obstacle{
    public final int height = 5;

    @Override
    public boolean overcomingObstacles(Activity object){
        return object.jump(height);
    }
}
