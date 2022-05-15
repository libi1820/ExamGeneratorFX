package Listeners;

public interface SystemEventListener {
    void displayQuestionInView(String s);
    void addQuestionToView();
    void updateQuestionInView();
    void updateAnswerInView();
    void deleteAnswerInView();
    void generateManualExamInView();
    void generateAutomaticExamInView();


}
