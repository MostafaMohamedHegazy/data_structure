import java.util.ArrayList;
import java.util.Stack;

public class File {
	
	public static void checkConsistency(String[] XML) {
		int lineIndex = 0;
		String line;
		Stack<Tag> tags = new Stack<Tag>();
		ArrayList<String> mistakes = new ArrayList<String>();
		
		while(lineIndex < XML.length) {
			line = XML[lineIndex];
			int charIndex = 0;
			
			
			while(charIndex < line.length()-1) {
				if(line.charAt(charIndex) == '<' && line.charAt(charIndex+1) != '?') {
					String label = new String();
					
					if(line.charAt(charIndex+1) == '/') {
						charIndex += 2;
						
						while (line.charAt(charIndex) != '>') {
							label += line.charAt(charIndex);
							charIndex++;
						}
						if(!tags.empty()) {
							
							if (label.equals((tags.peek()).label)) {
								tags.pop();
								System.out.println("pop: " + label);
							}
							else {
								Boolean noOpenTag = true;
								Stack<Tag> tmpTags = new Stack<Tag>();
								tmpTags = tags;
								tmpTags.pop();
								
								while(!tmpTags.empty()) {
									if(label.equals(tmpTags.peek().label)) {
										Tag wrongTag = tags.pop();
										String correctLine = XML[wrongTag.line];
										wrongTag.label = "</" + wrongTag.label + ">";
										correctLine = " ".repeat(wrongTag.margin) + wrongTag.label;
										XML[wrongTag.line] = correctLine;
										mistakes.add("No closing tag for" + wrongTag.label + "\n");
										noOpenTag = false;
									}
									tmpTags.pop();
								}
								if (noOpenTag == true) {
									Tag wrongTag = tags.peek();
									String correctLine = XML[wrongTag.line];
									label = "<" + label;
									correctLine = " ".repeat(wrongTag.margin) + label;
									XML[wrongTag.line] = correctLine;
									mistakes.add("No opening tag for" + label + "\n");
								}
							}
						}
						else {
							String correctLine = XML[0];
							label = "<" + label + ">";
							correctLine = label;
							XML[0] = correctLine;
							mistakes.add("No opening tag for" + label + "\n");
						}
					}
					else {
						charIndex++;
						while(line.charAt(charIndex) != '>') {
							label += line.charAt(charIndex);
							charIndex++;
						}
						tags.push(new Tag(label, lineIndex, charIndex-label.length()-1)) ;
						System.out.println("push: " + tags.peek().label);
					}
				}
				else {
					charIndex++;
				}
			}
			lineIndex++;
		}
		while(!tags.empty()) {
			Tag wrongTag = tags.pop();
			String correctLine = " ".repeat(wrongTag.margin) + "</" + wrongTag.label + ">";
			XML[XML.length-1] += correctLine;
			mistakes.add("No closing tag for" + wrongTag.label + "\n");
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
