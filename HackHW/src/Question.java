import java.util.ArrayList;


public class Question {

private ArrayList<String> phrase;

private String answer;

private boolean goodQuestion;//optional feature for later

//constructor sets the phrase and answer

public Question(ArrayList<String> str, String key)

{

phrase = str;

answer = key;

}

// changes the answer to passed String

public void changeAnswer(String newAnswer)

{

answer = newAnswer;

}

//returns the question

public String getQuestion()

{

String wholeQuestion="";

for(String word:phrase)

{

if (word!=null)

{

wholeQuestion+=word+" ";

}

}

wholeQuestion=wholeQuestion.substring(0,wholeQuestion.length()-1);

return wholeQuestion+"?";

}

//returns the answer 

public String getAnswer()

{

return answer;

}

//toString that prints out question AND answer

public String toString()

{

String wholeQuestion="";

for(String word:phrase)

{

if (word!=null)

{

wholeQuestion+=word+" ";

}

}

wholeQuestion=wholeQuestion.substring(0, wholeQuestion.length()-1);

if (answer.equals("~"))

{

wholeQuestion+= "? \n Our software does not recognize the answer "

+ "(please look at your notes if you are not sure)";

}

else

{

wholeQuestion+="? \n The answer is "+ answer;

}

return wholeQuestion;

}

}