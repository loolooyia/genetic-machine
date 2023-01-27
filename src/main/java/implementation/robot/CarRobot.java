package implementation.robot;

import genetic.Entity;
import genetic.Gen;
import genetic.GenPool;
import implementation.main.Constants;

import java.util.List;

public class CarRobot extends Entity {

    private double x;
    private double y;
    private double size;
    private double speed;
    private double acceleration;
    private double angle;
    private double sensor0, sensor1, sensor2, sensor3;
    private double time;

    public CarRobot(GenPool genPool, int genCount, double mutationRatio, double mutationProbability) {
        super(genPool, genCount, mutationRatio, mutationProbability);
        reset();
    }

    private CarRobot(GenPool genPool, double mutationRatio, double mutationProbability, List<Gen> genes) {
        super(genPool, mutationRatio, mutationProbability, 0, genes);
        reset();
    }

    public void reset() {
        x = Constants.CAR_SIZE;
        y = Constants.ROBOT_START_Y;
        size = Constants.CAR_SIZE;
        speed = Constants.ROBOT_START_SPEED;
        acceleration = 0;
        angle = 0.1;
        sensor0 = 0;
        sensor1 = 0;
        sensor2 = 0;
        sensor3 = 0;
        time = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSize() {
        return size;
    }


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getSensor0() {
        return sensor0;
    }

    public void setSensor0(double sensor0) {
        this.sensor0 = sensor0;
    }

    public double getSensor1() {
        return sensor1;
    }

    public void setSensor1(double sensor1) {
        this.sensor1 = sensor1;
    }

    public double getSensor2() {
        return sensor2;
    }

    public void setSensor2(double sensor2) {
        this.sensor2 = sensor2;
    }

    public double getSensor3() {
        return sensor3;
    }

    public void setSensor3(double sensor3) {
        this.sensor3 = sensor3;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    protected Entity subClone(Entity entity) {
        return new CarRobot(genPool, entity.getMutationRatio(), entity.getMutationProbability(), entity.getGenes());
    }
}
