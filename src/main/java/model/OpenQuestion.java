package model;


import java.util.InputMismatchException;

/**
 * The OpenQuestion class is a sub-class of the Question class. It holds a question text and an answer text.
 */


public class OpenQuestion extends Question implements Cloneable{

    private String answer;

    public OpenQuestion(String text, String answer) {
        super(text);
        this.answer = answer;
    }



    public OpenQuestion(OpenQuestion other) {
        super(other);
        this.answer= other.answer;
    }
    public String getAnswer() {

        return answer;
    }

    public void setAnswer(String answer) {

        this.answer = answer;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "the answer is:" + "\n" + getAnswer();

    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof OpenQuestion)) {
            return false;
        } else return super.equals(other);
    }

    @Override
    public int getAnswersLength() {
        return questionText.length();
    }

    @Override
    public OpenQuestion clone() throws CloneNotSupportedException {
        return (OpenQuestion) super.clone();
    }
}
