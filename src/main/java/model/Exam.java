package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Exam implements Cloneable {

    private List<Question> questions;

    public Exam() {
        questions = new ArrayList<>();

    }

    public void add(Question q) {
        questions.add(q);
    }

    public Question get(int index) {
        return questions.get(index);
    }

    public int getSize() {
        return questions.size();
    }

    @Override
    public String toString() {
        return questions + "";
    }

    public void setQuestions(List questions) {

        this.questions = questions;
    }

    public List getQuestions() {

        return questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return Objects.equals(questions, exam.questions);
    }


    public Exam clone() throws CloneNotSupportedException {
        Exam newExam = (Exam) super.clone();
        List newQuestions = new ArrayList<>();

        for (int i = 0; i < this.getSize(); i++) {
            if (getQuestions().get(i) instanceof MultiChoiceQuestion) {
                newQuestions.add(((MultiChoiceQuestion) getQuestions().get(i)).clone());
            } else if (getQuestions().get(i) instanceof OpenQuestion) {
                newQuestions.add(((OpenQuestion) getQuestions().get(i)).clone());
            }

        }

        newExam.setQuestions(newQuestions);
        return newExam;
    }

    public void loadExamQuestionsToFile() throws FileNotFoundException {
        String fileNameExam = "exam_" + new SimpleDateFormat("yyyy_MM_dd_hh_mm'.txt'").format(new Date());
        File examFile = new File(fileNameExam);
        PrintWriter pw = new PrintWriter(examFile);
        for (int i = 0; i < questions.size(); i++) {
            pw.println(i + 1 + ")" +(questions.get(i)).getQuestionText());
            if (questions.get(i) instanceof MultiChoiceQuestion) {
                pw.println("Choose the correct answer:");
                for (int j = 0; j < ((MultiChoiceQuestion) questions.get(i)).getLogicalSize(); j++) {
                    pw.println(j + 1 + ". " + ((MultiChoiceQuestion) questions.get(i)).getAnswerByIndex(j).getAnswer());
                }
            }
            pw.println();
        }
        pw.close();


    }


    public void loadExamSolutionsToFile() throws FileNotFoundException {
        String fileNameSol = "solution_" + new SimpleDateFormat("yyyy_MM_dd_hh_mm'.txt'").format(new Date());
        File solFile = new File(fileNameSol);
        PrintWriter pw = new PrintWriter(solFile);
        for (int i = 0; i < questions.size(); i++) {
            pw.print("Answer(s) to question ");
            pw.println(i + 1 + ":");
            if (questions.get(i) instanceof OpenQuestion) {
                pw.println(((OpenQuestion) questions.get(i)).getAnswer());
            } else if (questions.get(i) instanceof MultiChoiceQuestion) {
                for (int j = 0; j < ((MultiChoiceQuestion) questions.get(i)).getLogicalSize(); j++) {
                    pw.print(j + 1 + ". ");
                    ((MultiChoiceQuestion) questions.get(i)).getAnswerByIndex(j).save(pw);

                }
                pw.println();

            }
        }
        pw.close();
    }
}