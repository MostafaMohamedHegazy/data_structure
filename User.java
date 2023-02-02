import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

public class User {

    public int ID;
    public String name;
    public ArrayList<Post> posts;
    public ArrayList<User> followers;

    public User(int iD, String name, ArrayList<Post> posts) {

        ID = iD;
        this.name = name;
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User [ID=" + ID + ", name=" + name + "]";
    }

    public static ArrayList<User> fromXML(ArrayList<String> XML){

        ArrayList<User> users = new ArrayList<User>();
        int lineIndex = 0;
        int charIndex = 0;
        String line;
        while(lineIndex < XML.size()) {
            line = XML.get(lineIndex);
            if (line.contains("<user>")) {
                int my_ID = 0;
                String my_name = new String();
                ArrayList<Post> my_posts = new ArrayList<Post>();
                while(!line.contains("</user>")) {
                    lineIndex++;
                    line = XML.get(lineIndex);
                    if(line.contains("<id>") && !XML.get(lineIndex-1).contains("<follower>")){
                        charIndex = 0;
                        String S_ID = new String();
                        while(line.charAt(charIndex)<'0' || line.charAt(charIndex)>'9') {
                            charIndex++;
                        }
                        while (line.charAt(charIndex) != '<') {
                            S_ID += line.charAt(charIndex);
                            charIndex++;
                        }
                        my_ID = Integer.parseInt(S_ID);
                    }
                    if(line.contains("<name>")){
                        charIndex = 0;
                        while (line.charAt(charIndex) != '>') {
                            charIndex++;
                        }
                        while (line.charAt(charIndex+1) != '<') {
                            charIndex++;
                            my_name += line.charAt(charIndex);
                        }
                    }
                    if(line.contains("<posts>")){
                        while(!line.contains("</posts>")) {
                            lineIndex++;
                            line = XML.get(lineIndex);
                            if(line.contains("<post>")) {
                                String my_body = new String();
                                ArrayList<String> my_topics = new ArrayList<String>();
                                while(!line.contains("</post>")) {
                                    lineIndex++;
                                    line = XML.get(lineIndex);
                                    if (line.contains("<body>")) {
                                        lineIndex++;
                                        line = XML.get(lineIndex);
                                        charIndex = 0;
                                        while(line.charAt(charIndex) == ' ' ||line.charAt(charIndex) == '	') {
                                            charIndex++;
                                        }
                                        while(charIndex < line.length()) {
                                            my_body += line.charAt(charIndex);
                                            charIndex++;
                                        }
                                    }
                                    if (line.contains("<topic>")) {
                                        lineIndex++;
                                        line = XML.get(lineIndex);
                                        charIndex = 0;
                                        String temp = new String();
                                        while(line.charAt(charIndex) == ' ' ||line.charAt(charIndex) == '	') {
                                            charIndex++;
                                        }
                                        while(charIndex < line.length()) {
                                            temp += line.charAt(charIndex);
                                            charIndex++;
                                        }
                                        my_topics.add(temp);
                                    }
                                }
                                Post post = new Post(my_body, my_topics);
                                my_posts.add(post);
                            }
                        }
                    }
                }
                User user = new User(my_ID, my_name, my_posts);
                users.add(user);
            }
            lineIndex++;
        }

        lineIndex = 0;
        int userIndex = 0;

        while(lineIndex < XML.size()) {
            line = XML.get(lineIndex);
            if (line.contains("<user>")) {
                ArrayList<User> my_followers = new ArrayList<User>();
                while(!line.contains("</user>")) {
                    lineIndex++;
                    line = XML.get(lineIndex);
                    if(line.contains("<followers>")){
                        while(!line.contains("</followers>")) {
                            lineIndex++;
                            line = XML.get(lineIndex);
                            if(line.contains("<follower>")){
                                line = XML.get(lineIndex+1);
                                int follower_ID = 0;
                                charIndex = 0;
                                while(line.charAt(charIndex)<'0' || line.charAt(charIndex)>'9') {
                                    charIndex++;
                                }
                                String temp = new String();
                                while (line.charAt(charIndex) != '<') {
                                    temp += line.charAt(charIndex);
                                    charIndex++;
                                }
                                follower_ID = Integer.parseInt(temp);
                                for (int i = 0; i <users.size(); i++) {
                                    if (users.get(i).ID == follower_ID) {
                                        my_followers.add(users.get(i));
                                    }
                                }
                            }
                        }
                        users.get(userIndex).followers = my_followers;
                    }
                }
                userIndex++;
            }
            lineIndex++;
        }
        return users;
    }
     static void DFS(Graph g, User start)
    {
        boolean[] visited=new boolean[g.verticesMap.keySet().size()];
        User v=start;
        Stack<User> user_stack = new Stack<>();
        user_stack.push(start);
        ArrayList<User> s;

        do
        {
            System.out.println(v=user_stack.pop());
            if(!visited[v.ID-1])
            {
                s= (ArrayList<User>) g.verticesMap.get(v) ;
                /*System.out.println(s);
                System.out.println(s.get(0));
                System.out.println(s.get(1));*/
                for(int i=0 ;i<s.size();i++)
                {
                    if(!visited[s.get(i).ID-1])
                    {
                        user_stack.push(s.get(i));
                    }
                }
                visited[v.ID-1]=true;

            }
        }
        while(!user_stack.isEmpty());
        //g.verticesMap.keySet() ;
    }
    public static int in_degree(Graph g,User v)
    {
        g.validateVertex(v);
        return v.followers.size();
    }
    public static User max_indegree(Graph g, User start)
    {
        int max;
        boolean[] visited=new boolean[g.verticesMap.keySet().size()];
        User v=start;
        User maxdeg;
        Stack<User> user_stack = new Stack<>();
        user_stack.push(start);
        ArrayList<User> s;
        max=in_degree( g,start);
        maxdeg=start;
        do
        {
            v=user_stack.pop();

            if(in_degree( g , v)>max)
            {
                 max=in_degree( g,v);
                 maxdeg=v;
            }
            if(!visited[v.ID-1])
            {
                s= (ArrayList<User>) g.verticesMap.get(v) ;
                /*System.out.println(s);
                System.out.println(s.get(0));
                System.out.println(s.get(1));*/
                for(int i=0 ;i<s.size();i++)
                {
                    if(!visited[s.get(i).ID-1])
                    {
                        user_stack.push(s.get(i));
                    }
                }
                visited[v.ID-1]=true;

            }
        }
        while(!user_stack.isEmpty());
        return maxdeg;
    }
    public static User max_outdegree(Graph g, User start)
    {
        int max;
        boolean[] visited=new boolean[g.verticesMap.keySet().size()];
        User v=start;
        User maxdeg;
        Stack<User> user_stack = new Stack<>();
        user_stack.push(start);
        ArrayList<User> s;
        max=in_degree( g,start);
        maxdeg=start;
        do
        {
            v=user_stack.pop();

            if(g.out_degree(v)>max)
            {
                max=g.out_degree(v);
                maxdeg=v;
            }
            if(!visited[v.ID-1])
            {
                s= (ArrayList<User>) g.verticesMap.get(v) ;
                /*System.out.println(s);
                System.out.println(s.get(0));
                System.out.println(s.get(1));*/
                for(int i=0 ;i<s.size();i++)
                {
                    if(!visited[s.get(i).ID-1])
                    {
                        user_stack.push(s.get(i));
                    }
                }
                visited[v.ID-1]=true;

            }
        }
        while(!user_stack.isEmpty());
        return maxdeg;
    }
    public static void main(String[] args)
    {

        ArrayList<String> b = new ArrayList<>();
        ArrayList<User> temp_followers = new ArrayList<>();
        ArrayList<User> user_ojects = new ArrayList<>();
        b=ReadFile.returna_array();
        user_ojects=fromXML(b);
        Graph<User> graph = new Graph<>();

        for(int i=0 ; i<user_ojects.size();i++)
        {
            graph.addVertex(user_ojects.get(i));
            //System.out.println(user_ojects.get(i).toString());
            //System.out.println(b);
        }
        //for(int m=0 ; m<user_ojects.size();m++)
        //{
            for(int i=0 ; i<user_ojects.size();i++)
            {
                temp_followers=user_ojects.get(i).followers;
                //System.out.println(user_ojects.get(i));
                //System.out.println(temp_followers);
                for(int j=0 ; j<temp_followers.size();j++)
                {
                    //System.out.println("user_ojects.get(i)      "+user_ojects.get(i).ID);
                    //System.out.println("temp_followers.get(j)   "+temp_followers.get(j).ID);

                        graph.addEdge(user_ojects.get(i),temp_followers.get(j));

                }
                //System.out.println(user_ojects.get(i).toString());
                //System.out.println(b);
            }
        //}
        //graph.addEdge(user_ojects.get(2),user_ojects.get(0));
        //System.out.println(user_ojects.get(2).toString());
       // System.out.println("******************graph vertices*******************\n");
       // System.out.println(graph.verticesMap.keySet());
       // System.out.println("******************adjacency list of 1st vertex  graph*******************\n");
       // System.out.println(graph.verticesMap.get(user_ojects.get(0)));
        System.out.println("******************graph*******************\n");
        System.out.println(graph);
        System.out.println("****************************************");
        System.out.println("Vertices: " + graph.getNumVertices());
        System.out.println("Edges: " + graph.getNumEdges());
        //System.out.println("****************************************\n");
        //System.out.println(user_ojects.get(0)+"  OUT DEGREE : "+graph.out_degree(user_ojects.get(0)));
        //System.out.println(user_ojects.get(0)+"  IN DEGREE : "+User.in_degree(graph,user_ojects.get(0)));
        System.out.println("\n******************MAX IN DEG*******************\n");
        User u=max_indegree(graph , user_ojects.get(1));
        System.out.println(u+"  IN DEGREE : "+User.in_degree(graph,u));
        //DFS(graph,user_ojects.get(0));max_outdegree
        System.out.println("\n******************MAX OUT DEG*******************\n");
        User u1=max_outdegree(graph , user_ojects.get(1));
        System.out.println(u1+"  out DEGREE : "+ graph.out_degree(u1));


    }

}