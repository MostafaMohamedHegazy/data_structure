import java.util.ArrayList;

public class PostSearch {

    public static ArrayList<Post> Search (ArrayList<User> Users,String word){
        ArrayList<Post> found=new ArrayList<Post>();
           for(int i=0;i<Users.size();i++){
            for(int j=0;j<Users.get(i).posts.size();j++){
                    if(Users.get(i).posts.get(j).body.contains(word)){
                        found.add(Users.get(i).posts.get(j));
                        continue;
                    }
                    for(int k=0;k<Users.get(i).posts.get(j).topics.size();k++){
                      if(Users.get(i).posts.get(j).topics.contains(word)){
                        found.add(Users.get(i).posts.get(j));
                        break;
                      }
                    }
            }
           }
        return found;
    }
}