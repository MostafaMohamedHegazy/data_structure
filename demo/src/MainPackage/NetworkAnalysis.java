package MainPackage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class NetworkAnalysis {

    public static User MostInfluencer(ArrayList<String> XML) {
        ArrayList<User> users;
        users = User.fromXML(XML);
        Graph<User> g = Graph.fill_in_graph(users);
        return Graph.max_outdegree(g, users);
    }

    public static User MostActive(ArrayList<String> XML) {
        ArrayList<User> users;
        users = User.fromXML(XML);
        Graph<User> g = Graph.fill_in_graph(users);
        return Graph.max_indegree(g, users);
    }

    public static ArrayList<User> MutualFollowers(ArrayList<String> XML, int n1, int n2) {
        ArrayList<User> vertices;
        vertices = User.fromXML(XML);
        ArrayList<User> mutual_users = new ArrayList<>();
        User u1 = Graph.user_search(vertices, n1);
        User u2 = Graph.user_search(vertices, n2);
        for (int i = 0; i < u1.followers.size(); i++) {
            for (int j = 0; j < u2.followers.size(); j++) {
                if (u1.followers.get(i).ID == u2.followers.get(j).ID) {
                    mutual_users.add(u2.followers.get(j));
                }
            }
        }
        return mutual_users;
    }

    public static ArrayList<User> SuggestFollowers(ArrayList<String> XML, int ID) {
        ArrayList<User> users;
        users = User.fromXML(XML);
        User my_user = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).ID == ID) {
                my_user = users.get(i);
            }
        }
        ArrayList<User> SF = new ArrayList<User>();
        Graph<User> g = Graph.fill_in_graph(users);
        boolean[] visited = new boolean[g.verticesMap.keySet().size()];
        boolean[] added = new boolean[g.verticesMap.keySet().size()];
        User v = my_user;
        Queue<User> queue = new LinkedList<User>();
        queue.add(my_user);
        ArrayList<User> s;
        int c = my_user.followers.size();
        do {
            v = queue.remove();
            if (!visited[v.ID - 1]) {
                s = (ArrayList<User>) g.verticesMap.get(v);
                for (int i = 0; i < s.size(); i++) {
                    if (!visited[s.get(i).ID - 1] && !added[s.get(i).ID - 1]) {
                        queue.add(s.get(i));
                        added[s.get(i).ID - 1] = true;
                    }
                }
                visited[v.ID - 1] = true;
                if (c == 0) {
                    break;
                }
                c--;
            }
        } while (!queue.isEmpty());

        while (!queue.isEmpty()) {
            SF.add(queue.remove());
        }
        return SF;
    }

    public static ArrayList<Post> Search(ArrayList<String> XML, String word) {
        ArrayList<User> Users;
        Users = User.fromXML(XML);
        ArrayList<Post> found = new ArrayList<Post>();
        for (int i = 0; i < Users.size(); i++) {
            for (int j = 0; j < Users.get(i).posts.size(); j++) {
                if (Users.get(i).posts.get(j).body.contains(word)) {
                    found.add(Users.get(i).posts.get(j));
                    continue;
                }
                for (int k = 0; k < Users.get(i).posts.get(j).topics.size(); k++) {
                    if (Users.get(i).posts.get(j).topics.contains(word)) {
                        found.add(Users.get(i).posts.get(j));
                        break;
                    }
                }
            }
        }
        return found;
    }
}