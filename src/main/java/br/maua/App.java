package br.maua;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/br/maua/view/main.fxml"));
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Jornada Mauá");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
