import java.util.*;

// based on the work of Robert Sedgewick and Kevin Wayne
public class Graph<T> implements Iterable<User> {
    public Map<User, ArrayList<User>> verticesMap;

    public int edgesCount;

    public Graph() {
        verticesMap = new HashMap<User, ArrayList<User>>();
    }

    public int getNumVertices() {
        return verticesMap.size();
    }

    public int getNumEdges() {
        return edgesCount;
    }


    public void validateVertex(User v) {
        if (!hasVertex(v)) throw new IllegalArgumentException(v.toString() + " is not a vertex");
    }

    public int out_degree(User v)
    {
        validateVertex(v);
        return verticesMap.get(v).size();
    }

    public void addEdge(User v, User w) {
        if (!hasVertex(v)) addVertex(v);
        if (!hasVertex(w)) addVertex(w);
        if (!hasEdge(v, w)) edgesCount++;
        verticesMap.get(w).add(v);
    }

    public void addVertex(User v) {
        if (!hasVertex(v)) verticesMap.put(v, new ArrayList<User>());
    }

    public boolean hasEdge(User v, User w) {
        validateVertex(v);
        validateVertex(w);
        return verticesMap.get(v).contains(w);
    }

    public boolean hasVertex(User v) {
        return verticesMap.containsKey(v);
    }

    @Override
    public Iterator<User> iterator() {
        return verticesMap.keySet().iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (User v: verticesMap.keySet()) {
            builder.append(v.toString() + ": ");
            for (User w: verticesMap.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
    
    static User user_search(ArrayList<User> vertices, int id) {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).ID == id) {
				return vertices.get(i);
			}
		}
		return null;
	}
    
    public static int in_degree(User v) {
		return v.in_degree;
	}
    
    public static User max_indegree(Graph<User> g, ArrayList<User> vertices) {
		User maxdeg = vertices.get(0);
		int max = 0;
		for (int i = 0; i < vertices.size(); i++) {
			if (max < in_degree(vertices.get(i))) {
				maxdeg = vertices.get(i);
			}
		}
		return maxdeg;
	}
    
    public static User max_outdegree(Graph<User> g, ArrayList<User> vertices) {
		User maxdeg = vertices.get(0);
		int max;
		ArrayList<User> s2;
		s2 = (ArrayList<User>) g.verticesMap.get(vertices.get(0));
		max = s2.size();
		for (int i = 0; i < vertices.size(); i++) {
			if (s2 != null) {
				if (max < g.out_degree(vertices.get(i))) {
					maxdeg = vertices.get(i);
				}
			}
			s2 = (ArrayList<User>) g.verticesMap.get(vertices.get(i));
		}
		return maxdeg;
	}
    
    public static Graph<User> fill_in_graph(ArrayList<User> user_ojects) {
		Graph<User> graph = new Graph<User>();
		ArrayList<User> temp_followers;
		for (int i = 0; i < user_ojects.size(); i++) {
			graph.addVertex(user_ojects.get(i));
		}
		for (int i = 0; i < user_ojects.size(); i++) {
			temp_followers = user_ojects.get(i).followers;

			for (int j = 0; j < temp_followers.size(); j++) {
				graph.addEdge(temp_followers.get(j), user_ojects.get(i));
			}
		}
		return graph;
	}

}