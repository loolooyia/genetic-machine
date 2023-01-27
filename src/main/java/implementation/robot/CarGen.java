package implementation.robot;

import genetic.Entity;
import genetic.Gen;

public class CarGen implements Gen {

    private int action;
    private int weight;
    private int sensor;
    private int state;

    public CarGen(int action, int weight, int sensor, int state) {
        this.action = action;
        this.weight = weight;
        this.sensor = sensor;
        this.state = state;
    }

    @Override
    public void action(Entity entity) {
        CarRobot robot = (CarRobot) entity;
        if (checkSensorValue(robot)) updateRobotState(robot);
    }

    private boolean checkSensorValue(CarRobot robot) {
        double value = 0;
        if (sensor == 0) value = robot.getSensor0();
        if (sensor == 1) value = robot.getSensor1();
        if (sensor == 2) value = robot.getSensor2();
        if (sensor == 3) value = robot.getSensor3();

        if (sensor == 0) {
            if (state == 0) return value == 0;
            if (state == 1) return value == 1;
            if (state == 2) return value == 2;
            if (state == 3) return value == 3;
            if (state == 4) return value > 0;
        } else if (sensor < 4) {
            if (state == 0) return value <= 0.5;
            if (state == 1) return value > 0.5 & value <= 2;
            if (state == 2) return value > 2 & value <= 5;
            if (state == 3) return value > 5 & value <= 10;
            if (state == 4) return value > 10;
        }
        return false;
    }

    private void updateRobotState(CarRobot robot) {
        double value = 0;
        if (weight == 0) value = -3;
        if (weight == 1) value = -2;
        if (weight == 2) value = -1;
        if (weight == 3) value = 0;
        if (weight == 4) value = 1;
        if (weight == 5) value = 2;
        if (weight == 6) value = 3;

        if (action == 0) robot.setAcceleration(robot.getAcceleration() + value);
        if (action == 1) robot.setAngle(robot.getAngle() + value);
    }

    public int[] getCode() {
        return new int[]{action, weight, sensor, state};
    }
}
