package simple.course;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Application.class.getResource("mainPage.fxml"));
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(new Scene (root, 900, 510));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}