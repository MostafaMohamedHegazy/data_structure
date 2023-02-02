import java.util.ArrayList;
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

	public static ArrayList<User> fromXML(ArrayList<String> XML) {

		ArrayList<User> users = new ArrayList<User>();
		int lineIndex = 0;
		int charIndex = 0;
		String line;
		while (lineIndex < XML.size()) {
			line = XML.get(lineIndex);
			if (line.contains("<user>")) {
				int my_ID = 0;
				String my_name = new String();
				ArrayList<Post> my_posts = new ArrayList<Post>();
				while (!line.contains("</user>")) {
					lineIndex++;
					line = XML.get(lineIndex);
					if (line.contains("<id>") && !XML.get(lineIndex - 1).contains("<follower>")) {
						charIndex = 0;
						String S_ID = new String();
						while (line.charAt(charIndex) < '0' || line.charAt(charIndex) > '9') {
							charIndex++;
						}
						while (line.charAt(charIndex) != '<') {
							S_ID += line.charAt(charIndex);
							charIndex++;
						}
						my_ID = Integer.parseInt(S_ID);
					}
					if (line.contains("<name>")) {
						charIndex = 0;
						while (line.charAt(charIndex) != '>') {
							charIndex++;
						}
						while (line.charAt(charIndex + 1) != '<') {
							charIndex++;
							my_name += line.charAt(charIndex);
						}
					}
					if (line.contains("<posts>")) {
						while (!line.contains("</posts>")) {
							lineIndex++;
							line = XML.get(lineIndex);
							if (line.contains("<post>")) {
								String my_body = new String();
								ArrayList<String> my_topics = new ArrayList<String>();
								while (!line.contains("</post>")) {
									lineIndex++;
									line = XML.get(lineIndex);
									if (line.contains("<body>")) {
										lineIndex++;
										line = XML.get(lineIndex);
										charIndex = 0;
										while (line.charAt(charIndex) == ' ' || line.charAt(charIndex) == '	') {
											charIndex++;
										}
										while (charIndex < line.length()) {
											my_body += line.charAt(charIndex);
											charIndex++;
										}
									}
									if (line.contains("<topic>")) {
										lineIndex++;
										line = XML.get(lineIndex);
										charIndex = 0;
										String temp = new String();
										while (line.charAt(charIndex) == ' ' || line.charAt(charIndex) == '	') {
											charIndex++;
										}
										while (charIndex < line.length()) {
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

		while (lineIndex < XML.size()) {
			line = XML.get(lineIndex);
			if (line.contains("<user>")) {
				ArrayList<User> my_followers = new ArrayList<User>();
				while (!line.contains("</user>")) {
					lineIndex++;
					line = XML.get(lineIndex);
					if (line.contains("<followers>")) {
						while (!line.contains("</followers>")) {
							lineIndex++;
							line = XML.get(lineIndex);
							if (line.contains("<follower>")) {
								line = XML.get(lineIndex + 1);
								int follower_ID = 0;
								charIndex = 0;
								while (line.charAt(charIndex) < '0' || line.charAt(charIndex) > '9') {
									charIndex++;
								}
								String temp = new String();
								while (line.charAt(charIndex) != '<') {
									temp += line.charAt(charIndex);
									charIndex++;
								}
								follower_ID = Integer.parseInt(temp);
								for (int i = 0; i < users.size(); i++) {
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

	static void DFS(Graph<User> g, User start) {
		boolean[] visited = new boolean[g.verticesMap.keySet().size()];
		User v = start;
		Stack<User> user_stack = new Stack<>();
		user_stack.push(start);
		ArrayList<User> s;

		do {
			if (!visited[v.ID - 1]) {
				s = (ArrayList<User>) g.verticesMap.get(v);
				/*
				 * System.out.println(s); System.out.println(s.get(0));
				 * System.out.println(s.get(1));
				 */
				for (int i = 0; i < s.size(); i++) {
					if (!visited[s.get(i).ID - 1]) {
						user_stack.push(s.get(i));
					}
				}
				visited[v.ID - 1] = true;

			}
		} while (!user_stack.isEmpty());
		// g.verticesMap.keySet() ;
	}

	static User user_search(Graph<User> g, ArrayList<User> vertices, int id) {
		// User u=null;
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).ID == id) {
				return vertices.get(i);
			}
		}
		return null;
	}

	static User user_search(Graph<User> g, User start, int id) {
		boolean[] visited = new boolean[g.verticesMap.keySet().size()];
		User v = start;
		Stack<User> user_stack = new Stack<>();
		user_stack.push(start);
		ArrayList<User> s;

		do {
			v = user_stack.pop();
			if (v.ID == id) {
				return v;
			}

			if (!visited[v.ID - 1]) {
				s = (ArrayList<User>) g.verticesMap.get(v);
				/*
				 * System.out.println(s); System.out.println(s.get(0));
				 * System.out.println(s.get(1));
				 */
				for (int i = 0; i < s.size(); i++) {
					if (!visited[s.get(i).ID - 1]) {
						user_stack.push(s.get(i));
					}
				}
				visited[v.ID - 1] = true;

			}
		} while (!user_stack.isEmpty());
		// g.verticesMap.keySet() ;
		return null;
	}

	public static int in_degree(Graph<User> g, User v) {
		g.validateVertex(v);
		return v.followers.size();
	}

	public static User max_indegree(Graph<User> g, ArrayList<User> vertices) {
		User maxdeg = vertices.get(0);
		int max;
		ArrayList<User> s2 = new ArrayList<>();
		s2 = (ArrayList<User>) g.verticesMap.get(vertices.get(0));
		max = s2.size();
		for (int i = 0; i < vertices.size(); i++) {
			if (max < in_degree(g, vertices.get(i))) {
				maxdeg = vertices.get(i);
			}
		}
		return maxdeg;
	}

	public static User max_indegree(Graph<User> g, User start) {
		int max;
		boolean[] visited = new boolean[g.verticesMap.keySet().size()];
		User v = start;
		User maxdeg;
		Stack<User> user_stack = new Stack<>();
		user_stack.push(start);
		ArrayList<User> s;
		max = in_degree(g, start);
		maxdeg = start;
		do {
			v = user_stack.pop();

			if (in_degree(g, v) > max) {
				max = in_degree(g, v);
				maxdeg = v;
			}
			if (!visited[v.ID - 1]) {
				s = (ArrayList<User>) g.verticesMap.get(v);
				/*
				 * System.out.println(s); System.out.println(s.get(0));
				 * System.out.println(s.get(1));
				 */
				for (int i = 0; i < s.size(); i++) {
					if (!visited[s.get(i).ID - 1]) {
						user_stack.push(s.get(i));
					}
				}
				visited[v.ID - 1] = true;

			}
		} while (!user_stack.isEmpty());
		return maxdeg;
	}

	public static User max_out(Graph<User> g, ArrayList<User> vertices) {
		User maxdeg = vertices.get(0);
		int max;
		ArrayList<User> s2 = new ArrayList<>();
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

	public static User max_outdegree(Graph<User> g, User start) {
		int max;
		boolean[] visited = new boolean[g.verticesMap.keySet().size()];
		User v = start;
		User maxdeg;
		Stack<User> user_stack = new Stack<>();
		user_stack.push(start);
		ArrayList<User> s;
		max = in_degree(g, start);
		maxdeg = start;
		do {
			v = user_stack.pop();

			if (g.out_degree(v) > max) {
				max = g.out_degree(v);
				maxdeg = v;
			}
			if (!visited[v.ID - 1]) {
				s = (ArrayList<User>) g.verticesMap.get(v);

				for (int i = 0; i < s.size(); i++) {
					if (!visited[s.get(i).ID - 1]) {
						user_stack.push(s.get(i));
					}
				}
				visited[v.ID - 1] = true;

			}
		} while (!user_stack.isEmpty());

		return maxdeg;
	}

	public static ArrayList<User> mutual_followers(Graph<User> g, ArrayList<User> vertices, int n1, int n2) {
		ArrayList<User> mutual_users = new ArrayList<>();
		User u1 = user_search(g, vertices, n1);
		User u2 = user_search(g, vertices, n2);
		for (int i = 0; i < u1.followers.size(); i++) {
			for (int j = 0; j < u2.followers.size(); j++) {
				if (u1.followers.get(i).ID == u2.followers.get(j).ID) {
					mutual_users.add(u2.followers.get(j));
				}
			}
		}
		return mutual_users;
	}

	public static Graph<User> fill_in_graph(ArrayList<User> user_ojects) {
		Graph<User> graph = new Graph<>();
		ArrayList<User> temp_followers = new ArrayList<>();
		for (int i = 0; i < user_ojects.size(); i++) {
			graph.addVertex(user_ojects.get(i));
			// System.out.println(user_ojects.get(i).toString());
			// System.out.println(b);
		}
		for (int i = 0; i < user_ojects.size(); i++) {
			temp_followers = user_ojects.get(i).followers;
			// System.out.println(user_ojects.get(i));
			// System.out.println(temp_followers);
			for (int j = 0; j < temp_followers.size(); j++) {
				// System.out.println("user_ojects.get(i) "+user_ojects.get(i).ID);
				// System.out.println("temp_followers.get(j) "+temp_followers.get(j).ID);
				graph.addEdge(temp_followers.get(j), user_ojects.get(i));

			}
			// System.out.println(user_ojects.get(i).toString());
			// System.out.println(b);
		}
		return graph;
	}
	
}