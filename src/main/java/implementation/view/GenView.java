package implementation.view;

import genetic.Gen;
import implementation.robot.CarGen;
import implementation.robot.CarRobot;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class GenView extends AnchorPane {

    @FXML
    private Canvas canvas;

    public GenView() {
        loadFxml();
    }

    public void draw(CarRobot robot) {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (robot != null) {
            for (Gen gen : robot.getGenes()) {
                int[] code = ((CarGen) gen).getCode();
                int c = code[3] * 40;
                int s = (int) (Math.abs(code[1] - 3) * 6.7);
                int shift = (20 - s) / 2;
                int x = code[0] * 140 + code[1] * 20 + shift;
                int y = code[2] * 100 + code[3] * 20 + shift;
                canvas.getGraphicsContext2D().setFill(Color.rgb(255, c, c));
                canvas.getGraphicsContext2D().fillRect(x, y, s, s);
            }

            canvas.getGraphicsContext2D().setStroke(Color.BLACK);
            canvas.getGraphicsContext2D().setLineWidth(1);
            for (int i = 0; i < 15; i++) {
                canvas.getGraphicsContext2D().strokeLine(i * 20, 0, i * 20, 400);
            }
            for (int i = 0; i < 21; i++) {
                canvas.getGraphicsContext2D().strokeLine(0, i * 20, 280, i * 20);
            }
            canvas.getGraphicsContext2D().setLineWidth(3);
            for (int i = 0; i < 3; i++) {
                canvas.getGraphicsContext2D().strokeLine(i * 140, 0, i * 140, 400);
            }
            for (int i = 0; i < 5; i++) {
                canvas.getGraphicsContext2D().strokeLine(0, i * 100, 280, i * 100);
            }
        }
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GenView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
