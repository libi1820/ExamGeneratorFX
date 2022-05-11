package model;


/**
 * The MultiChoiceQuestion class is a sub-class of the question class, it manages a list of answers
 * and adds basic functionality such ass adding, deleting and comparing answers.
 */

public class MultiChoiceQuestion extends Question implements Cloneable{
    private GenericSet<MultiChoiceAnswer> answers;

    public MultiChoiceQuestion(String text) {
        super(text);
        this.answers = new GenericSet<>();

    }



    public boolean addAnswer(String text, boolean trueFalse) {
        MultiChoiceAnswer ans = new MultiChoiceAnswer(text, trueFalse);
        answers.add(ans);
        return true;

    }

    public MultiChoiceQuestion(MultiChoiceQuestion other,boolean isAutoExam) {
        super(other);
       this.answers = new GenericSet<>();
       if(!isAutoExam) {
           for (int i = 0; i < other.answers.size(); i++) {
               this.answers.add(other.answers.get(i));
           }
       }

    }

    public int getAnswersLength() {
        int length =0;
        for (int i=0; i<answers.size();i++) {
           length+= answers.get(i).getAnswer().length();
        }
        return length;
    }




    public MultiChoiceAnswer getAnswerById(int choice) {
        return answers.get(choice-1);

    }


    public MultiChoiceAnswer getAnswerByIndex(int index) {
        return answers.get(index);
    }


    public int getLogicalSize() {
        if (answers.size() == 0) {
            System.out.println("there are no answers to this question");
        }
        return answers.size();
    }

    /**
     * @param text
     * @param trueFalse
     * @return
     */


    /**
     * @param answer of question to be compared against.
     * @return boolean value indicating whether a similar answer appears in the answers list.
     */
    public boolean answerExists(String answer) {
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).getAnswer().compareToIgnoreCase(answer) == 0) {

                return true;
            }
        }
        return false;
    }

    /**
     * @param answerNumber
     */
    public void deleteAnswer(int answerNumber)  {
      answers.removeByIndex(answerNumber);

    }

    /**
     * This functions checks how many correct answers there are in the object
     * and by that determines whether there are more than one correct answers or none.
     */
    public void answerIndication() {
        int allTrue = 0, allFalse = 0;
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).getTrueFalse()) {
                allTrue++;
            } else allFalse++;
        }
        if (allFalse == answers.size()) {
            addAnswer("more than one correct answer", false);
            addAnswer("there is no correct answer", true);
        }
        if (allTrue == 1) {
            addAnswer("more than one correct answer", false);
            addAnswer("there is no correct answer", false);
        }
        if (allTrue > 1) {
            for (int i = 0; i < answers.size(); i++) {
                answers.get(i).setTrueFalse(false);
            }
            addAnswer("more than one correct answer", true);
            addAnswer("there is no correct answer", false);
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (answers.get(0) == null) {
            sb.append(super.toString() + "\n" + "there are no answers");
            return sb.toString();
        }
        else {
            sb.append(super.toString() + "\n" + "the answers are: " + "\n");
            for (int i = 0; i < answers.size(); i++) {
              sb.append(i + 1 + ")" + answers.get(i).toString() + "\n");
            }

            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MultiChoiceQuestion)) {
            return false;
        } else return super.equals(other);
    }

    @Override
    public MultiChoiceQuestion clone() throws CloneNotSupportedException {
        MultiChoiceQuestion copyQuestion = (MultiChoiceQuestion) super.clone();
        GenericSet <MultiChoiceAnswer> copyAnswers= new GenericSet<>();
        for(int i=0;i<answers.size();i++){
            copyAnswers.add((answers.get(i)).clone()) ;
        } copyQuestion.answers = copyAnswers;
        return copyQuestion;
    }

}
