import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class File {
	
	public static ArrayList<String> checkConsistency(ArrayList<String> XML) {
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
									while(line.charAt(i-1) != '>') {
										i++;
									}
									if(line.charAt(i) == '1'||line.charAt(i) == '2'||line.charAt(i) == '3'||line.charAt(i) == '4'||line.charAt(i) == '5'||line.charAt(i) == '6'||line.charAt(i) == '7'||line.charAt(i) == '8'||line.charAt(i) == '9'||line.charAt(i) == '0') {
										String correctLine = " ".repeat(tags.peek().margin) + "<id>" + line.charAt(i) + "</id>";
										XML.set(lineIndex, correctLine);
									}
									else {
										int j = i;
										while(line.charAt(j+1) != '<') {
											j++;
										}
										String name = line.substring(i, j);
										String correctLine = " ".repeat(tags.peek().margin) + "<name>" + name + "</name>";
										XML.set(lineIndex, correctLine);
									}
									mistakes.add("Mismatching tags in line " + (tags.peek().line+1) + "\n");
									tags.pop();
								}
								else if(noClosingTagF == true) {
									tags.pop();
									noOpeningTagF = false;
									while(!noClosingTags.isEmpty()) {
										Tag noClosingTag = noClosingTags.remove();
										String afterLine = XML.get(noClosingTag.line+1);
										String closingTag = "</" + noClosingTag.label + ">";
										int afterLineMargin = 0;
										while(afterLine.charAt(afterLineMargin) == ' ') {
											afterLineMargin++;
										}
										if(noClosingTag.margin >= afterLineMargin) {
											XML.set(noClosingTag.line, XML.get(noClosingTag.line) + closingTag);
										}
										else if(noClosingTag.margin < afterLineMargin){
											int i = 1;
											while(!XML.get(noClosingTag.line+i).equals(" ".repeat(noClosingTag.margin))) {
												i++;
											}
											XML.set(noClosingTag.line+i, XML.get(noClosingTag.line+i) + closingTag);
										}
										mistakes.add("No closing tag for " + noClosingTag.label + " in line " +  (noClosingTag.line+1) + "\n");
										tags.pop();
									}
								}
								else if(noOpeningTagF == true) {
									String myLine = XML.get(lineIndex);
									String beforeLine = XML.get(lineIndex-1);
									int myLineMargin = 0;
									
									while(myLine.charAt(myLineMargin) == ' ') {
										myLineMargin++;
									}
									int beforeLineMargin = 0;
									while(beforeLine.charAt(beforeLineMargin) == ' ') {
										beforeLineMargin++;
									}
									if(myLineMargin >= beforeLineMargin) {
										String margin = myLine.substring(0, myLineMargin);
										String restOfLine = myLine.substring(myLineMargin, myLine.length());
										myLine = margin + "<" + closingTagLabel + ">" + restOfLine;
										XML.set(lineIndex, myLine);
									}
									else if(myLineMargin < beforeLineMargin) {
										int i = lineIndex;
										while(!XML.get(lineIndex-i).equals(" ".repeat(myLineMargin))) {
											i--;
										}
										XML.set(lineIndex-i, XML.get(lineIndex-i) + "<" + closingTagLabel + ">");
									}
									
									mistakes.add("No opening tag for " + closingTagLabel + " in line " +  (lineIndex+1) + "\n");
								}
								
							}
						}
						else {
							String correctLine = XML.get(XML.size()-lineIndex-1);
							correctLine += "<" + closingTagLabel + ">";
							XML.set(XML.size()-lineIndex-1, correctLine);
							mistakes.add("No opening tag for " + closingTagLabel + " in line " +  (lineIndex+1) + "\n");
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
			mistakes.add("No closing tag for" + noClosingTag.label + "\n");
		}
		return mistakes;
		
	}
	
}

class Tag {
	String label;
	int line;
	int margin;
	public Tag(String label, int line, int margin) {
		super();
		this.label = label;
		this.line = line;
		this.margin = margin;
	}
}
