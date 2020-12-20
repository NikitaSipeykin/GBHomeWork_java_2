package ru.geekbrains.NSipeykin.lesson1;

public class Track implements Obstacle{
    public final int distance = 200;

    @Override
    public boolean overcomingObstacles(Activity object) {
        return object.run(distance);
    }
}
