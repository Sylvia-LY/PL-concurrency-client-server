package org.example;

public class Factory {
    public static AppModel newOSModel() {
        return new OSModel();
    }

    public static AppModel newRobotModel() {
        return new RobotModel();
    }

}
