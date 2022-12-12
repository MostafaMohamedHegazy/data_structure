import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class Tag {
    String label;
    int line;
    int margin;
    String tag_type;
    boolean is_array;
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

    }

    boolean isArray(ArrayList<Tag> list)
    {
        Stack<Tag> temp_stack=new Stack<Tag>();
        this.is_array=false;
        int k=this.line;
        int store_line=0;
        int count=0;
        int num=0;
        int i=0;
        int j=0;

        for(i=0;i<list.size();i++)
        {
            if(list.get(i).label==this.label && list.get(i).line==this.line)
            {
                break;
            }
        }

        for(j=this.line+2;j<list.size();j++)
        { if(list.get(j).margin==list.get(i).margin)
        {
            break;
        }
            if(list.get(i+1).label==list.get(j).label)
            {
                is_array=true;
                break;
            }
        }

        return  is_array ;
    }
}