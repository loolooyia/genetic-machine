package implementation.model;

import implementation.robot.CarRobot;

import java.util.ArrayList;
import java.util.List;

public class Road {

    private List<Strip> strips;
    private int stripsCount;
    private double length;
    private double width;
    private double carSize;
    private double minSpeed;
    private double maxSpeed;
    private double minCount;
    private double maxCount;

    public Road(int stripsCount, double length, double width, double carSize, int minSpeed, int maxSpeed, int minCount, int maxCount) {
        this.stripsCount = stripsCount;
        this.length = length;
        this.width = width;
        this.carSize = carSize;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.minCount = minCount;
        this.maxCount = maxCount;
        generateStrips();
    }

    private void generateStrips() {
        strips = new ArrayList<>();
        for (int i = 0; i < stripsCount; i++) {
            double rndMinSpeed = Math.random() * ((maxSpeed - minSpeed) / 2 - minSpeed) + minSpeed;
            double rndMaxSpeed = Math.random() * (maxSpeed - (maxSpeed - minSpeed) / 2) + (maxSpeed - minSpeed) / 2;
            double rndMinCount = Math.random() * ((maxCount - minCount) / 2 - minCount) + minCount;
            double rndMaxCount = Math.random() * (maxCount - (maxCount - minCount) / 2) + (maxCount - minCount) / 2;
            strips.add(new Strip(rndMinSpeed, rndMaxSpeed, rndMinCount, rndMaxCount));
            strips.get(strips.size() - 1).generateCars(this);
        }
    }

    public void resetRobotX(CarRobot robot) {
        robot.setX(Math.random() * (stripsCount * width - carSize * 2) + carSize);
    }

    public List<Strip> getStrips() {
        return strips;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getCarSize() {
        return carSize;
    }
}
