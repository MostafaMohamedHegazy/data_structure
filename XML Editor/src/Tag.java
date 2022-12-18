import java.util.ArrayList;

public class Tag {
	String label;
	boolean in_Array;
	int line;
	int margin;
	String tag_type;
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

	boolean isArray(ArrayList<Tag> list)
	{
		int i=0;
		int j=0;
		i=this.line;

		for (j = i + 2; j < list.size(); j++)
		{
			if (list.get(j).margin == list.get(i).margin && list.get(j).tag_type == "closing tag")
			{
				break;
			}
			if ( (list.get(i+1).tag_type=="opening tag" && list.get(j).tag_type=="opening tag") && list.get(i + 1).label == list.get(j).label )
			{
				this.is_array = true;
				list.get(i + 1).is_Array_element = true;
				list.get(i + 1).is_1stArray_element = true;
				list.get(j).is_Array_element = true;

			}
		}
		return  is_array ;
	}

	boolean isObject(ArrayList<Tag> list)
	{
		is_json_Object=false;
		int i=0;
		int j=0;

		for(i=0;i<list.size();i++)
		{
			if(list.get(i).label==this.label && list.get(i).line==this.line)
			{
				break;
			}
		}


		for(j=i+1;j<list.size();j++)
		{
			if(list.get(j).margin==list.get(i).margin && list.get(j).tag_type=="closing tag")   //closing tag
			{
				break;
			}

			if(((list.get(i).margin<list.get(j).margin) && (list.get(j).tag_type=="opening tag" && list.get(i).tag_type=="opening tag")) ||((list.get(i).margin<list.get(j).margin) && (list.get(j).tag_type=="both tags" && list.get(i).tag_type=="opening tag")))
			{
				is_json_Object=true;
				break;
			}

		}

		return is_json_Object;
	}

}
