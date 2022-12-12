package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ChooseFileController implements Initializable {
    FileChooser fc = new FileChooser();
    public static String xmlText;

    @FXML
    private TextArea textArea;

    @FXML
    public void fileChooser(ActionEvent e){
        textArea.clear();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML File", "*.xml"),
        new FileChooser.ExtensionFilter("Text File", "*.txt"));
        File file = fc.showOpenDialog(new Stage());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
                textArea.appendText(scanner.nextLine()+"\n");
            xmlText = textArea.getText();
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fc.setInitialDirectory(new File("../."));
    }
}
