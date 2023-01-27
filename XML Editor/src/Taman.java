import java.util.ArrayList;

public class Taman {

	public static ArrayList<String> prettify(ArrayList<String> generalXML) {
		ArrayList<String> correctIndentsXML = new ArrayList<String>();
		
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


		}
		/*System.out.println('\n'+"Elements of correctIndentsXML are:"+'\n');
		for (int lineIndex = 0; lineIndex < correctIndentsXML.size(); lineIndex++) {
			System.out.println(correctIndentsXML.get(lineIndex));
		}*/
		return correctIndentsXML;
	}


}


