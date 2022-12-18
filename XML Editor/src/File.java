import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class File {

	public static ArrayList<String> correct(ArrayList<String> XML) {
		int lineIndex = 0;
		String line;
		Stack<Tag> tags = new Stack<Tag>();
		ArrayList<String> mistakes = new ArrayList<String>();

		while (lineIndex < XML.size()) {
			line = XML.get(lineIndex);
			int charIndex = 0;

			while (charIndex < line.length()) {
				if (line.charAt(charIndex) == '<' && line.charAt(charIndex + 1) != '?'
						&& line.charAt(charIndex + 1) != '!') {
					String closingTagLabel = new String();

					if (line.charAt(charIndex + 1) == '/') {
						charIndex += 2;

						while (line.charAt(charIndex) != '>') {
							closingTagLabel += line.charAt(charIndex);
							charIndex++;
						}

						if (!tags.empty()) {
							if (closingTagLabel.equals((tags.peek()).label)) {
								tags.pop();
							} else {

								Boolean noOpeningTagF = true;
								Boolean noClosingTagF = false;
								Boolean mismatchingTagsF = false;
								Stack<Tag> tmpTags = new Stack<Tag>();
								tmpTags.addAll(tags);
								Queue<Tag> noClosingTags = new LinkedList<Tag>();

								while (!tmpTags.empty()) {
									if (closingTagLabel.equals(tmpTags.peek().label)) {
										noClosingTagF = true;
										break;
									}

									noClosingTags.add(tmpTags.pop());
								}

								if (tags.peek().line == lineIndex) {
									mismatchingTagsF = true;
									noOpeningTagF = false;
									noClosingTagF = false;
								}

								if (mismatchingTagsF == true) {

									int i = 1;
									while (line.charAt(i - 1) != '<') {
										i++;
									}
									int j = i;
									while (line.charAt(j) != '>') {
										j++;
									}
									String mismatchingTagLabel = line.substring(i, j);
									int k = j;
									while (line.charAt(k) != '<') {
										k++;
									}
									XML.set(lineIndex, line.substring(0, k) + "</" + mismatchingTagLabel + ">");
									mistakes.add("Mismatching tags for <" + (tags.peek().label) + ">" + " in line "
											+ (tags.peek().line + 1));
									tags.pop();
								} else if (noClosingTagF == true) {
									noOpeningTagF = false;
									while (!noClosingTags.isEmpty()) {
										Tag noClosingTag = noClosingTags.remove();
										String closingTag = "</" + noClosingTag.label + ">";
										XML.set(noClosingTag.line, XML.get(noClosingTag.line) + closingTag);
										mistakes.add("No closing tag for <" + noClosingTag.label + "> in line "
												+ (noClosingTag.line + 1));
										tags.pop();
									}
									tags.pop();
								} else if (noOpeningTagF == true) {
									String myLine = XML.get(lineIndex);
									int myLineMargin = 0;

									while (myLine.charAt(myLineMargin) == ' ') {
										myLineMargin++;
									}

									String margin = myLine.substring(0, myLineMargin);
									String restOfLine = myLine.substring(myLineMargin, myLine.length());
									myLine = margin + "<" + closingTagLabel + ">" + restOfLine;
									XML.set(lineIndex, myLine);
									mistakes.add(
											"No opening tag for </" + closingTagLabel + "> in line " + (lineIndex + 1));
								}

							}
						} else {
							String myLine = XML.get(0);
							int myLineMargin = 0;

							while (myLine.charAt(myLineMargin) == ' ') {
								myLineMargin++;
							}

							String margin = myLine.substring(0, myLineMargin);
							String restOfLine = myLine.substring(myLineMargin, myLine.length());
							myLine = margin + "<" + closingTagLabel + ">" + restOfLine;
							XML.set(0, myLine);
							mistakes.add("No opening tag for </" + closingTagLabel + "> in line " + (lineIndex + 1));
						}
					} else {

						charIndex++;
						while (line.charAt(charIndex) != '>') {
							closingTagLabel += line.charAt(charIndex);
							charIndex++;
						}
						tags.push(new Tag(closingTagLabel, lineIndex, charIndex - closingTagLabel.length() - 1));
					}
				} else {
					charIndex++;
				}
			}
			lineIndex++;
		}

		while (!tags.empty()) {
			Tag noClosingTag = tags.pop();
			String correctLine = " ".repeat(noClosingTag.margin) + "</" + noClosingTag.label + ">";
			XML.add(correctLine);
			mistakes.add("No closing tag for <" + noClosingTag.label + "> " + "in line " + (noClosingTag.line + 1));
		}
		return mistakes;

	}

	public static ArrayList<String> prettify(ArrayList<String> badIndentsXML) {
		ArrayList<String> noIndentsXML = new ArrayList<String>();
		ArrayList<String> correctIndentsXML = new ArrayList<String>();
		String line = new String();
		int indentsCounter;
		for (int x = 0; x < badIndentsXML.size(); x++) {
			correctIndentsXML.add("x");
		}

		for (int lineIndex = 0; lineIndex < badIndentsXML.size(); lineIndex++) {
			noIndentsXML.add(badIndentsXML.get(lineIndex).replaceAll("\\s", ""));

		}

		indentsCounter = 0;

		for (int lineIndex = 0; lineIndex < noIndentsXML.size(); lineIndex++) {
			line = noIndentsXML.get(lineIndex);

			correctIndentsXML.set(lineIndex, "\t".repeat(indentsCounter) + noIndentsXML.get(lineIndex));
			int repeatNumber = 0;

			for (int i = 0; i < line.length() - 1; i++) {
				if (line.charAt(i) == '<') {
					repeatNumber++;
				}
			}
			if (line.contains("/") && repeatNumber < 2) {
				correctIndentsXML.set(lineIndex, "\t".repeat(indentsCounter - 1) + noIndentsXML.get(lineIndex));

			}
			for (int charIndex_1 = 0; charIndex_1 < line.length(); charIndex_1++) {

				if (line.charAt(charIndex_1) == '<' && line.charAt(charIndex_1 + 1) != '/') {
					indentsCounter++;
				}

				else if (line.charAt(charIndex_1) == '<' && line.charAt(charIndex_1 + 1) == '/') {
					indentsCounter--;
				}

			}

		}
		return correctIndentsXML;
	}

	public static ArrayList<String> Convert2JSON(ArrayList<String> a) {
		Stack<Tag> tag_s = new Stack<Tag>();
		Stack<Tag> temp_stack = new Stack<Tag>();
		Queue<Tag> tag_q = new LinkedList<Tag>();
		ArrayList<Tag> tag_list = new ArrayList<Tag>();
		ArrayList<Tag> openingtag_list = new ArrayList<Tag>();
		ArrayList<String> b = new ArrayList<String>();
		boolean openingtag = false, closingtag = false, bothtags = false;
		int j = 1;
		for (int i = 1; i < a.size() + 1; i++) {
			for (j = 1; j < a.get(i - 1).length() + 1; j++) {
				if (a.get(i - 1).charAt(j - 1) == '<' && a.get(i - 1).charAt(j) != '/') {
					if (a.get(i - 1).contains("</")) {
						bothtags = true;
						openingtag = false;
						closingtag = false;
						tag_list.add(new Tag(a.get(i - 1), (i - 1), j - 1, "both tags"));
						tag_q.add(new Tag(a.get(i - 1), (i - 1), j - 1, "both tags"));
						break;

					} else {
						openingtag = true;
						closingtag = false;
						bothtags = false;
						tag_list.add(new Tag(a.get(i - 1), (i - 1), j - 1, "opening tag"));
						openingtag_list.add(new Tag(a.get(i - 1), (i - 1), j - 1, "opening tag"));
						break;
					}
				}
			}
			if (a.get((i - 1)).contains("</") && (!openingtag && !bothtags)) {
				int k;
				for (k = 1; k < a.get((i - 1)).length(); k++) {
					if (a.get((i - 1)).charAt(k - 1) == '<' && a.get((i - 1)).charAt(k) == '/') {
						break;
					}
				}
				closingtag = true;
				openingtag = false;
				bothtags = false;
				tag_list.add(new Tag(a.get((i - 1)), (i - 1), k - 1, "closing tag"));
				tag_q.add(new Tag(a.get((i - 1)), (i - 1), k - 1, "closing tag"));
			}
			if ((!closingtag && !openingtag && !bothtags)) {
				closingtag = false;
				openingtag = false;
				bothtags = false;
				tag_list.add(new Tag(a.get((i - 1)), (i - 1), 0, "data"));
				tag_q.add(new Tag(a.get((i - 1)), (i - 1), 0, "data"));

			}
			openingtag = false;
			closingtag = false;
			bothtags = false;
		}
		for (int m = 0; m < tag_list.size(); m++) {
			tag_list.get(m).isArray(tag_list);
			tag_list.get(m).isObject(tag_list);
		}

		String stemp = "";
		b.add("{\n");
		String compare_margins = "";

		for (int m = 0; m < tag_list.size(); m++) {

			if (tag_list.get(m).tag_type == "opening tag") {

				if (!tag_s.isEmpty()) {
					if (tag_s.peek().margin > tag_list.get(m).margin) {
						compare_margins = "lower";
					}
					if (tag_s.peek().margin < tag_list.get(m).margin) {
						compare_margins = "higher";
					}
					if (tag_s.peek().margin == tag_list.get(m).margin) {
						compare_margins = "equal";
					}
				}
				temp_stack.push(tag_list.get(m));

				tag_s.push(tag_list.get(m));
				String st0 = "\t";
				for (int t = 0; t < tag_list.get(m).label.length(); t++) {
					if (tag_list.get(m).label.charAt(t) == '<') {
						stemp = "\t";
						st0 = st0 + "\"";

					} else if (tag_list.get(m).label.charAt(t) == '>') {

						if (tag_list.get(m).is_json_Object && !tag_list.get(m).is_Array_element) {

							st0 = st0 + "\":{";
						} else if (tag_list.get(m).is_json_Object && tag_list.get(m).is_Array_element) {

							if (tag_list.get(m).is_1stArray_element) {

								st0 = st0 + "\":[ {";
							} else if (!tag_list.get(m).is_1stArray_element) {

								st0 = stemp + "{";
							}
						} else if (!tag_list.get(m).is_json_Object && !tag_list.get(m).is_Array_element) {
							st0 = st0 + "\": ";
						} else if (!tag_list.get(m).is_json_Object && tag_list.get(m).is_Array_element) {

							if (tag_list.get(m).is_1stArray_element) {

								st0 = st0 + "\":[ ";
							} else if (!tag_list.get(m).is_1stArray_element) {
								st0 = "";
							}

						}

						break;
					} else {
						if (!(tag_list.get(m).is_Array_element && !tag_list.get(m).is_1stArray_element)) {
							stemp = stemp + "\t";
							st0 = st0 + tag_list.get(m).label.charAt(t);
						}
					}
				}
				b.add(st0 + "\n");
			}

			if (tag_list.get(m).tag_type == "closing tag") {
				Tag poped_tag2;
				Tag aux_tag = tag_list.get(m);
				poped_tag2 = temp_stack.pop();

				String st0 = "\t";
				for (int t = 0; t < tag_list.get(m).label.length(); t++) {
					if (tag_list.get(m).label.charAt(t) == '<') {
						if (poped_tag2.is_array) {
							st0 = st0 + "] }";

						} else {
							if (poped_tag2.is_json_Object) {
								st0 = st0 + "}";

							}
							if (!poped_tag2.is_json_Object) {
								st0 = st0 + "";
							}
						}

						if (m < tag_list.size() - 1) {
							if (compare_margins == "equal" || tag_list.get(m + 1).margin == aux_tag.margin) {
								st0 = st0 + ",";
							}
						}
						break;
					}
					st0 = st0 + " ";
				}
				b.add(st0 + "\n");
			}

			if (tag_list.get(m).tag_type == "both tags") {

				String st0 = "\t";
				int count = 0;
				for (int t = 0; t < tag_list.get(m).label.length(); t++) {
					if (tag_list.get(m).label.charAt(t) == '<') {
						count++;
						if (count <= 2) {
							st0 = st0 + "\"";
						}

						else {
							st0 = st0 + "\"";
							if (compare_margins == "equal" || tag_list.get(m + 1).margin == tag_list.get(m).margin) {
								st0 = st0 + ",";
							}
							break;
						}
					} else if (tag_list.get(m).label.charAt(t) == '>') {
						count++;
						if (count <= 2) {
							st0 = st0 + "\":\"";
						} else
							break;
					} else {
						st0 = st0 + tag_list.get(m).label.charAt(t);
					}
				}
				b.add(st0 + "\n");
			}

			if (tag_list.get(m).tag_type == "data") {
				String st0 = "\t";
				int count = 0;
				for (int t = 0; t < tag_list.get(m).label.length(); t++) {
					if (tag_list.get(m).label.charAt(t) != ' ') {
						count++;
						if (count <= 1) {
							st0 = st0 + "\"";
						}
					}
					st0 = st0 + tag_list.get(m).label.charAt(t);

				}
				st0 = st0 + "\"";
				b.add(st0 + "\n");
			}

		}
		b.add("}\n");
		return b;
	}

}