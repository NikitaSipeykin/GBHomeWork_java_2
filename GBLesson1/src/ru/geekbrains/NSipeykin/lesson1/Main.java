package ru.geekbrains.NSipeykin.lesson1;

public class Main {
//TODO 1. Создайте три класса Человек, Кот, Робот, которые не наследуются от одного класса. Эти классы должны уметь
// бегать и прыгать (методы просто выводят информацию о действии в консоль).
//TODO 2. Создайте два класса: беговая дорожка и стена, при прохождении через которые, участники должны выполнять
// соответствующие действия (бежать или прыгать), результат выполнения печатаем в консоль (успешно пробежал, не
// смог пробежать и т.д.).
//TODO 3. Создайте два массива: с участниками и препятствиями, и заставьте всех участников пройти этот набор
// препятствий.
//TODO 4. * У препятствий есть длина (для дорожки) или высота (для стены), а участников ограничения на бег и
// прыжки. Если участник не смог пройти одно из препятствий, то дальше по списку он препятствий не идет.

    public static void main(String[] args) {
        Activity[] objects = {
            new Cat(),
            new Robot(),
            new Human()
        };

        Obstacle[] obstacles = {
            new Track(),
            new Wall()
        };

        obstacleCourse(objects, obstacles);
    }

    static void obstacleCourse(Activity[] object, Obstacle[] obstacles){

        for (int i = 0; i < obstacles.length; i++) {
            for (int j = 0; j < object.length; j++) {
                if(object[j] != null){
                    if (!obstacles[i].overcomingObstacles(object[j])){
                        object[j]=null;
                    }
                }
            }
        }
    }
}
