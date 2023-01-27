package implementation.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import implementation.controller.Controller;
import implementation.view.MainView;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) {
        MainView mainView = new MainView();
        stage.setScene(new Scene(mainView));
        stage.setTitle("GeneticAlgorithm");
        stage.show();

        Controller controller = new Controller(mainView);
        controller.step();
    }
}
