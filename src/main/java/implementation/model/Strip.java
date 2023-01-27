package implementation.model;

import java.util.ArrayList;
import java.util.List;

public class Strip {

    private double minSpeed;
    private double maxSpeed;
    private double minCount;
    private double maxCount;
    private List<Car> cars;

    public Strip(double minSpeed, double maxSpeed, double minCount, double maxCount) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.minCount = minCount;
        this.maxCount = maxCount;
        cars = new ArrayList<>();
    }

    public void generateCars(Road road) {
        int count = (int) (Math.random() * (maxCount - minCount) + minCount);
        double speed = Math.random() * (maxSpeed - minSpeed) + minSpeed;
        int size = (int) road.getCarSize();
        for (int i = 0; i < count; i++) {
            double y = getRandomPosition(road.getLength(), size);
            if (Double.isNaN(y)) {
                break;
            } else {
                double x = 0;
                for (int j = 0; j < road.getStrips().size(); j++) {
                    if (road.getStrips().get(j).equals(this)) {
                        x = road.getWidth() * j + road.getWidth() / 2 - road.getCarSize() / 2;
                        break;
                    }
                }
                cars.add(new Car(x, y, speed, size));
            }
        }
    }

    private double getRandomPosition(double length, double size) {
        double value = Math.random() * (length - length / 10) + length / 20;
        int tryCount = 0;
        int tryMax = 100;
        boolean isFound = true;
        while (tryCount < tryMax) {
            for (Car car : cars) {
                if (value >= car.getY() - car.getSize() - size && value <= car.getY() + car.getSize() + size) {
                    isFound = false;
                    break;
                }
            }
            if (isFound) {
                return value;
            }
            tryCount++;
        }
        return Double.NaN;
    }

    public List<Car> getCars() {
        return cars;
    }
}
