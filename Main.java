import java.util.*;

public class Main {

    static ArrayList<String>  Convert2JSON( ArrayList<String> a){
        Stack<Tag> tag_s = new Stack<>();
        Stack<Tag> temp_stack= new Stack<>();
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
            //System.out.println(tag_list.get(m).label+" is array: ? "+tag_list.get(m).isArray(tag_list));
            //System.out.println(tag_list.get(m).label+" is object: ? "+tag_list.get(m).isObject(tag_list));
            tag_list.get(m).isArray(tag_list);
            tag_list.get(m).isObject(tag_list);
        }
        /*for(int m=0;m< tag_list.size();m++)
        {
            System.out.println(tag_list.get(m).label+"      margin:? "+tag_list.get(m).line);
        }*/

        //System.out.println("**********************************************************************hiiiiiiii");

        /*for(int m=0;m< tag_list.size();m++)
        {
            System.out.println(tag_list.get(m).label+"      is array:? "+tag_list.get(m).is_array);
        }*/

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

                if(!tag_s.isEmpty())
                {
                    if (tag_s.peek().margin > tag_list.get(m).margin) {
                        compare_margins = "lower";
                    }
                    if (tag_s.peek().margin < tag_list.get(m).margin) {
                        compare_margins = "higher";
                    }
                    if (tag_s.peek().margin == tag_list.get(m).margin) {
                        compare_margins = "equal";
                    }
                }
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
                            if (compare_margins == "equal" || tag_list.get(m + 1).margin==aux_tag.margin)
                            {
                                st0 = st0 + ",";
                            }
                        }
                        break;
                    }
                    st0=st0+" ";
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
                            if (compare_margins == "equal" || tag_list.get(m + 1).margin==tag_list.get(m).margin) {
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
        return b;
    }


    public static void main(String[] args) {

        ArrayList<String> s = new ArrayList<>();
        s.add( "<users>");
        s.add( "    <user>");
        s.add( "        <id>1</id>");
        s.add( "        <name>Ahmed Ali</name>");
        s.add( "        <posts>");
        s.add( "            <post>");
        s.add( "                <body>");
        s.add( "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        s.add( "                </body>");
        s.add( "                <topics>");
        s.add( "                    <topic>");
        s.add( "                        economy");
        s.add( "                    </topic>");
        s.add( "                    <topic>");
        s.add( "                        finance");
        s.add( "                    </topic>");
        s.add( "                </topics>");
        s.add( "            </post>");
        s.add( "            <post>");
        s.add( "                <body>");
        s.add( "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        s.add( "                </body>");
        s.add( "                <topics>");
        s.add( "                    <topic>");
        s.add( "                        solar energy");
        s.add( "                    </topic>");
        s.add( "                </topics>");
        s.add( "            </post>");
        s.add( "        </posts>");
        s.add( "        <followers>");
        s.add( "            <follower>");
        s.add( "                <id>2</id>");
        s.add( "            </follower>");
        s.add( "            <follower>");
        s.add( "                <id>3</id>");
        s.add( "            </follower>");
        s.add( "        </followers>");
        s.add( "    </user>");

        s.add( "    <user>");
        s.add( "        <id>2</id>");
        s.add( "        <name>Yasser Ahmed</name>");
        s.add( "        <posts>");
        s.add( "            <post>");
        s.add( "                <body>");
        s.add( "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        s.add( "                </body>");
        s.add( "                <topics>");
        s.add( "                    <topic>");
        s.add( "                        education");
        s.add( "                    </topic>");
        s.add( "                </topics>");
        s.add( "            </post>");
        s.add( "        </posts>");
        s.add( "        <followers>");
        s.add( "            <follower>");
        s.add( "                <id>1</id>");
        s.add( "            </follower>");
        s.add( "        </followers>");
        s.add( "    </user>");

        s.add( "    <user>");
        s.add( "        <id>3</id>");
        s.add( "        <name>Mohamed Sherif</name>");
        s.add( "        <posts>");
        s.add( "            <post>");
        s.add( "                <body>");
        s.add( "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        s.add( "                </body>");
        s.add( "                <topics>");
        s.add( "                    <topic>");
        s.add( "                        sports");
        s.add( "                    </topic>");
        s.add( "                </topics>");
        s.add( "            </post>");
        s.add( "        </posts>");
        s.add( "        <followers>");
        s.add( "            <follower>");
        s.add( "                <id>1</id>");
        s.add( "            </follower>");
        s.add( "        </followers>");
        s.add( "    </user>");
        s.add( "</users>");

        /*s.add("<bookstore>");
        s.add("  <book>");
        s.add("    <title >Everyday Italian</title>");
        s.add("    <author>Giada De Laurentiis</author>");
        s.add("    <year>2005</year>");
        s.add("    <price>30.00</price>");
        s.add("  </book>");
        s.add("  <book>");
        s.add("    <title >Harry Potter</title>");
        s.add("    <author>J K. Rowling</author>");
        s.add("    <year>2005</year>");
        s.add("    <price>29.99</price>");
        s.add("  </book>");
        s.add("  <book>");
        s.add("    <title >Learning XML</title>");
        s.add("    <author>Erik T. Ray</author>");
        s.add("    <year>2003</year>");
        s.add("    <price>39.95</price>");
        s.add("  </book>");
        s.add("</bookstore>");*/

        ArrayList<String> st = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();

        b=ReadFile.returna_array();
        st=Convert2JSON(b);

        for (int i = 0; i < st.size(); i++)
        {
            System.out.print(st.get(i));
        }

        //System.out.println(b);
        //System.out.println(s);


    }
}
