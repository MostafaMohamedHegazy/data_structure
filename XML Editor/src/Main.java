import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
    	
    	ArrayList<String> s = new ArrayList<String>();
        s.add( "<users>");
        s.add( "    <user>");
        s.add( "        <id>1</id>");
        s.add( "        <name>Ahmed Ali</name>");
        s.add( "        <posts>");
        s.add( "            <post>");
        s.add( "                <body>");
        s.add( "                    FooK");
        s.add( "                </body>");
        s.add( "                <topics>");
        s.add( "                    <topic>");
        s.add( "                        UNI...HARD!!");
        s.add( "                    </topic>");
        s.add( "                    <topic>");
        s.add( "                        LOL!");
        s.add( "                    </topic>");
        s.add( "                </topics>");
        s.add( "            </post>");
        s.add( "             <post>");
        s.add( "                <body>");
        s.add( "                    Oh...Noo!");
        s.add( "                </body>");
        s.add( "                <topics>");
        s.add( "                    <topic>");
        s.add( "                        UNI!..Stop :(");
        s.add( "                    </topic>");
        s.add( "                </topics>");
        s.add( "            </post>");
        s.add( "        </posts>");
        s.add( "        <followers>");
        s.add( "            <follower>");
        s.add( "                <id>2</id>");
        s.add( "            </follower>");
        s.add( "            <follower>");
        s.add( "                <id>3</id>");
        s.add( "            </follower>");
        s.add( "        </followers>");
        s.add( "    </user>");
        s.add( "</users>");
    	File.checkConsistency(s);
    	for (int i = 0; i < s.size(); i++) {
			System.out.println(s.get(i));
		}
    }
}