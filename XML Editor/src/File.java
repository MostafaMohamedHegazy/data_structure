import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import src.String;

public class File {
	
	public static ArrayList<String> correct(ArrayList<String> XML) {
		int lineIndex = 0;
		String line;
		Stack<Tag> tags = new Stack<Tag>();
		ArrayList<String> mistakes = new ArrayList<String>();
		
		while(lineIndex < XML.size()) {
			line = XML.get(lineIndex);
			int charIndex = 0;
			
			while(charIndex < line.length()) {
				if(line.charAt(charIndex) == '<' && line.charAt(charIndex+1) != '?' && line.charAt(charIndex+1) != '!') {
					String closingTagLabel = new String();
					
					if(line.charAt(charIndex+1) == '/') {
						charIndex += 2;
						
						while (line.charAt(charIndex) != '>') {
							closingTagLabel += line.charAt(charIndex);
							charIndex++;
						}
						
						if(!tags.empty()) {
							if (closingTagLabel.equals((tags.peek()).label)) {
								tags.pop();
							}
							else {
								
								Boolean noOpeningTagF = true;
								Boolean noClosingTagF = false;
								Boolean mismatchingTagsF = false;
								Stack<Tag> tmpTags = new Stack<Tag>();
								tmpTags.addAll(tags);
								Queue<Tag> noClosingTags = new LinkedList<Tag>();
								
								while(!tmpTags.empty()) {
									if(closingTagLabel.equals(tmpTags.peek().label)) {
										noClosingTagF = true;
										break;
									}
									
									noClosingTags.add(tmpTags.pop());
								}
								
								if(tags.peek().line == lineIndex) {
									mismatchingTagsF = true;
									noOpeningTagF = false;
									noClosingTagF = false;
								}
								
								if(mismatchingTagsF == true) {
									
									int i = 1;
									while(line.charAt(i-1) != '<') {
										i++;
									}
									int j = i;
									while(line.charAt(j) != '>') {
										j++;
									}
									String mismatchingTagLabel = line.substring(i,j);
									int k = j;
									while(line.charAt(k) != '<') {
										k++;
									}
									XML.set(lineIndex, line.substring(0,k) + "</" + mismatchingTagLabel + ">");
									mistakes.add("Mismatching tags for <" + (tags.peek().label) + ">" + " in line " + (tags.peek().line+1));
									tags.pop();
								}
								else if(noClosingTagF == true) {
									noOpeningTagF = false;
									while(!noClosingTags.isEmpty()) {
										Tag noClosingTag = noClosingTags.remove();
										String closingTag = "</" + noClosingTag.label + ">";
										XML.set(noClosingTag.line, XML.get(noClosingTag.line) + closingTag);
										mistakes.add("No closing tag for <" + noClosingTag.label + "> in line " +  (noClosingTag.line+1));
										tags.pop();
									}
									tags.pop();
								}
								else if(noOpeningTagF == true) {
									String myLine = XML.get(lineIndex);
									int myLineMargin = 0;
									
									while(myLine.charAt(myLineMargin) == ' ') {
										myLineMargin++;
									}
									
									String margin = myLine.substring(0, myLineMargin);
									String restOfLine = myLine.substring(myLineMargin, myLine.length());
									myLine = margin + "<" + closingTagLabel + ">" + restOfLine;
									XML.set(lineIndex, myLine);
									mistakes.add("No opening tag for </" + closingTagLabel + "> in line " +  (lineIndex+1));
								}
								
							}
						}
						else {
							String myLine = XML.get(0);
							int myLineMargin = 0;
							
							while(myLine.charAt(myLineMargin) == ' ') {
								myLineMargin++;
							}
							
							String margin = myLine.substring(0, myLineMargin);
							String restOfLine = myLine.substring(myLineMargin, myLine.length());
							myLine = margin + "<" + closingTagLabel + ">" + restOfLine;
							XML.set(0, myLine);
							mistakes.add("No opening tag for </" + closingTagLabel + "> in line " +  (lineIndex+1));
						}
					}
					else {
						
						charIndex++;
						while(line.charAt(charIndex) != '>') {
							closingTagLabel += line.charAt(charIndex);
							charIndex++;
						}
						tags.push(new Tag(closingTagLabel, lineIndex, charIndex-closingTagLabel.length()-1)) ;
					}
				}
				else {
					charIndex++;
				}
			}
			lineIndex++;
		}
		
		while(!tags.empty()) {
			Tag noClosingTag = tags.pop();
			String correctLine = " ".repeat(noClosingTag.margin) + "</" + noClosingTag.label + ">";
			XML.add(correctLine);
			mistakes.add("No closing tag for <" + noClosingTag.label + "> " + "in line " + (noClosingTag.line+1));
		}
		return mistakes;
		
	}
	
	
	
	
	
	
	public static ArrayList<String> prettify(ArrayList<String> badIndentsXML) {
		ArrayList<String> noIndentsXML = new ArrayList<String>();
		ArrayList<String> correctIndentsXML = new ArrayList<String>();
		String line = new String();
		int indentsCounter;
		for (int x = 0; x<	badIndentsXML.size();x++) {
			correctIndentsXML.add("x");	
		}

		for (int lineIndex = 0; lineIndex < badIndentsXML.size(); lineIndex++) {
			noIndentsXML.add( badIndentsXML.get(lineIndex).replaceAll("\\s", ""));

		}


		indentsCounter=0;

		for (int lineIndex = 0; lineIndex < noIndentsXML.size(); lineIndex++) {
			line = noIndentsXML.get(lineIndex);			//get line content

			correctIndentsXML.set(lineIndex, "\t".repeat(indentsCounter)+noIndentsXML.get(lineIndex));
			int repeatNumber=0;

			for ( int i = 0; i < line.length()-1; i++) {
				if(line.charAt(i)=='<') {
					repeatNumber++;
				}
			}
			if (line.contains("/") && repeatNumber <2  ) {
				correctIndentsXML.set(lineIndex, "\t".repeat(indentsCounter-1)+noIndentsXML.get(lineIndex));

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
		return correctIndentsXML;
	}


	
}