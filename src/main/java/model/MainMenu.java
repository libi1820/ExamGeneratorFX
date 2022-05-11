package model;

import java.io.FileNotFoundException;
import java.util.Scanner;


public interface MainMenu {

    void displayQuestions(Manager repository);

    void addQuestion(Manager repository, Scanner s);

    void updateQuestion(Manager repository, Scanner s);

    boolean updateAnswer(Manager repository, Scanner s);

    boolean deleteAnswer(Manager repository, Scanner s);

    void generateManualExam(Manager repository, Scanner s);

    void generateAutomaticExam(Manager repository, Scanner s);

    void copyExamToNewFile(Manager repository, Scanner s)throws CloneNotSupportedException;
}
