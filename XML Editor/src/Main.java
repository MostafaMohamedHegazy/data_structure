
public class Main {
    public static void main(String[] args) {
    	
    	String s[] = new String[39];
        s[0] = "<users>";
        s[1] = "    <user>";
        s[2] = "        <id>1</id>";
        s[3] = "        <name>Ahmed Ali</name>";
        s[4] = "        <posts>";
        s[5] = "            <post>";
        s[6] = "                <body>";
        s[7] = "                    FooK";
        s[8] = "                </body>";
        s[9] = "                <topics>";
        s[10] = "                    <topic>";
        s[11] = "                        UNI...HARD!!";
        s[12] = "                    </topic>";
        s[13] = "                    <topic>";
        s[14] = "                        ";
        s[15] = "                    </topic>";
        s[16] = "                </topics>";
        s[17] = "            </post>";
        s[18] = "             <post>";
        s[19] = "                <body>";
        s[20] = "                    Oh...Noo!";
        s[21] = "                </body>";
        s[22] = "                <topics>";
        s[23] = "                    <topic>";
        s[24] = "                        UNI!..Stop :(";
        s[25] = "                    </topic>";
        s[26] = "                </topics>";
        s[27] = "            </post>";
        s[28] = "        </posts>";
        s[29] = "        <followers>";
        s[30] = "            <follower>";
        s[31] = "                <id>2</id>";
        s[32] = "            </follower>";
        s[33] = "            <follower>";
        s[34] = "                <id>3</id>";
        s[35] = "            </follower>";
        s[36] = "        </followers>";
        s[37] = "    </user>";
        s[38] = "";
        for (int i = 0; i < s.length; i++) {
			System.out.println(s[i]);
		}
    	File.checkConsistency(s);
    	for (int i = 0; i < s.length; i++) {
			System.out.println(s[i]);
		}
    }
}