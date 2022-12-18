package MainPackage;

import Controllers.FileController;

import java.util.ArrayList;

public class XMLParser {
    private static final String rowString = FileController.xmlText;

    public static ArrayList<String> Parse(){
        StringBuilder s = new StringBuilder("");
        ArrayList<String> parsed = new ArrayList<String>();
        for (int i = 0; i < rowString.length(); i++){
            if (rowString.charAt(i) != '\n')
                s.append(rowString.charAt(i));
            else {
                parsed.add(s.toString());
                s = new StringBuilder("");
            }
        }
        return parsed;
    }

}
