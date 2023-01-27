package implementation.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainView extends AnchorPane {

    @FXML
    private StackPane roadPane, genPane;
    private RoadView roadView;
    private GenView genView;

    public MainView() {
        loadFxml();
        initView();
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        roadView = new RoadView();
        roadPane.getChildren().add(roadView);
        genView = new GenView();
        genPane.getChildren().add(genView);
    }

    public RoadView getRoadView() {
        return roadView;
    }

    public GenView getGenView() {
        return genView;
    }
}
