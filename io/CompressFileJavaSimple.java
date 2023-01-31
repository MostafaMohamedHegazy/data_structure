package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.DeflaterOutputStream;

public class CompressFileJavaSimple{

    public static void main (String[] args){
        String fileName="sample";
        String fileExtension=".xml";
        System.out.println(CompressFile(fileName,fileExtension));
      
    }
    
    public static boolean CompressFile(String file,String extension){
        String filepath=file+extension;
        String compFilepath=file+"compressed"+extension;
        try{
            FileInputStream fileRead=new FileInputStream(filepath);
            FileOutputStream fileWrite=new FileOutputStream(compFilepath);
            try (FileInputStream fileReaded = new FileInputStream(compFilepath)) {
                DeflaterOutputStream comp = new DeflaterOutputStream(fileWrite);
                int data;
                while ((data=fileRead.read())!=-1){
                    comp.write(data);
                
                }
                int i;
                while((i=fileReaded.read())!=-1){
                    System.out.println((char)i);
                }
                fileRead.close();
                comp.close();
            }

            long originalSize=new File(filepath).length();
            long compSize=new File(compFilepath).length();
            System.out.println("original file size:"+originalSize+ " and the new file size:"+compSize);
            
            if (compSize<originalSize){
                return true;
            }
            else{
                File fileToDelete=new File(compFilepath);
                fileToDelete.delete();
                System.out.println("can't compress he file");
                return false;
            }

        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
}