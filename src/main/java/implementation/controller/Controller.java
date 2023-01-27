package implementation.controller;

import genetic.Entity;
import genetic.Evolution;
import genetic.GenPool;
import implementation.main.Constants;
import implementation.model.Road;
import implementation.robot.CarGen;
import implementation.robot.CarRobot;
import implementation.robot.CarWorld;
import implementation.view.MainView;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private Timer timer;
    private MainView mainView;
    private CarWorld carWorld;
    private List<Entity> robots;
    private List<Road> roads;
    private int generation;

    public Controller(MainView mainView) {
        this.mainView = mainView;
        initKeyboard();
        initGeneticGame();
    }

    private void initGeneticGame() {
        roads = generateRoads();
        GenPool genPool = generateGenPool();
        carWorld = new CarWorld(roads);
        robots = generateRobots(genPool);

        mainView.getRoadView().setRoad(roads.get(0));
        mainView.getRoadView().setRobot((CarRobot) robots.get(0));
        mainView.getRoadView().draw();
    }

    public void step() {
        Evolution.runCycles(carWorld, robots, Constants.SKIP_CYCLES);
        play((CarRobot) robots.get(0), roads.get(0));
        mainView.getGenView().draw(((CarRobot) robots.get(0)));
        generation += Constants.SKIP_CYCLES;
        renameStage();
    }

    private void renameStage() {
        Stage stage = (Stage) mainView.getScene().getWindow();
        String title = stage.getTitle().split(" ")[0];
        stage.setTitle(title + " generation " + generation);
    }

    private List<Road> generateRoads() {
        roads = new ArrayList<>();
        for (int i = 0; i < Constants.ROAD_COUNT; i++) {
            roads.add(new Road(Constants.STRIPS_COUNT, Constants.ROAD_LENGTH, Constants.STRIPS_WIDTH,
                    Constants.CAR_SIZE, Constants.ROAD_MIN_SPEED, Constants.ROAD_MAX_SPEED,
                    Constants.CAR_MIN_COUNT, Constants.CAR_MAX_COUNT));
        }
        return roads;
    }

    private List<Entity> generateRobots(GenPool genPool) {
        List<Entity> robots = new ArrayList<>();
        for (int i = 0; i < Constants.ROBOT_SELECTION_SIZE; i++) {
            robots.add(new CarRobot(genPool, Constants.ROBOT_GEN_COUNT,
                    Constants.ROBOT_MUTATION_RATIO,
                    Constants.ROBOT_MUTATION_PROBABILITY));
        }
        return robots;
    }

    private void play(CarRobot robot, Road road) {
        robot.reset();
        road.resetRobotX(robot);
        int delay = Constants.DELTA_T;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                boolean status = carWorld.calcChanges(delay, robot, road);
                Platform.runLater(() -> {
                    mainView.getRoadView().setRoad(road);
                    mainView.getRoadView().setRobot(robot);
                    mainView.getRoadView().draw();
                });
                if (!status) {
                    Platform.runLater(() -> {
                        timer.purge();
                        timer.cancel();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int index = roads.indexOf(road) + 1;
                        if (index < roads.size()) {
                            play(robot, roads.get(index));
                        } else {
                            step();
                        }
                    });
                }
            }
        }, 0, delay);
    }

    private void initKeyboard() {
        mainView.getRoadView().getScene().setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                step();
            }
        });
    }

    private GenPool generateGenPool() {
        return new GenPool() {
            @Override
            protected void generateGenes() {
                for (int a = 0; a < 2; a++) {
                    for (int w = 0; w < 7; w++) {
                        for (int s = 0; s < 4; s++) {
                            for (int st = 0; st < 5; st++) {
                                getGenes().add(new CarGen(a, w, s, st));
                            }
                        }
                    }
                }
            }
        };
    }

}
