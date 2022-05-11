package model;


import java.io.PrintWriter;
import java.io.Serializable;

/**
 * The MultiChoiceAnswer class represents a multi-choice answer used by the MultiChoiceQuestion class.
 */

public class MultiChoiceAnswer implements Serializable,Cloneable {
    private String answer;
    private boolean trueFalse;
    private static int staticID = 0;
    private int answerId;

    public MultiChoiceAnswer(String answer, boolean trueFalse) {

        this.answer = answer;
        this.trueFalse = trueFalse;
        this.answerId = ++staticID;

    }

    @Override
    protected MultiChoiceAnswer clone() throws CloneNotSupportedException {
        return (MultiChoiceAnswer)super.clone();
    }

    public boolean getTrueFalse() {
        return this.trueFalse;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setTrueFalse(boolean trueFalse) {

        this.trueFalse = trueFalse;
    }

    public String getAnswer() {
        return this.answer;

    }

    public int getAnswerId() {
        return this.answerId;
    }

    @Override
    public String toString() {
        if (this.trueFalse) {
            return answer + ", " + "true.";

        } else return answer + ", " + "false.";

    }


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MultiChoiceAnswer q)) {
            return false;
        }
        return this.answer.compareToIgnoreCase(q.answer) == 0;
    }


    public void save(PrintWriter pw2) {

        pw2.println(this.getAnswer()+", "+this.getTrueFalse());
    }
}
