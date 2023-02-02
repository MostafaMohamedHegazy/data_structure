package MainPackage;

import Controllers.FileController;

import java.util.ArrayList;

public class XMLParser {

    public static ArrayList<String> Parse(){
        String rowString = FileController.xmlText;
        StringBuilder s = new StringBuilder();
        ArrayList<String> parsed = new ArrayList<String>();
        for (int i = 0; i < rowString.length(); i++){
            if (rowString.charAt(i) != '\n') {
//                if (rowString.charAt(i) == '<'){
//                    parsed.add(s.toString());
//                    s = new StringBuilder();
//                    s.append(rowString.charAt(i));
//                }
//                else if (rowString.charAt(i) == '>') {
//                    s.append(rowString.charAt(i));
//                    parsed.add(s.toString());
//                    s = new StringBuilder();
//                }
                s.append(rowString.charAt(i));
            }
            else {
                parsed.add(s.toString());
                s = new StringBuilder();
            }
        }
//        for (int i = 0; i < parsed.size(); i++) {
//            boolean isWhite = true;
//            for (int j = 0; j < parsed.get(i).length(); j++) {
//                if(parsed.get(i).charAt(j) != ' ') {
//                    if (parsed.get(i).charAt(j) == '\t')
//                        continue;
//                    isWhite = false;
//                    break;
//                }
//            }
//            if (isWhite)
//                parsed.remove(i--);
//        }
        return parsed;
    }

}
