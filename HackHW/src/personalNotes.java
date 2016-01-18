import java.util.*;

import java.io.*;


public class personalNotes {

public static String filePath, fileOut;


public static void main(String[] args) throws IOException {


BufferedReader br = new BufferedReader(new FileReader(filePath));

PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileOut)));


String line = null;

do {

line = br.readLine();

ArrayList<Question> lineQuestion = (toQuestion(line));

for (Question oneQuestion : lineQuestion) {

pw.print(oneQuestion.getQuestion());

pw.println();

pw.print(oneQuestion.getAnswer());

pw.println();

}

} while (br.ready());

br.close();

pw.close();


}

public static boolean semanticCompare(String str1,String str2) throws IOException

{

if(WolframCloudCall.call(str1, str2).equalsIgnoreCase("True"))

return true;

return false;

}

/**

* removes the period from the end of the statement

* 

* @param list the list of words in the statement

*/

public static void removePeriod(ArrayList<String> list) {

String end = list.get(list.size() - 1);

if (end.endsWith("."))

end = end.substring(0, end.length() - 1);

list.set(list.size() - 1, end);

}


/**

* parses text into an ArrayList<String>

* 

* @param text the initial text

* @return an ArrayList of words separated by spaces in text

*/

public static ArrayList<String> getStringArr(String text) {

ArrayList<String> newText = new ArrayList<String>();

String[] arr = text.split(" ");

for (String str : arr)

newText.add(str);

removePeriod(newText);

return newText;

}


/**

* sets the file path to read from

* 

* @param path the file path

*/

public static void setPath(String path) {

filePath = path;

}


public static void setOutPath(String path)

    {

            fileOut = path;

    }


/**

* returns whether or not the string is a month name or abbreviation

* 

* @param current a word

* @return true if it is a month, false if not

*/

public static boolean isMonth(String current) {

current = current.toLowerCase();

return current.equals("january") || current.equals("jan.") || current.equals("jan")

|| current.equals("february") || current.equals("feb.") || current.equals("feb")

|| current.equals("march") || current.equals("mar.") || current.equals("mar") || current.equals("june")

|| current.equals("jun.") || current.equals("jun") || current.equals("july") || current.equals("jul.")

|| current.equals("jul") || current.equals("may") || current.equals("may.") || current.equals("august")

|| current.equals("aug") || current.equals("aug.") || current.equals("september")

|| current.equals("sept.") || current.equals("sept") || current.equals("sep.") || current.equals("sep")

|| current.equals("november") || current.equals("nov.") || current.equals("nov")

|| current.equals("october") || current.equals("oct.") || current.equals("oct")

|| current.equals("december") || current.equals("dec.") || current.equals("dec")

|| current.equals("april") || current.equals("apr.") || current.equals("apr");


}


/**

* returns the month the string is

* 

* @param str the string

* @return the full name of the month

*/

public static String getMonth(String str) {

String current = str.toLowerCase();

if (current.equals("january") || current.equals("jan.") || current.equals("jan"))

return "January";

if (current.equals("february") || current.equals("feb.") || current.equals("feb"))

return "February";

if (current.equals("march") || current.equals("mar.") || current.equals("mar"))

return "March";

if (current.equals("june") || current.equals("jun.") || current.equals("jun"))

return "June";

if (current.equals("july") || current.equals("jul.") || current.equals("jul"))

return "July";

if (current.equals("may") || current.equals("may."))

return "May";

if (current.equals("august") || current.equals("aug") || current.equals("aug."))

return "August";

if (current.equals("september") || current.equals("sept.") || current.equals("sept") || current.equals("sep.")

|| current.equals("sep"))

return "September";

if (current.equals("november") || current.equals("nov.") || current.equals("nov"))

return "November";

if (current.equals("october") || current.equals("oct.") || current.equals("oct"))

return "October";

if (current.equals("december") || current.equals("dec.") || current.equals("dec"))

return "December";

if (current.equals("april") || current.equals("apr.") || current.equals("apr"))

return "April";

else

return "NOMI DOES NOT KNOW HIS MONTHS!!!!11!!!!!!2!!!";

}


/**

* returns an array of questions based off a fact

* 

* @param text the fact entered

* @return an array of questions based off of it

*/

public static ArrayList<Question> toQuestion(String text) {

ArrayList<String> newText = getStringArr(text);// the text of the

// statement

ArrayList<String> qText = (ArrayList<String>) (newText.clone());// question

// text

ArrayList<Question> qs = new ArrayList<Question>();// the array of

// questions to be

// returned


String answer = null;// question answer


int imp = -1;// important word index


// go through every word in the statement

for (int i = 0; i < newText.size(); i++) {

//if the word is a month, replace it with when

if (isMonth(newText.get(i))) {

if (i < newText.size() - 1) {

String next = newText.get(i + 1);

//if the next word is a number, include it with the month

if (next.contains("0") || next.contains("1") || next.contains("2") || next.contains("3")

|| next.contains("4") || next.contains("5") || next.contains("6") || next.contains("7")

|| next.contains("8") || next.contains("9")) {

qText.set(i, "when");

answer = getMonth(newText.get(i)) + " " + qText.remove(i + 1);

i++;

} else {

qText.set(i, "when");

answer = getMonth(newText.get(i));

}

}

//if the word is a pronoun, replace it with who with an unknown answer

} else if (newText.get(i).equalsIgnoreCase("he") || newText.get(i).equalsIgnoreCase("she")

|| newText.get(i).equalsIgnoreCase("they") || newText.get(i).equalsIgnoreCase("we")) {

answer = "~";//a tilda as the answer tells the question class that the answer is unknown

qText.set(i, "who");

imp = -1;

//if the word is a posessive pronoun except her

} else if (newText.get(i).equalsIgnoreCase("his") 

|| newText.get(i).equalsIgnoreCase("their")) {

answer = "~";

qText.set(i, "whose");

imp = -1;

//if the current word is coming after a posessive, set it as who/what

} else if (i == imp) {

answer = newText.get(i);

qText.set(i, "who/what");

imp = -1;

}

// if the word is I, set it as who

else if (newText.get(i) == "I") {

qText.set(i, "who");

answer = "I";

}

// if the word is capitalized, set it as who/what

else if (newText.get(i).charAt(0) >= 65 && newText.get(i).charAt(0) <= 90) {


int k = i + 1;

int qIndex = k;

answer = newText.get(i);

qText.set(i, "who/what");

//if following words are capitalized, combine them with the first one

while (k < newText.size() && newText.get(k).charAt(0) >= 65 && newText.get(k).charAt(0) <= 90

&& !newText.get(i).endsWith(",")) {


// add the capital word to the answer and remove it from the

// text

answer += " " + newText.get(k);

qText.remove(qIndex);

k++;

i++;

}

// if there is a possessive on the last proper noun, set the next word as important

if (answer.endsWith("\'s") || answer.endsWith("s\'")) {

imp = i + 1;

}

}

// if there is a number, replace with how many/when

else if (newText.get(i).charAt(0) >= 48 && newText.get(i).charAt(0) <= 57) {


answer = newText.get(i);

qText.set(i, "how many/when");


// if bc/a.d./b.c./ad, then it is for sure when. Also add the suffix to the word.

if (i + 1 < newText.size()

&& (newText.get(i + 1).equalsIgnoreCase("ad")

|| newText.get(i + 1).equalsIgnoreCase("a.d.") || newText.get(i + 1)

.equalsIgnoreCase("bc")

|| newText.get(i + 1).equalsIgnoreCase("b.c.")

|| newText.get(i + 1).equalsIgnoreCase("bce")

|| newText.get(i + 1).equalsIgnoreCase("b.c.e.")

|| newText.get(i + 1).equalsIgnoreCase("ce")

|| newText.get(i + 1).equalsIgnoreCase("c.e."))) {

qText.set(i, "what year");

answer += " " + qText.get(i + 1);

qText.remove(i + 1);

i++;

}


// if it's a non-proper noun with an apostrophe, replace with whose/what's

} else if (newText.get(i).endsWith("\'s") || newText.get(i).endsWith("s\'")) {

answer = newText.get(i);

if (newText.get(i + 1) != null) {

qText.set(i, "whose/what\'s");

} else {

qText.set(i, "whose/what\'s");

}

}

  //if the after apostrophe has not already been set

if (imp == -1) {

//if the word ends with apostrophe, the next one is important

if (newText.get(i).endsWith("\'s") || newText.get(i).endsWith("s\'")) {

imp = i + 1;

}

}

// if the word actually made a question, add it to questions. Reset question and answer.

if (answer != null) {

qs.add(new Question(qText, answer));

answer = null;

qText = (ArrayList<String>) (newText.clone());

}

}

return qs;

}

}