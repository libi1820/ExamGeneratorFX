package model;


import java.io.Serializable;

/**
 * The Question class is the parent class for MultiChoiceQuestion and OpenQuestion sub-classes.
 * It provides basic functionality such as generating a serial number for each created object,
 * and setting a question text.
 */

public abstract class Question implements Serializable , Cloneable{

    protected static int staticSerial = 0;
    protected int serial;
    protected String questionText;


    public Question(String questionText) {
        this.questionText = questionText;
        this.serial = ++staticSerial;
    }


    public Question(Question other) {
        this.questionText = other.getQuestionText();
        this.serial = other.getSerial();
    }

    @Override
    public Question clone() throws CloneNotSupportedException {
        return (Question)super.clone();
    }

    public void setQuestionText(String questionText) {

        this.questionText = questionText;
    }


    protected static void setStaticSerial(int serialNumber) {
        Question.staticSerial = serialNumber;
    }

    public int getSerial() {
        return serial;
    }

    public String getQuestionText() {

        return questionText;
    }


    @Override
    public String toString() {
        return "Question number " + getSerial() + ":" +
                "\n" + this.questionText;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Question)) {
            return false;
        }
        Question q = (Question) other;
        if (q.questionText != this.questionText) {
            return false;
        }
        return true;
    }


public abstract int getAnswersLength();



}

