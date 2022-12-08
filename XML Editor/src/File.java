import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class File {
	
	public static void checkConsistency(ArrayList<String> XML) {
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
								
								if(noClosingTagF == true) {
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
										if(noClosingTag.margin == afterLineMargin || XML.get(noClosingTag.line-1).contains("follower")) {
											XML.set(noClosingTag.line, XML.get(noClosingTag.line) + closingTag);
										}
										else if(noClosingTag.margin < afterLineMargin){
											int i = 1;
											while(!XML.get(noClosingTag.line+i).equals(" ".repeat(noClosingTag.margin))) {
												i++;
											}
											XML.set(noClosingTag.line+i, XML.get(noClosingTag.line+i) + closingTag);
										}
										mistakes.add("No closing tag for" + noClosingTag.label + "\n");
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
									
									mistakes.add("No opening tag for" + closingTagLabel + "\n");
								}
								
							}
						}
						else {
							String correctLine = XML.get(XML.size()-lineIndex-1);
							correctLine += "<" + closingTagLabel + ">";
							XML.set(XML.size()-lineIndex-1, correctLine);
							mistakes.add("No opening tag for" + closingTagLabel + "\n");
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
