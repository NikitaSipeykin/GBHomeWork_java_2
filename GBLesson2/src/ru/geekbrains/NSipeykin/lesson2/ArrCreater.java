package ru.geekbrains.NSipeykin.lesson2;

public class ArrCreater {

    String[][] correctArrCreator () {
        String[][] arr = {
            {"1", "2", "3", "4"},
            {"1", "2", "3", "4"},
            {"1", "2", "3", "4"},
            {"1", "2", "3", "4"}
        };
        return arr;
    }

    String[][] incorrectArrCreator_SizeLess () { // array size less than 4x4
        String[][] arr = {
                {"1", "2", "3" },
                {"1", "2", "3", "4"},
                {"1", "2" },
                {"1", "2", "3", "4"}
        };
        return arr;
    }

    String[][] incorrectArrCreator_SizeGreater () { // array size greater than 4x4
        String[][] arr = {
                {"1", "2", "3", "4","5"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4","5"},
                {"1", "2", "3", "4"}
        };
        return arr;
    }

    String[][] incorrectArrCreator_IncElements () { // array have incorrect elements
        String[][] arr = {
                {"one", "2", "3", "4"},
                {"1", "two", "3", "4"},
                {"1", "2", "tree", "4"},
                {"1", "2", "3", "four"}
        };
        return arr;
    }

    String[][] incorrectArrCreator_SizeLess_IncElements () { // array have incorrect elements and size not 4x4
        String[][] arr = {
                {"one", "2", "4"},
                {"1", "two", "3", "4"},
                {"1", "2", "tree", "4"},
                {"1", "2", "4"},
                {"1", "2", "tree", "4"},
                {"1", "2", "3", "four"}
        };
        return arr;
    }

    String[][] incorrectArrCreator_SizeGreater_IncElements () { // array have incorrect elements and size not 4x4
        String[][] arr = {
                {"one", "2", "3"},
                {"1", "two", "3", "4","5"},
                {"1", "2", "tree", "4"},
                {"1", "2", "tree", "4","5"},
                {"1", "2", "tree", "4"},
                {"1", "2", "3", "four"}
        };
        return arr;
    }
}
