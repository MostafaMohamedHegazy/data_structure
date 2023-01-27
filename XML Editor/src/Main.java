import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {


    	/*ArrayList<String> generalXML = new ArrayList<String>();
		ArrayList<String> correctIndentsXML = new ArrayList<String>();
		
		generalXML.add( "<users>");
		generalXML.add( "    <user>");
		generalXML.add( "        <id>1</id>");
		generalXML.add( "        <name>Ahmed Ali</name>");
		generalXML.add( "        <posts>");
		generalXML.add( "            <post>");
		generalXML.add( "                <body>");
		generalXML.add( "                    FooK");
		generalXML.add( "                </body>");
		generalXML.add( "                <topics>");
		generalXML.add( "                    <topic>");
		generalXML.add( "                        UNI...HARD!!");
		generalXML.add( "                    </topic>");
		generalXML.add( "                    <topic>");
		generalXML.add( "                        LOL!");
		generalXML.add( "                    </topic>");
		generalXML.add( "                </topics>");
		generalXML.add( "            </post>");
		generalXML.add( "            <post>");
		generalXML.add( "                <body>");
		generalXML.add( "                    Oh...Noo!");
		generalXML.add( "                </body>");
		generalXML.add( "                <topics>");
		generalXML.add( "                    <topic>");
		generalXML.add( "                        UNI!..Stop :(");
		generalXML.add( "                    </topic>");
		generalXML.add( "                </topics>");
		generalXML.add( "            </post>");
		generalXML.add( "        </posts>");
		generalXML.add( "        <followers>");
		generalXML.add( "            <follower>");
		generalXML.add( "                <id>2</id>");
		generalXML.add( "            </follower>");
		generalXML.add( "            <follower>");
		generalXML.add( "                <id>3</id>");
		generalXML.add( "            </follower>");
		generalXML.add( "        </followers>");
		generalXML.add( "    </user>");
		generalXML.add( "</users>");
		
		String line = new String();
		int indentsCounter;
		
		for (int x = 0; x<	generalXML.size();x++) {
			correctIndentsXML.add("x");	
		}

		for (int lineIndex = 0; lineIndex < generalXML.size(); lineIndex++) {
			generalXML.set(lineIndex ,generalXML.get(lineIndex).replaceAll("\\s", ""));

		}


		indentsCounter=0;

		for (int lineIndex = 0; lineIndex < generalXML.size(); lineIndex++) {
			line = generalXML.get(lineIndex);			//get line content

			correctIndentsXML.set(lineIndex, "\t".repeat(indentsCounter)+generalXML.get(lineIndex));
			int repeatNumber=0;

			for ( int i = 0; i < line.length()-1; i++) {
				if(line.charAt(i)=='<') {
					repeatNumber++;
				}
			}
			if (line.contains("/") && repeatNumber <2  ) {
				correctIndentsXML.set(lineIndex, "\t".repeat(indentsCounter-1)+generalXML.get(lineIndex));

			}
			for (int charIndex_1 = 0; charIndex_1 < line.length(); charIndex_1++) { //looping on the line char by char looking for an opening tag

				if(line.charAt(charIndex_1)=='<'&& line.charAt(charIndex_1+1)!='/' ) { // if line has opening tag then;
					indentsCounter++;
				}

				else if (line.charAt(charIndex_1)=='<' && line.charAt(charIndex_1+1)=='/') {
					indentsCounter--;
				}

			}


		}*/
		
		
	}
}