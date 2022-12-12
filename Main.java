import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {

    static ArrayList<String>  Convert2JSON( ArrayList<String> a){
        Stack<Tag> tag_s = new Stack<Tag>();
        Queue<Tag> tag_q = new LinkedList<Tag>() ;
        //ArrayList<String> aux= new ArrayList<String>();
        ArrayList<Tag> tag_list = new ArrayList<Tag>();
        ArrayList<Tag> openingtag_list = new ArrayList<Tag>();
        ArrayList<String> b = new ArrayList<String>();
        //Queue<String> q = new LinkedList<String>() ;
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
                        System.out.println("this is both tags line");
                        System.out.println(""+(i-1)+" "+a.get((i-1)));
                        tag_list.add(new Tag(a.get(i-1),(i-1),j-1,"both tags"));
                        tag_q.add(new Tag(a.get(i-1),(i-1),j-1,"both tags"));
                        break;

                    }
                    else
                    {
                        openingtag=true;
                        closingtag =false ;
                        bothtags=false;

                        System.out.println("this is opening tag line");
                        System.out.println(""+(i-1)+" "+a.get(i-1));
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
                tag_q.add(new Tag(a.get((i-1)),(i-1),k-1,"closing tag"));
                System.out.println("this is closing tag line");
                System.out.println(""+(i-1)+" "+a.get((i-1)));
            }
            if((!closingtag && !openingtag && !bothtags))
            {
                closingtag =false;
                openingtag=false;
                bothtags =false ;
                tag_list.add(new Tag(a.get((i-1)),(i-1),0,"data"));
                tag_q.add(new Tag(a.get((i-1)),(i-1),0,"data"));
                System.out.println("this is data line");
                System.out.println(""+(i-1)+" "+a.get((i-1)));

            }
            openingtag=false ; closingtag =false ; bothtags=false;
        }
        for(int m=0;m< tag_list.size();m++)
        {
            tag_list.get(m).isArray(tag_list);
        }
        System.out.println("**********************************************************************hiiiiiiii");
        int num=0;
        int line=0;
        boolean IS_Array=false;
        System.out.println("{");
        b.add("{\n");
        Tag T;
        String compare_margins="";
        for(int m=0;m< tag_list.size();m++)
        {
            if(tag_list.get(m).tag_type=="opening tag")
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

                T=tag_list.get(m);
                tag_s.push(tag_list.get(m))   ;
                String st0="\t";
                for(int t=0; t<tag_list.get(m).label.length();t++)
                {
                    if(tag_list.get(m).label.charAt(t)=='<')
                    {
                        st0=st0+"\"";
                    }
                    else if(tag_list.get(m).label.charAt(t)=='>')
                    {
                        if(tag_list.get(m).is_array)
                        {
                            st0=st0+"\":[";
                            line=m;
                            num=tag_list.get(m).margin;
                            IS_Array=true;

                        }
                        else
                        {
                            st0=st0+"\":{";
                        }
                        break;
                    }
                    else
                    {
                        st0=st0+tag_list.get(m).label.charAt(t);
                    }
                }
                System.out.println(st0);
                b.add(st0+"\n");
            }

            if(tag_list.get(m).tag_type=="closing tag")
            {
                Tag poped_tag;
                Tag aux_tag=tag_list.get(m);
                { poped_tag=tag_s.pop(); }
                String st0="\t";
                int count=0;
                for(int t=0;t<tag_list.get(m).label.length();t++)
                {
                    if(tag_list.get(m).label.charAt(t)=='<')
                    {
                        if(tag_list.get(m).margin==num && num>0 && IS_Array)
                        {
                            st0=st0+"]";
                            num=0;
                        }
                        else
                        {
                            st0=st0+"}";
                        }
                        if(m<tag_list.size()-1)
                        {
                            if (compare_margins == "equal" || tag_list.get(m + 1).margin==aux_tag.margin) {
                                st0 = st0 + ",";
                            }
                        }
                        break;
                    }
                    st0=st0+" ";
                }
                System.out.println(st0);
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
                System.out.println(st0);
                b.add(st0+"\n");
            }

            if(tag_list.get(m).tag_type=="data")
            {
                String st0="\t";
                int count=0;
                for(int t=0; t<tag_list.get(m).label.length();t++)
                {
                    if(tag_list.get(m).label.charAt(t) !=' ')
                    {
                        count++;
                        if(count<=1){ st0=st0+"\""; }
                    }
                    st0=st0+tag_list.get(m).label.charAt(t);

                }
                st0=st0+"\"";
                System.out.println(st0);
                b.add(st0+"\n");
            }

        }
        System.out.println("}");
        b.add("}\n");
        return b;
    }
    public static void main(String[] args) {

        ArrayList<String> s = new ArrayList<String>();
        s.add( "<users>");
        s.add( "    <user>");
        s.add( "        <id>1</id>");
        s.add( "        <name>Ahmed Ali</name>");
        s.add( "        <posts>");
        s.add( "            <post>");
        s.add( "                <body>");
        s.add( "                    FooK");
        s.add( "                </body>");
        s.add( "                <topics>");
        s.add( "                    <topic>");
        s.add( "                        UNI...HARD!!");
        s.add( "                    </topic>");
        s.add( "                    <topic>");
        s.add( "                        LOL!");
        s.add( "                    </topic>");
        s.add( "                </topics>");
        s.add( "            </post>");
        s.add( "            <post>");
        s.add( "                <body>");
        s.add( "                    Oh...Noo!");
        s.add( "                </body>");
        s.add( "                <topics>");
        s.add( "                    <topic>");
        s.add( "                        UNI!..Stop :(");
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
        s.add( "</users>");
        //File.checkConsistency(s);

        Convert2JSON(s);
    	/*for (int i = 0; i < s.size(); i++) {
			System.out.print(Convert2JSON(s).get(i));
		}*/

    }
}