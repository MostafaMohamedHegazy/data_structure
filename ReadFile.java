import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFile {

    public static ArrayList<String> returna_array(){
        ArrayList<String> b = new ArrayList<>();
        try {
            File myObj = new File("C:\\Users\\HP\\OneDrive\\Documents\\GitHub\\data_structure\\out\\original sample");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //System.out.println(data);
                b.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return b;
    }
    public static void main(String[] args) {}
}
