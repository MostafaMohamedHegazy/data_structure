package Controllers;

import MainPackage.XML;
import MainPackage.XMLParser;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FileController implements Initializable {
    FileChooser f = new FileChooser();

    public static String xmlText;
    
    public ArrayList<String> ss;

    @FXML
    private TextArea inputText;

    @FXML
    private TextArea outputText;

    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private Button b3;

    @FXML
    private Button b4;

    @FXML
    private Button b5;

    @FXML
    private Button b6;

    @FXML
    private Button b7;


    @FXML
    public void fileChooser(ActionEvent e){
        inputText.clear();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
        File file = f.showOpenDialog(new Stage());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
                inputText.appendText(scanner.nextLine()+ "\n");
            b6.setStyle("-fx-background-color: #dda100");

        } catch (FileNotFoundException ex){
            inputText.setText("");
        } catch (Exception exx){}
        xmlText = inputText.getText();
        ss = XMLParser.Parse();

    }

    @FXML
    public void fileSaver(ActionEvent e){
        File file = f.showSaveDialog(new Stage());
        f.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("File", "*"));
        if (file != null)
        {
            saveSystem(file, outputText.getText());
            b7.setStyle("-fx-background-color: #dda100");
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

    @FXML
    public void corrector(ActionEvent e) {
        outputText.clear();
        if (ss.size() != 1){
            ArrayList<String> a = XML.Correct(ss);
            StringBuilder sb = new StringBuilder("");

            for (String s : a)
                sb.append(s + "\n");
            outputText.setText(sb.toString());
            outputText.appendText("------------------------------------------------------------\n");

            StringBuilder sb2 = new StringBuilder("");
            for (String s : ss)
                sb2.append(s + "\n");
            outputText.appendText(sb2.toString());
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ss.get(0).length(); i++) {
                if (ss.get(0).charAt(i)!='<')
                    sb.append(ss.get(0).charAt(i));
                else {
                    ss.add(sb.toString());
                    sb.delete(0, sb.length());
                    sb.append('<');
                }
            }
            ss.remove(0);
            ss.remove(0);
            ArrayList<String> a = XML.Correct(ss);
            a.remove(a.size() - 1);
            sb.delete(0, sb.length());

            for (String s : a)
                sb.append(s + "\n");
            outputText.setText(sb.toString());
            outputText.appendText("------------------------------------------------------------\n");

            StringBuilder sb2 = new StringBuilder("");
            for (String s : ss)
                sb2.append(s + "\n");
            outputText.appendText(sb2.toString());
        }
        b1.setStyle("-fx-background-color: #dda100");
    }

    @FXML
    public void printer(ActionEvent e) {
        outputText.clear();
        outputText.appendText("");
        for (String s: ss)
            outputText.appendText(s+"\n");
    }

    @FXML
    void toJsonConverter(ActionEvent e) {
        ArrayList<String> mostafa = XML.Convert2JSON(ss);
        StringBuilder sb = new StringBuilder("");

        for (String s : mostafa)
            sb.append(s);
        outputText.setText(sb.toString());
        b3.setStyle("-fx-background-color: #dda100");
    }

    @FXML
    void prettify(ActionEvent e) {
        ArrayList<String> omar = XML.Prettify(ss);
        StringBuilder sb = new StringBuilder();
        for (String s : omar)
            sb.append(s + "\n");
        outputText.setText(sb.toString());
        b3.setStyle("-fx-background-color: #dda100");
    }

    @FXML
    void minify(ActionEvent e) {
        ArrayList<String> ahmed = XML.Minify(ss);
        StringBuilder sb = new StringBuilder(ahmed.get(0));
        outputText.setText(sb.toString());
        b3.setStyle("-fx-background-color: #dda100");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        f.setInitialDirectory(new File("../."));
    }
}
