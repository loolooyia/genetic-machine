package implementation.robot;

import genetic.Entity;
import genetic.Gen;
import genetic.World;
import implementation.main.Constants;
import implementation.model.Car;
import implementation.model.Road;
import implementation.model.Strip;

import java.util.List;

public class CarWorld extends World {

    private List<Road> roads;

    public CarWorld(List<Road> roads) {
        this.roads = roads;
    }

    @Override
    public void doAction(Entity entity) {
        super.doAction(entity);
        CarRobot robot = (CarRobot) entity;
        int time = Constants.DELTA_T;
        boolean status = true;
        robot.setRating(0);
        for (Road road : roads) {
            robot.reset();
            road.resetRobotX(robot);
            double rating = robot.getRating();
            while (status) {
                status = calcChanges(time, robot, road);
                entity.setRating(rating + robot.getY());
            }
        }
    }

    public boolean calcChanges(double time, CarRobot robot, Road road) {
        if (checkAlive(robot, road)) {
            calcSensorValues(robot, road);
            doGeneticActions(robot);
            calcNewPositions(time, robot, road);
            return true;
        } else {
            return false;
        }
    }

    private void calcSensorValues(CarRobot robot, Road road) {
        int closeCarCount = 0;
        double k1 = 1 / Math.tan(Math.toRadians(robot.getAngle()));
        double k2 = 1 / Math.tan(Math.toRadians(robot.getAngle() + 45));
        double k3 = 1 / Math.tan(Math.toRadians(robot.getAngle() - 45));
        double c1 = robot.getY() - k1 * robot.getX();
        double c2 = robot.getY() - k2 * robot.getX();
        double c3 = robot.getY() - k3 * robot.getX();
        double min1 = Double.MAX_VALUE, min2 = Double.MAX_VALUE, min3 = Double.MAX_VALUE;

        for (Strip strip : road.getStrips()) {
            for (Car car : strip.getCars()) {
                car.setSelected(false);
                if (calcLine(car.getX(), car.getY(), robot.getX(), robot.getY()) < robot.getSize() * 3) {
                    closeCarCount++;
                    car.setSelected(true);
                }
                if (calcB2(car.getX(), car.getY(), c1, k1) >= calc4AC(car.getX(), car.getY(), c1, k1, car.getSize())) {
                    if (isInFront(robot.getAngle(), robot.getX(), robot.getY(), car.getX(), car.getY())) {
                        min1 = Math.min(calcLine(car.getX(), car.getY(), robot.getX(), robot.getY()), min1);
                        car.setSelected(true);
                    }
                }
                if (calcB2(car.getX(), car.getY(), c2, k2) >= calc4AC(car.getX(), car.getY(), c2, k2, car.getSize())) {
                    if (isInFront(robot.getAngle(), robot.getX(), robot.getY(), car.getX(), car.getY())) {
                        min2 = Math.min(calcLine(car.getX(), car.getY(), robot.getX(), robot.getY()), min2);
                        car.setSelected(true);
                    }
                }
                if (calcB2(car.getX(), car.getY(), c3, k3) > calc4AC(car.getX(), car.getY(), c3, k3, car.getSize())) {
                    if (isInFront(robot.getAngle(), robot.getX(), robot.getY(), car.getX(), car.getY())) {
                        min3 = Math.min(calcLine(car.getX(), car.getY(), robot.getX(), robot.getY()), min3);
                        car.setSelected(true);
                    }
                }
            }
        }

        double w = road.getWidth() * (road.getStrips().size() - 1) + 0.5 * road.getWidth();
        min1 = Math.min(calcLine(0, c1, robot.getX(), robot.getY()), min1);
        min1 = Math.min(calcLine(w, k1 * w + c1, robot.getX(), robot.getY()), min1);
        min2 = Math.min(calcLine(0, c2, robot.getX(), robot.getY()), min2);
        min2 = Math.min(calcLine(w, k2 * w + c2, robot.getX(), robot.getY()), min2);
        min3 = Math.min(calcLine(0, c3, robot.getX(), robot.getY()), min3);
        min3 = Math.min(calcLine(w, k3 * w + c3, robot.getX(), robot.getY()), min3);


        robot.setSensor0(closeCarCount);
        robot.setSensor1(min1 / robot.getSize());
        robot.setSensor2(min2 / robot.getSize());
        robot.setSensor3(min3 / robot.getSize());
    }

    private void doGeneticActions(CarRobot robot) {
        double maxSpeed = Constants.ROBOT_MAX_SPEED;
        for (Gen gen : robot.getGenes()) {
            gen.action(robot);
        }
        if (Math.abs(robot.getSpeed()) > maxSpeed) {
            robot.setSpeed(maxSpeed);
            robot.setAcceleration(0);
        }
    }

    private void calcNewPositions(double time, CarRobot robot, Road road) {
        time = time / 1000;
        robot.setTime(robot.getTime() + time);
        robot.setSpeed(robot.getSpeed() + robot.getAcceleration() * time);
        robot.setX(robot.getX() + robot.getSpeed() * time * Math.sin(robot.getAngle() * Math.PI / 180));
        robot.setY(robot.getY() + robot.getSpeed() * time * Math.cos(robot.getAngle() * Math.PI / 180));

        for (Strip strip : road.getStrips()) {
            for (Car car : strip.getCars()) {
                car.setY(car.getY() + car.getSpeed() * time);
                if (car.getY() > road.getLength()) {
                    car.setY(car.getY() - road.getLength());
                }
            }
        }
    }

    private boolean checkAlive(CarRobot robot, Road road) {
        if (robot.getTime() > Constants.ROBOT_TIME_TO_DEATH) {
            return false;
        }
        if (robot.getY() < 0 || robot.getY() > road.getLength()) {
            return false;
        }
        if (robot.getX() < 0 || robot.getX() > (road.getStrips().size() - 1) * road.getWidth() + 0.5 * road.getWidth()) {
            return false;
        }
        for (Strip strip : road.getStrips()) {
            for (Car car : strip.getCars()) {
                if (calcLine(car.getX(), car.getY(), robot.getX(), robot.getY()) < (robot.getSize() + car.getSize()) / 2) {
                    return false;
                }
            }
        }
        return true;
    }

    private double calcB2(double a, double b, double c, double k) {
        return Math.pow(c * k - b * k - a, 2);
    }

    private double calc4AC(double a, double b, double c, double k, double r) {
        return (1 + k * k) * (a * a + b * b + c * c - 2 * b * c - r * r);
    }

    private double calcLine(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
    }

    private boolean isInFront(double alpha, double x, double y, double px, double py) {
        double alphaRad = Math.toRadians(alpha);
        double sinA = Math.sin(alphaRad);
        double cosA = Math.cos(alphaRad);
        return (py - y) * cosA - (px - x) * sinA >= 0;
    }


}
