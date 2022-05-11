package model;

import java.util.Comparator;

public class CompareByAnswerLength implements Comparator<Question> {
    @Override
    public int compare(Question o1, Question o2) {
        if (o1.getAnswersLength() > o2.getAnswersLength()) {
            return 1;
        }
        else if (o1.getAnswersLength() < o2.getAnswersLength()) {
            return -1;
        }
        else return 0;
    }


}
