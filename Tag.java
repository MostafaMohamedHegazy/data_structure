import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class Tag {
    String label;
    boolean in_Array;
    int line;
    int margin;
    String tag_type , name;
    boolean is_json_Object, is_Array_element ,is_1stArray_element ;
    boolean  is_array;
    public Tag(String label, int line, int margin) {
        super();
        this.label = label;
        this.line = line;
        this.margin = margin;
    }
    public Tag(String label, int line, int margin, String st) {
        //super();
        if(st=="both tags" || st=="opening tag" || st=="closing tag" || st=="data"){this.tag_type=st;}
        else{throw new IllegalArgumentException("illegal argument !");}
        this.label = label;
        this.line = line;
        this.margin = margin;
        is_Array_element=false;
        is_json_Object=false;
        is_array=false;
        is_1stArray_element=false;
        in_Array=false;
    }

    void isArray(ArrayList<Tag> list)
    {
        //System.out.println("**********************************************************************hiiiiiiii");
        int i=0;
        int j=0;
        i=this.line;
        int k,m;

        if((list.get(i).tag_type.equals("opening tag" )))
        {
            k=i+1;
            //System.out.println("k-1 label : "+list.get(k-1).label);
            //System.out.println("k label : "+list.get(k).label);
            //System.out.println("k name : "+list.get(k).name+" type: "+list.get(k).tag_type);
            for (j = k+1; j < list.size(); j++)
            {
                //System.out.println("j label : "+list.get(j).label);
                //System.out.println("j name : "+list.get(j).name);
                if (list.get(j).name.equals(list.get(k).name) && list.get(j).tag_type.equals("closing tag")) {
                    //System.out.println("break is array at: "+list.get(j).label);
                    break;
                }
            }
            if(j<list.size()-1)
            {
                if ((list.get(k).tag_type.equals("opening tag") && list.get(j + 1).tag_type.equals("opening tag")) && list.get(k).name.equals(list.get(j + 1).name))
                {

                    this.is_array = true;
                    list.get(k).is_Array_element = true;
                    list.get(k).is_1stArray_element = true;
                    //list.get(j+1).is_Array_element = true;
                    //System.out.println(" list.get(k).is_Array_element = true at: "+list.get(k).label);
                    //System.out.println(" list.get(k).is_1stArray_element = true at: "+list.get(k).label);
                    //System.out.println("true is array at: "+list.get(j).label);
                    for (int l = j+1; l < list.size(); l++)
                    {
                        if (list.get(l).name.equals(list.get(k-1).name) && list.get(l).tag_type.equals("closing tag"))
                        {
                            //System.out.println("l label : "+list.get(l).label);
                            //System.out.println("break is array at: "+list.get(l).label);
                            break;
                        }
                        if(list.get(l).tag_type.equals("opening tag")&& list.get(l).name.equals(list.get(k).name))
                        {
                            list.get(l).is_Array_element = true;
                        }


                    }

                }
            }

         }
        /******************************************************
        int i=0;
        int j=0;
        i=this.line;
        int k,m;
        if((list.get(i).tag_type=="opening tag" ))
        {

            k=i+1;

            //System.out.println("k label : "+list.get(k).label);
            //System.out.println("k name : "+list.get(k).label);

            for (j = k+1; j < list.size(); j++)
            {
                //System.out.println("j label : "+list.get(j).label);
                //System.out.println("j name : "+list.get(j).name);
                if (list.get(j).name.equals(list.get(i).name)  && list.get(j).tag_type == "closing tag")
                {
                    //System.out.println("break is array at: "+list.get(j).label);
                    break;
                }
                if ( (list.get(k).tag_type.equals("opening tag") && list.get(j).tag_type.equals("opening tag")) && list.get(k).label.equals(list.get(j).label) )
                {
                    this.is_array = true;
                    list.get(k).is_Array_element = true;
                    list.get(k).is_1stArray_element = true;
                    list.get(j).is_Array_element = true;
                    //System.out.println(" list.get(k).is_Array_element = true at: "+list.get(k).label);
                    //System.out.println(" list.get(k).is_1stArray_element = true at: "+list.get(k).label);
                    //System.out.println("true is array at: "+list.get(j).label);

                }
            }

        }*/

    }

    boolean isObject(ArrayList<Tag> list , int i)
    {
        is_json_Object=false;
        int j=0;

        for(j=i+1;j<list.size();j++)
        {
            if(list.get(j).name.equals(list.get(i).name) && list.get(j).tag_type=="closing tag")   //closing tag
            {
                break;
            }

            if(((list.get(j).tag_type=="opening tag" && list.get(i).tag_type=="opening tag")) ||((list.get(j).tag_type=="both tags" && list.get(i).tag_type=="opening tag")))
            {
                is_json_Object=true;
                break;
            }

        }

        return is_json_Object;
    }

    void get_name()
    {
        int i=0;
        String name="";
        if(this.tag_type.equals("opening tag") || this.tag_type.equals("closing tag") )
        {
            if(this.tag_type.equals("opening tag"))
            {
                for (i = 0; i < this.label.length(); i++) {
                    if (this.label.charAt(i) == '<') { i++ ; break;}
                }
                for (int j = i; j < this.label.length(); j++)
                {
                      if(label.charAt(j)!= '>'){name=name+this.label.charAt(j);}
                      else{ break; }
                }
            }
            if(this.tag_type.equals("closing tag"))
            {
                for (i = 0; i < this.label.length(); i++) {
                    if (this.label.charAt(i) == '/') {i++ ; break;}
                }
                for (int j = i; j < this.label.length(); j++)
                {
                    if(label.charAt(j)!= '>'){name=name+this.label.charAt(j);}
                    else{ break; }
                }
            }


        }
        else
        {

        }
        this.name=name;

    }

}
