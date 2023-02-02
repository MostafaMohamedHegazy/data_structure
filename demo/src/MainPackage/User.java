package MainPackage;

import java.util.ArrayList;

public class User {

    public int ID;
    public int in_degree;
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
                                        users.get(i).in_degree++;
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

}