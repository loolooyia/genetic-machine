package implementation.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import implementation.model.Car;
import implementation.model.Road;
import implementation.robot.CarRobot;

import java.io.IOException;

public class RoadView extends AnchorPane {

    @FXML
    private Canvas canvas;

    private Road road;
    private CarRobot robot;

    public RoadView() {
        loadFxml();
        initCanvas();
    }

    private void initCanvas() {
        this.widthProperty().addListener(observable -> updateCanvas());
        this.heightProperty().addListener(observable -> updateCanvas());
    }

    private void updateCanvas() {
        canvas.setWidth(getWidth());
        canvas.setHeight(getHeight());
        draw();
    }

    public void draw() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (road != null) {
            canvas.getGraphicsContext2D().setFill(Color.BLACK);
            for (int i = 0; i < road.getStrips().size() + 1; i++) {
                canvas.getGraphicsContext2D().strokeLine(i * road.getWidth(), canvas.getHeight() / 2 - robot.getY(), i * road.getWidth(), road.getLength() + canvas.getHeight() / 2 - robot.getY());
                if (i < road.getStrips().size()) {
                    for (Car car : road.getStrips().get(i).getCars()) {
                        if (car.isSelected()) {
                            canvas.getGraphicsContext2D().setFill(Color.GREEN);
                        } else {
                            canvas.getGraphicsContext2D().setFill(Color.BLACK);
                        }
                        canvas.getGraphicsContext2D().fillOval(car.getX(), car.getY() + canvas.getHeight() / 2 - robot.getY(), car.getSize(), car.getSize());
                    }
                }
            }
        }
        if (robot != null) {
            canvas.getGraphicsContext2D().setFill(Color.RED);
            canvas.getGraphicsContext2D().fillOval(robot.getX(), canvas.getHeight() / 2, robot.getSize(), robot.getSize());
        }
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RoadView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public CarRobot getRobot() {
        return robot;
    }

    public void setRobot(CarRobot robot) {
        this.robot = robot;
    }
}
