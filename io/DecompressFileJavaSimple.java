import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class DecompressFileJavaSimple{

    public static void main (String[] args){
        String fileName="samplecompressed";
        String fileExtension=".xml";
        System.out.println(DecompressFile(fileName,fileExtension));
    }
    public static boolean DecompressFile(String file,String extension){
        String filepath=file+extension;
        String decompFilepath=file+"decompressed"+extension;
        try{
            FileInputStream fileRead=new FileInputStream(filepath);
            FileOutputStream fileWrite=new FileOutputStream(decompFilepath);
            try (FileInputStream fileReaded = new FileInputStream(decompFilepath)) {
                InflaterOutputStream decomp = new InflaterOutputStream(fileWrite);
                int data;
                while ((data=fileRead.read())!=-1){
                    decomp.write(data);
                }
                int i;
                while((i=fileReaded.read())!=-1){
                    System.out.println((char)i);

                }
                fileRead.close();
                decomp.close();
            }

            long originalSize=new File(filepath).length();
            long decompSize=new File(decompFilepath).length();
            System.out.println("original file size:"+originalSize+"and the new file size:"+decompSize);
            if (decompSize > originalSize){
                return true;
            }
            else{
                File fileToDelete=new File(decompFilepath);
                fileToDelete.delete();
                System.out.println("can't decompress he file");
                return false;
            }

        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
}