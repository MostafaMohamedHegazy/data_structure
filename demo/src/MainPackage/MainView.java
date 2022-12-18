package MainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class MainView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource("./main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setTitle("XML Editor");
        stage.setScene(scene);
        stage.setMinHeight(450);
        stage.setMinWidth(610);
        stage.getIcons().add(new Image("./MainPackage/XML Logo.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}