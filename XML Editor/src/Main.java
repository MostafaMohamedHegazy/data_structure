import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
    	
    	ArrayList<String> s = new ArrayList<String>();

        s.add( "<users>");
        s.add( "	<user>");
        s.add( "		<id>1</id>");
        s.add( "		<name>Ahmed Ali</name>");
        s.add( "		<posts>");
        s.add( "			<post>");
        s.add( "				<body>");
        s.add( "					Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        s.add( "				</body>");
        s.add( "				<topics>");
        s.add( "					<topic>");
        s.add( "						economy");
        s.add( "					</topic>");
        s.add( "					<topic>");
        s.add( "						finance");
        s.add( "					</topic>");
        s.add( "				</topics>");
        s.add( "			</post>");
        s.add( "			<post>");
        s.add( "				<body>");
        s.add( "					Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        s.add( "				</body>");
        s.add( "				<topics>");
        s.add( "					<topic>");
        s.add( "						solar_energy");
        s.add( "					</topic>");
        s.add( "				</topics>");
        s.add( "			</post>");
        s.add( "		</posts>");
        s.add( "		<followers>");
        s.add( "			<follower>");
        s.add( "				<id>2</id>");
        s.add( "			</follower>");
        s.add( "			<follower>");
        s.add( "				<id>3</id>");
        s.add( "			</follower>");
        s.add( "		</followers>");
        s.add( "	</user>");
        s.add( "	<user>");
        s.add( "		<id>2</id>");
        s.add( "		<name>Yasser Ahmed</name>");
        s.add( "		<posts>");
        s.add( "			<post>");
        s.add( "				<body>");
        s.add( "					Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        s.add( "				</body>");
        s.add( "				<topics>");
        s.add( "					<topic>");
        s.add( "						education");
        s.add( "					</topic>");
        s.add( "				</topics>");
        s.add( "			</post>");
        s.add( "		</posts>");
        s.add( "		<followers>");
        s.add( "			<follower>");
        s.add( "				<id>1</id>");
        s.add( "			</follower>");
        s.add( "		</followers>");
        s.add( "	</user>");
        s.add( "	<user>");
        s.add( "		<id>3</id>");
        s.add( "		<name>Mohamed Sherif</name>");
        s.add( "		<posts>");
        s.add( "			<post>");
        s.add( "				<body>");
        s.add( "					Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        s.add( "				</body>");
        s.add( "				<topics>");
        s.add( "					<topic>");
        s.add( "						sports");
        s.add( "					</topic>");
        s.add( "				</topics>");
        s.add( "			</post>");
        s.add( "		</posts>");
        s.add( "		<followers>");
        s.add( "			<follower>");
        s.add( "				<id>1</id>");
        s.add( "			</follower>");
        s.add( "		</followers>");
        s.add( "	</user>");
        s.add( "</users>");

		for (int i = 0; i < User.fromXML(s).size(); i++) {
			System.out.println(User.fromXML(s).get(i).followers);
		}

    }
}