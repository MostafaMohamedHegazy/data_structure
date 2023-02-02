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
        //verticesMap.get(v).add(w);
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
}