package ge.biterium.chat.voice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    public static boolean calling = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image favicon = new Image("file:resources/images/favicon.png");
        Parent root = FXMLLoader.load(getClass().getResource("view/callView.fxml"));
        primaryStage.setTitle(" ");
        primaryStage.getIcons().add(favicon);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
