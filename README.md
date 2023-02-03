# Explanation

### Error correction

Error correction Firstly, we created a stack for storing the names(labels) of the tags we come across then we loop over the XML file character by character looking for a tag be it closing or opening by looking for the character '<' and then seeing if it's an opening or closing tag by checking the character after '<' if it's '/' then it's closing else it's opening (ignoring preprocessor tags). If the tag is an opening tag, then we push it into the stack. If the tag is a closing tag, then we compare it to the top of the stack. If it matches, then we pop the tag from the stack as it means that tag is correct with its closing. If it doesn't match, then there is an error either there is no opening tag for the closing one or no closing tag for the opening one inside the stack or those tags are mismatching tags. First, we check if there is no closing tag for the top of the stack by looping over the stack looking for the opening tag of our current closing tag if found then the top of the stack(opening tag) doesn't have a closing tag so we add it. If both our current tag and the tag on top of the stack are in the same line, then it's a mismatching tags issue so we change one of them to match the other. If the problem is neither then our current tag which is a closing one doesn't have an opening, so we add it to the same line of the closing tag. After looping over the whole XML file if there are tags still in our stack this means those opening tags don't have a closing tag, so we add those. All this is done while keeping a list of the errors discovered to give a report for what the program has done.

---

### Prettifying

1. Remove all indents from the xml file.

2. Loop on lines from zero to last line: Staring at line zero.

   2.1. if you find an opening tag increase counter by 1.

   2.2. if you find a closing tag decrease counter by 1.

3. Write the line with the new indentation then go to the next line.
4. Repeat until the last line of XML file.
5. Return the pretty XML file.

---

### Compression

The compression function compresses The XML file which is converted into a string, the input of the compression function is a string and the output is vector of integers this function is used to make the string smaller by deleting the repeating character and keeping the ASCII number in vector of integer.

---

### Decompression

This function is doing exactly the opposite of the Compression function, it is used to get the main or original string back by taking the ASCII form the vector of integer.

---

### Conversion from XML to JSON

- **Is_Array**

this function takes an array list of Tags ( opening tags , closing tags and their content )

this function is defined within the class Tag , it determines whether this opening tag represents an array or not 

it returns a Boolean data type

it checks if the next opening tag (1st opening tag after the opening tag to be checked) is repeated after its closing tag and before the closing tag of the tag that you want to check if it represents an array

if the tag represents an array , the next opening tag (1st opening tag after the opening tag to be checked) is marked as 1st array element and whenever it appears before the closing tag of the tag to be checked , it will be marked as array element

it breaks when the current tag is the closing tag of the tag that you want to check if it represents an array.

* **isObject**

This function takes an array list of Tags ( opening tags , closing tags and their content).

This function is defined within the class Tag , it determines whether this opening tag represents a json object or not.

It returns a Boolean data type.

It checks if there is another opening tag before the closing tag of the tag that you want to check if it represents an object.

- **Convert2JSON**

This function takes an array list of strings , each line in a text file is stored in an entry of this array list we iterate through this array list and classify the strings in it into ( opening tag , closing tag ,both tags or data) we create a new array list of classified tags.

Called: tag_list

And we add all opening tags to a stack.

Then we iterate through tag_list.

###### if the current tag is an opening tag

We define a temp string variable and add to it the conversion of the opening tag from xml to json after the following checks : we check if it is an array and if it is a json object, if it is an array or object we but its name between quotations example: "name": and add this to temp string and according to if it is array or not we specify the type of brackets [ or { and add them to temp string.

If this opening tag is the first array element we add its name , else if it is not first array element but it is an array element, we don't add its name to our temp string the temp string.

Is then added to an arraylist of strings in which each entry in this arraylist represent a line.

###### Else if the current tag is closing tag

We define a temp string variable and add to it the conversion of the closing tag from xml to json after the following checks : we pop the top of opening tag stack and check if it is an array and if it is a json object and according to these checks we specify the type of brackets ] or } and add them to the temp string.

The temp string is then added to an array list of strings in which each entry in this array list represents a line.

###### Else if the current tag is both tags

This means that this line contains opening tag closing tag and the data only , no other internal opening tags.

 we define a temp string variable and add to it the conversion of both tag from xml to json.

This both tag line is represented as a data field, so it is converted as the following example.

"Title(opening tag name)":"data".

the temp string is then added to an array list of strings in which each entry in this array list represents a line.

###### Else if the current tag is data only

we define a temp string variable.

Data is added between quotations to the temp string.

The temp string is then added to an arraylist of strings in which each entry in this arraylist represent a line.

**After the loop over the tag_list ends the function returns an arraylist of strings that contains the conversion of xml file to json format.**

---

### Minifying

We loop over the characters of the XML file while transferring them to a new ArrayList ignoring new lines and spacing (only transferring the characters).

___

### Fill_in_Graph

It takes the array list of user objects as an argument and add each user as a vertex in the graph and add the adjacency list of each user in the graph such that the followers of this user is its adjacency list.

___

## Network analysis

**From XML to User Objects**

- First, we look for the opening tag of a user then we loop over the lines until the closing tag of said user.

- We extract the data of the user by looping over the lines and looking for opening tags of said data for example looking for the opening tag of id field then name field etc.

- We do not add the followers’ field yet but finish looping over the whole XML and add all users to an Array List so that when we add the followers’ field, we add them as an Array List of user objects.

- A user object has 4 members ID(int), Name(String), Posts(objects) and Followers(object).

- The method then returns an Array List of all users in the XML file. 

### Most influencer user (Max_Out_degree)

- Declare max value.

- Loop on all users comparing their Out-degree fields.

- Return user with max degree.

### Most active user (Max_In_degree)

- Declare min value.

- Loop on all users comparing their Out-degree fields.

- Return user with max degree.

 

### Mutual followers

- Select 2 users and get their followers list.

- Compare each follower of the first list with all the second user’s follower.

- Return array of mutual users. 

 

### Suggestions (Followers of Followers)

- First, we look for the specified user and then begin breadth first traversal of the graph but only equal to the number of times of the followers as to reach the 2nd depth which is the followers of the main user’s followers.

- That was done while keeping track of the visited nodes and the added nodes to the queue not to add duplicates.

- Then it is like we took a picture of the queue at a certain time which is then returned as an Array List of Users.

 

### Post Search

- We search for a word in a post or a major topic name.

- Returning the whole post or the topic in an Array list.

- The time complexity is O(n^2).

- The space complexity is O(U\*P\*C).

___

|              Task               | Time Complexity | Space Complexity |
| :-----------------------------: | :-------------: | :--------------: |
|        Error correction         |      O(N)       |       O(m)       |
|           Prettifying           |      O(N)       |       O(N)       |
|           Compression           |      O(N)       |       O(1)       |
|          Decompression          |      O(N)       |       O(1)       |
|   Conversion from XML to JSON   |     O(N*M)      |       O(m)       |
|            Minifying            |      O(N)       |       O(N)       |
| Most influencer  Max_Out_degree |      O(U)       |       O(F)       |
| Most active user  Max_In_degree |      O(U)       |       O(U)       |
|        Mutual followers         |      O(F)       |       O(F)       |
|           User_Search           |      O(U)       |       O(1)       |
|          Fill_in_Graph          |     O(U*F)      |       O(U)       |
|           Post Search           |   O(U\*P\*C)    |       O(U)       |
|      User Objects from XML      |      O(N)       |       O(n)       |
|       Recommend Followers       |      O(U)       |       O(U)       |

- Where N is the number of **characters** in the **XML file**.

- Where m is the number of **tags** in Tag list.

- Where n is the number of characters of **data fields** in the XML file.

* Where U is the number of **users** in **Graph**.

- Where F is the number of **Followers** of the user.
- Where P is the number of **Posts** of each user.
- Where C is the number of **Characters** of each Post. 

___

### Dependencies

Javafx library.
