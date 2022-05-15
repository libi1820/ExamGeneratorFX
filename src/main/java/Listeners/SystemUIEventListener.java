package Listeners;

public interface SystemUIEventListener {
    void displayQuestionToModel();
    void addQuestionToModel();
    void updateQuestionToModel();
    void updateAnswerToModel();
    void deleteAnswerToModel();
    void generateManualExamToModel();
    void generateAutomaticExamToModel();
    void copyLastExam();

}
