package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FileController implements Initializable {
    FileChooser f = new FileChooser();
    public static String xmlText;

    @FXML
    private TextArea textArea1;

    @FXML
    private TextArea textArea2;

    @FXML
    public void fileChooser(ActionEvent e){
        textArea1.clear();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
        File file = f.showOpenDialog(new Stage());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
                textArea1.appendText(scanner.nextLine()+"\n");
            xmlText = textArea1.getText();
            textArea2.setText(xmlText);
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    public void fileSaver(ActionEvent e){
        File file = f.showSaveDialog(new Stage());
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*"));
        if (file != null)
        {
            saveSystem(file, textArea2.getText());
        }
    }

    public void saveSystem(File file, String content){
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        f.setInitialDirectory(new File("../."));
    }
}
