import java.util.ArrayList;

public class Post {

    public String body;
    public ArrayList<String> topics;

    public Post(String body, ArrayList<String> topics) {

        this.body = body;
        this.topics = topics;
    }

    @Override
    public String toString() {
        return "Post [body=" + body + ", topics=" + topics + "]";
    }
}
