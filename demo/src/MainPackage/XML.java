package MainPackage;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class XML {

    public static ArrayList<String> Correct(ArrayList<String> XML) {
        int lineIndex = 0;
        String line;
        Stack<Tag> tags = new Stack<Tag>();
        ArrayList<String> mistakes = new ArrayList<String>();

        while (lineIndex < XML.size()) {
            line = XML.get(lineIndex);
            int charIndex = 0;

            while (charIndex < line.length()) {
                if (line.charAt(charIndex) == '<' && line.charAt(charIndex + 1) != '?'
                        && line.charAt(charIndex + 1) != '!') {
                    String closingTagLabel = new String();

                    if (line.charAt(charIndex + 1) == '/') {
                        charIndex += 2;

                        while (line.charAt(charIndex) != '>') {
                            closingTagLabel += line.charAt(charIndex);
                            charIndex++;
                        }

                        if (!tags.empty()) {
                            if (closingTagLabel.equals((tags.peek()).label)) {
                                tags.pop();
                            } else {

                                Boolean noOpeningTagF = true;
                                Boolean noClosingTagF = false;
                                Boolean mismatchingTagsF = false;
                                Stack<Tag> tmpTags = new Stack<Tag>();
                                tmpTags.addAll(tags);
                                Queue<Tag> noClosingTags = new LinkedList<Tag>();

                                while (!tmpTags.empty()) {
                                    if (closingTagLabel.equals(tmpTags.peek().label)) {
                                        noClosingTagF = true;
                                        break;
                                    }

                                    noClosingTags.add(tmpTags.pop());
                                }

                                if (tags.peek().line == lineIndex) {
                                    mismatchingTagsF = true;
                                    noOpeningTagF = false;
                                    noClosingTagF = false;
                                }

                                if (mismatchingTagsF == true) {
                                    int i = 1;
                                    while (line.charAt(i - 1) != '<') {
                                        i++;
                                    }
                                    int j = i;
                                    while (line.charAt(j) != '>') {
                                        j++;
                                    }
                                    String mismatchingTagLabel = line.substring(i, j);
                                    int k = j;
                                    while (line.charAt(k) != '<') {
                                        k++;
                                    }
                                    XML.set(lineIndex, line.substring(0, k) + "</" + mismatchingTagLabel + ">");
                                    mistakes.add("Mismatching tags for <" + (tags.peek().label) + ">" + " in line "
                                            + (tags.peek().line + 1));
                                    tags.pop();
                                } else if (noClosingTagF == true) {
                                    noOpeningTagF = false;
                                    while (!noClosingTags.isEmpty()) {
                                        Tag noClosingTag = noClosingTags.remove();
                                        String closingTag = "</" + noClosingTag.label + ">";
                                        XML.set(noClosingTag.line, XML.get(noClosingTag.line) + closingTag);
                                        mistakes.add("No closing tag for <" + noClosingTag.label + "> in line "
                                                + (noClosingTag.line + 1));
                                        tags.pop();
                                    }
                                    tags.pop();
                                } else if (noOpeningTagF == true) {
                                    String myLine = XML.get(lineIndex);
                                    int myLineMargin = 0;

                                    while (myLine.charAt(myLineMargin) == ' ') {
                                        myLineMargin++;
                                    }

                                    String margin = myLine.substring(0, myLineMargin);
                                    String restOfLine = myLine.substring(myLineMargin, myLine.length());
                                    myLine = margin + "<" + closingTagLabel + ">" + restOfLine;
                                    XML.set(lineIndex, myLine);
                                    mistakes.add(
                                            "No opening tag for </" + closingTagLabel + "> in line " + (lineIndex + 1));
                                }

                            }
                        } else {
                            String myLine = XML.get(0);
                            int myLineMargin = 0;

                            while (myLine.charAt(myLineMargin) == ' ') {
                                myLineMargin++;
                            }

                            String margin = myLine.substring(0, myLineMargin);
                            String restOfLine = myLine.substring(myLineMargin, myLine.length());
                            myLine = margin + "<" + closingTagLabel + ">" + restOfLine;
                            XML.set(0, myLine);
                            mistakes.add("No opening tag for </" + closingTagLabel + "> in line " + (lineIndex + 1));
                        }
                    } else {

                        charIndex++;
                        while (line.charAt(charIndex) != '>') {
                            closingTagLabel += line.charAt(charIndex);
                            charIndex++;
                        }
                        tags.push(new Tag(closingTagLabel, lineIndex, charIndex - closingTagLabel.length() - 1));
                    }
                } else {
                    charIndex++;
                }
            }
            lineIndex++;
        }

        while (!tags.empty()) {
            Tag noClosingTag = tags.pop();
            String correctLine = " ".repeat(noClosingTag.margin) + "</" + noClosingTag.label + ">";
            XML.add(correctLine);
            mistakes.add("No closing tag for <" + noClosingTag.label + "> " + "in line " + (noClosingTag.line + 1));
        }
        return mistakes;

    }

    public static ArrayList<String> Prettify(ArrayList<String> generalXML) {
        ArrayList<String> correctIndentsXML = new ArrayList<String>();

        String line;
        int indentsCounter;

        for (int x = 0; x<	generalXML.size();x++) {
            correctIndentsXML.add("x");
        }

        for (int lineIndex = 0; lineIndex < generalXML.size(); lineIndex++) {
            generalXML.set(lineIndex ,generalXML.get(lineIndex).replaceAll("\\s", ""));
        }

        indentsCounter=0;

        for (int lineIndex = 0; lineIndex < generalXML.size(); lineIndex++) {
            line = generalXML.get(lineIndex);			//get line content

            correctIndentsXML.set(lineIndex, "\t".repeat(indentsCounter)+generalXML.get(lineIndex));
            int repeatNumber=0;

            for ( int i = 0; i < line.length()-1; i++) {
                if(line.charAt(i)=='<') {
                    repeatNumber++;
                }
            }
            if (line.contains("/") && repeatNumber <2  ) {
                correctIndentsXML.set(lineIndex, "\t".repeat(indentsCounter-1)+generalXML.get(lineIndex));
            }
            for (int charIndex_1 = 0; charIndex_1 < line.length(); charIndex_1++) { //looping on the line char by char looking for an opening tag

                if(line.charAt(charIndex_1)=='<'&& line.charAt(charIndex_1+1)!='/' ) { // if line has opening tag then;
                    indentsCounter++;
                }

                else if (line.charAt(charIndex_1)=='<' && line.charAt(charIndex_1+1)=='/') {
                    indentsCounter--;
                }
            }
        }
        return correctIndentsXML;
    }

    public static ArrayList<String>  Convert2JSON( ArrayList<String> a){
        Stack<Tag> tag_s = new Stack<>();
        Stack<Tag> temp_stack= new Stack<>();
        //Stack<Tag> is_arr_opentag= new Stack<>();
        ArrayList<Tag> tag_list = new ArrayList<>();
        ArrayList<Tag> openingtag_list = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        boolean openingtag=false , closingtag =false , bothtags=false;
        int j=1;
        int qcount=0;
        for(int i=1 ; i<a.size()+1;i++)
        {
            for( j=1 ; j<a.get(i-1).length()+1;j++)
            {
                if (a.get(i-1).charAt(j-1) == '<' && a.get(i-1).charAt(j) != '/')
                {
                    if(a.get(i-1).contains("</"))
                    {
                        bothtags=true; openingtag=false; closingtag =false ;
                        //System.out.println("this is both tags line");
                        //System.out.println(""+(i-1)+" "+a.get((i-1)));
                        tag_list.add(new Tag(a.get(i-1),(i-1),j-1,"both tags"));
                        break;

                    }
                    else
                    {
                        openingtag=true;
                        closingtag =false ;
                        bothtags=false;

                        //System.out.println("this is opening tag line");
                        //System.out.println(""+(i-1)+" "+a.get(i-1));
                        qcount++;
                        //System.out.println(" qcount: "+qcount);
                        tag_list.add(new Tag(a.get(i-1),(i-1),j-1,"opening tag"));
                        openingtag_list.add(new Tag(a.get(i-1),(i-1),j-1,"opening tag"));
                        qcount++;
                        //System.out.println(" break at: "+a.get(i).charAt(j-1)+" "+a.get(i).charAt(j));
                        break;
                    }
                }
            }
            if(a.get((i-1)).contains("</")&& (!openingtag && !bothtags))
            {
                int k;
                for(  k=1 ; k<a.get((i-1)).length(); k++)
                {
                    if (a.get((i-1)).charAt(k-1) == '<' && a.get((i-1)).charAt(k) == '/'){ break; }
                }
                closingtag=true;
                openingtag=false;
                bothtags =false ;
                tag_list.add(new Tag(a.get((i-1)),(i-1),k-1,"closing tag"));
                //tag_q.add(new Tag(a.get((i-1)),(i-1),k-1,"closing tag"));
                //System.out.println("this is closing tag line");
                //System.out.println(""+(i-1)+" "+a.get((i-1)));
            }
            if((!closingtag && !openingtag && !bothtags))
            {
                closingtag =false;
                openingtag=false;
                bothtags =false ;
                tag_list.add(new Tag(a.get((i-1)),(i-1),0,"data"));
                //tag_q.add(new Tag(a.get((i-1)),(i-1),0,"data"));
                //System.out.println("this is data line");
                //System.out.println(""+(i-1)+" "+a.get((i-1)));

            }
            openingtag=false ; closingtag =false ; bothtags=false;
        }
        for(int m=0;m< tag_list.size();m++)
        {

            tag_list.get(m).get_name();

        }
        for(int m=0;m< tag_list.size();m++)
        {
            //System.out.println(tag_list.get(m).label+" is array: ? "+tag_list.get(m).isArray(tag_list));
            //System.out.println(tag_list.get(m).label+" is object: ? "+tag_list.get(m).isObject(tag_list));
            tag_list.get(m).isArray(tag_list);
            tag_list.get(m).isObject(tag_list, m);
        }
        /*for(int m=0;m< tag_list.size();m++)
        {
            System.out.println(tag_list.get(m).label+"      margin:? "+tag_list.get(m).line);
        }*/

        //System.out.println("**********************************************************************hiiiiiiii");

        for(int m=0;m< tag_list.size();m++)
        {
            // System.out.println(tag_list.get(m).label+"      is array:? "+tag_list.get(m).is_array);
            //System.out.println(tag_list.get(m).label+"      is  array elem:? "+tag_list.get(m).is_Array_element);
            //System.out.println(tag_list.get(m).name+"      "+tag_list.get(m).tag_type);
        }

        //System.out.println("**********************************************************************hiiiiiiii");
        int num=0;
        int line=0;
        String stemp="";
        b.add("{\n");
        Tag T;
        String compare_margins="";
        int arr_element_count=0;
        for(int m=0;m< tag_list.size();m++)
        {
            if(tag_list.get(m).tag_type == "opening tag")
            {
                temp_stack.push(tag_list.get(m));
                T=tag_list.get(m);
                tag_s.push(tag_list.get(m))  ;
                String st0="\t";
                for(int t=0; t<tag_list.get(m).label.length();t++)
                {
                    if(tag_list.get(m).label.charAt(t)=='<')
                    {
                        stemp="\t";
                        st0=st0+"\"";

                    }
                    else if(tag_list.get(m).label.charAt(t)=='>')
                    {

                        if(tag_list.get(m).is_json_Object && ! tag_list.get(m).is_Array_element )
                        {

                            st0=st0+"\":{";
                            line=m;
                            num=tag_list.get(m).margin;
                        }
                        else if(tag_list.get(m).is_json_Object && tag_list.get(m).is_Array_element )
                        {


                            if(tag_list.get(m).is_1stArray_element)
                            {
                                arr_element_count++;
                                st0=st0+"\":[ {";
                            }
                            else if(!tag_list.get(m).is_1stArray_element)
                            {
                                arr_element_count++;
                                st0=stemp+"{";
                            }
                        }
                        else if(!tag_list.get(m).is_json_Object && !tag_list.get(m).is_Array_element )
                        {
                            st0=st0+"\": ";
                        }
                        else if(!tag_list.get(m).is_json_Object && tag_list.get(m).is_Array_element )
                        {

                            if(tag_list.get(m).is_1stArray_element)
                            {
                                arr_element_count++;
                                st0=st0+"\":[ ";
                            }
                            else if(!tag_list.get(m).is_1stArray_element)
                            {
                                st0="";
                            }

                        }

                        break;
                    }
                    else
                    {
                        if(!(tag_list.get(m).is_Array_element && !tag_list.get(m).is_1stArray_element))
                        {
                            stemp=stemp+"\t";
                            st0 = st0 + tag_list.get(m).label.charAt(t);
                        }
                        //else{st0 = st0 +"";}
                    }
                }
                //System.out.println(st0);
                b.add(st0+"\n");
            }

            if(tag_list.get(m).tag_type=="closing tag")
            {
                Tag poped_tag ,poped_tag2;
                Tag aux_tag=tag_list.get(m);
                poped_tag=tag_s.pop();
                poped_tag2=temp_stack.pop();

                String st0="\t";
                int count=0;
                for(int t=0;t<tag_list.get(m).label.length();t++)
                {
                    if(tag_list.get(m).label.charAt(t)=='<')
                    {
                        if( poped_tag2.is_array )
                        {
                            st0=st0+"] }";
                            arr_element_count=0;
                            num=0;
                        }
                        else
                        {
                            if(poped_tag2.is_json_Object )
                            {
                                st0=st0+"}";

                            }
                            if(!poped_tag2.is_json_Object  )
                            {
                                st0=st0+"";
                            }
                        }

                        if(m<tag_list.size()-1)
                        {
                            if (!tag_list.get(m + 1).tag_type.equals("closing tag"))
                            {
                                st0 = st0 + ",";
                            }
                        }
                        break;
                    }
                    st0=st0 + " ";
                }
                //System.out.println(st0);
                b.add(st0+"\n");
            }

            if(tag_list.get(m).tag_type=="both tags")
            {

                String st0="\t";
                int count=0;
                for(int t=0; t<tag_list.get(m).label.length();t++)
                {
                    if(tag_list.get(m).label.charAt(t)=='<')
                    {
                        count++;
                        if(count<=2){ st0=st0+"\""; }

                        else
                        {
                            st0=st0+"\"";
                            if (!tag_list.get(m + 1).tag_type.equals("closing tag")) {
                                st0 = st0 + ",";
                            }
                            break;
                        }
                    }
                    else if(tag_list.get(m).label.charAt(t)=='>')
                    {
                        count++;
                        if(count<=2){ st0=st0+"\":\""; }
                        else break;
                    }
                    else
                    {
                        st0=st0+tag_list.get(m).label.charAt(t);
                    }
                }
                //System.out.println(st0);
                b.add(st0+"\n");
            }

            if(tag_list.get(m).tag_type=="data") {
                String st0 = "\t";
                int count = 0;
                for (int t = 0; t < tag_list.get(m).label.length(); t++) {
                    if (tag_list.get(m).label.charAt(t) >= '!') {
                        count++;
                        if (count <= 1) {
                            st0 = st0 + "\"";
                        }
                    }
                    st0 = st0 + tag_list.get(m).label.charAt(t);

                }
                st0 = st0 + "\"";
                //System.out.println(st0);
                b.add(st0 + "\n");
            }
        }
        b.add("}\n");
        //System.out.println(tag_list.get(4).name  + " type : " + tag_list.get(4).tag_type );
        return b;


    }

    public static boolean CompressFile(String file,String extension, StringBuilder info){
        String filepath=file+extension;
        String compFilepath=file+"compressed"+extension;
        try{
            FileInputStream fileRead=new FileInputStream(filepath);
            FileOutputStream fileWrite=new FileOutputStream(compFilepath);

            DeflaterOutputStream comp = new DeflaterOutputStream(fileWrite);
            int data;
            while ((data=fileRead.read())!=-1){
                comp.write(data);
            }
            fileRead.close();
            comp.close();
            long originalSize=new File(filepath).length();
            long compSize=new File(compFilepath).length();
            info.append("Original file size: "+originalSize+"\nThe new file size: "+compSize
                    +"\nThe new file is inside the same folder with the XML you chosen");
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

    public static boolean DecompressFile(String file,String extension, StringBuilder[] endFile){
        String filepath=file+extension;
        String decompFilepath = file+"decompressed"+extension;
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
                    endFile[0].append((char)i);
                }
                fileRead.close();
                decomp.close();
            }

            long originalSize=new File(filepath).length();
            long decompSize=new File(decompFilepath).length();
            endFile[1].append("Original file size: "+originalSize+" \nThe new file size: " + decompSize
                    + "\nThe new file is inside the same folder with the XML you chosen");
            if (decompSize > originalSize){
                return true;
            }
            else{
                File fileToDelete=new File(decompFilepath);
                fileToDelete.delete();
                endFile[1].append("can't decompress he file");
                return false;
            }
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public static ArrayList<String> Minify(ArrayList<String> XML){

        ArrayList<String> minified = new ArrayList<String>();
        minified.add("");

        for (int lineIndex = 0; lineIndex < XML.size(); lineIndex++) {
            for (int charIndex = 0; charIndex < XML.get(lineIndex).length(); charIndex++) {
                if(XML.get(lineIndex).charAt(charIndex)=='\n' || XML.get(lineIndex).charAt(charIndex)==' ')
                {
                    continue;
                }
                minified.set(0,minified.get(0) + XML.get(lineIndex).charAt(charIndex));
            }

        }
        return minified;
    }

}