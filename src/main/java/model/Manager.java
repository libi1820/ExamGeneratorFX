package model;

import java.io.*;
import java.util.*;

/**
 * The manager class communicates directly with the Program class.
 * It manages a questions list and includes the functionality needed to maintain a questions repository and generate exams.
 */
public class Manager implements Serializable {

    private List<Question> questions;
    private Exam currentExam;

    public Manager() {
        questions = new ArrayList();

    }


    public int getSize() {
        return questions.size();
    }

    public Exam getCurrentExam() {
        return currentExam;
    }

    /**
     * @param serial number of the question.
     * @return question matching the passed serial number.
     */
    public Question getQuestionById(int serial) {
        for (Question question : questions) {
            if (question.getSerial() == serial) {
                return question;
            }
        }
        return null;
    }

    public Question getQuestionByIndex(int index) {

        return questions.get(index);
    }

    public boolean isMultiChoiceQuestion(int serial) {
        return getQuestionById(serial) instanceof MultiChoiceQuestion;
    }


    /**
     * @param serial number of the question.
     * @param text   the updated text.
     */
    public void updateQuestion(int serial, String text) {
        getQuestionById(serial).setQuestionText(text);
        System.out.println("The question has been successfully updated.");
    }


    public boolean addOpenQuestion(String questionText, String answerText) {
        OpenQuestion question = new OpenQuestion(questionText, answerText);
        for (Question value : questions) {
            if (value != null && value.equals(question)) {
                return false;
            }
        }

        questions.add(question);
        return true;
    }

    public boolean addMultiQuestion(String questionText) {
        MultiChoiceQuestion question = new MultiChoiceQuestion(questionText);
        for (Question value : questions) {
            if (value != null && value.equals(question)) {
                return false;
            }
        }
        questions.add(question);
        return true;
    }

    /**
     * @param text
     * @return
     */

    public boolean questionTextExists(String text) {
        for (Question question : questions) {
            if (question instanceof MultiChoiceQuestion q) {
                if (q.getQuestionText().equalsIgnoreCase(text))
                    return true;

            } else if (question.getQuestionText().equalsIgnoreCase(text)) {
                return true;

            }
        }
        return false;

    }

    /**
     * Sorts the questions array in lexicographic order using the insertion sort algorithm.
     */
    public void lexicographicSort(Question[] exam) {
        for (int i = 1; i < exam.length; i++) {
            for (int j = i; j > 0 && exam[j - 1].getQuestionText().compareToIgnoreCase(exam[j].getQuestionText()) > 0; j--) {
                if (exam[j - 1].getQuestionText().compareToIgnoreCase(exam[j].getQuestionText()) > 0) {
                    swap(exam, j - 1, j);

                }
            }
        }

    }


    private void swap(Question[] questions, int i, int j) {
        Question temp = questions[i];
        questions[i] = questions[j];
        questions[j] = temp;
    }


    /**
     * Selects a random question.
     *
     * @return a question at a random index in the questions array.
     */
    public Question selectRandomQuestion() {
        int range = this.getSize();
        int randomIndex = (int) (Math.random() * (range));
        return this.getQuestionByIndex(randomIndex);
    }

    /**
     * Selects a random answer.
     *
     * @param q the question from which an answer will be drawn.
     * @return an answer at a random index in the answers array.
     */

    public MultiChoiceAnswer selectRandomAnswer(MultiChoiceQuestion q) {
        int range = q.getLogicalSize();
        int randomIndex = (int) (Math.random() * (range));
        return q.getAnswerByIndex(randomIndex);

    }


    /**
     * @param numOfQuestions
     * @throws NumOfQuestionsOutOfBoundException
     */
    public void checkIfNumOfQuestionsInBound(int numOfQuestions, Manager repository) throws NumOfQuestionsOutOfBoundException {
        if (numOfQuestions > repository.getSize() || numOfQuestions <= 0) {
            throw new NumOfQuestionsOutOfBoundException();

        }
    }

    public boolean containsQuestion(List<Question>qa, Question q) {
        if (qa.size() == 0) {
            return false;
        }
        for (int i = 0; i < qa.size(); i++) {
            if (qa.get(i) != null && qa.get(i).getQuestionText().equals(q.getQuestionText())) {
                return true;
            }

        }
        return false;
    }

    /**
     * @param numOfQuestions the number of questions in the exam.
     * @param repository     the repository from which the random questions are chosen.
     * @return the generated exam.
     */
    public void generateExam(int numOfQuestions, Manager repository) throws NumOfQuestionsOutOfBoundException, FileNotFoundException {
        Comparator<Question> c = new CompareByAnswerLength();
        currentExam = new Exam();

        checkIfNumOfQuestionsInBound(numOfQuestions, repository);
        for (int i = 0; i < numOfQuestions; i++) {
            Question q = repository.selectRandomQuestion();
            while (containsQuestion(currentExam.getQuestions(), q)) {
                q = repository.selectRandomQuestion();
            }
            if (q instanceof OpenQuestion) {

                currentExam.add(new OpenQuestion((OpenQuestion) q));
            } else {

                MultiChoiceQuestion question = (MultiChoiceQuestion) q;
                // variable to store the number of random answers to pick
                int numOfIterations, numOfTrueAnswers = 0;
                for (int j = 0; j < question.getLogicalSize(); j++) {
                    if (question.getAnswerByIndex(j).getTrueFalse()) {
                        numOfTrueAnswers++;
                    }
                }
                numOfIterations = question.getLogicalSize() - numOfTrueAnswers + 1;

                if (numOfIterations > 4) {
                    numOfIterations = 4;
                }
                boolean autoExam = true;
                currentExam.add(new MultiChoiceQuestion(question, autoExam));
                numOfTrueAnswers = 0;
                for (int j = 0; j < numOfIterations; j++) {
                    MultiChoiceAnswer answer = (repository.selectRandomAnswer(question));
                    while (((MultiChoiceQuestion) currentExam.get(i)).answerExists(answer.getAnswer()) || ((numOfTrueAnswers == 1) && answer.getTrueFalse())) {
                        answer = (repository.selectRandomAnswer(question));
                    }
                    if (answer.getTrueFalse()) {
                        numOfTrueAnswers++;
                    }

                    ((MultiChoiceQuestion) currentExam.get(i)).addAnswer(answer.getAnswer(), answer.getTrueFalse());

                }
                ((MultiChoiceQuestion) currentExam.get(i)).answerIndication();

            }

        }

        Collections.sort(currentExam.getQuestions(), c);
        currentExam.loadExamQuestionsToFile();
        currentExam.loadExamSolutionsToFile();

        System.out.println(currentExam.getQuestions());
    }


    public void createExam(List<Integer> examQuestionsId, int numOfQuestions) {
        this.currentExam = new Exam();
        for (int i = 0; i < numOfQuestions; i++) {
            int id = examQuestionsId.get(i);
            if (getQuestionById(id) instanceof MultiChoiceQuestion) {
                boolean autoExam = false;
                MultiChoiceQuestion originalQuestion = (MultiChoiceQuestion) getQuestionById(id);
                this.currentExam.add(new MultiChoiceQuestion(originalQuestion, autoExam));
                for (int j = 0; j < (originalQuestion.getLogicalSize()); j++) {
                    ((MultiChoiceQuestion) this.currentExam.get(i)).addAnswer(originalQuestion.getAnswerByIndex(j).getAnswer(), originalQuestion.getAnswerByIndex(j).getTrueFalse());
                }
            } else if (getQuestionById(id) instanceof OpenQuestion) {
                OpenQuestion originalQuestion = (OpenQuestion) getQuestionById(id);
                this.currentExam.add(new OpenQuestion(originalQuestion));
            }
        }

    }

    public void pickAnswersExam(Question question, List<Integer> answersByIndex) {
        MultiChoiceQuestion castQuestion = (MultiChoiceQuestion) question;
        boolean answerExist;
        for (int i = 0; i < castQuestion.getLogicalSize(); i++) {
            answerExist = false;
            for (int j = 0; j < answersByIndex.size() && !answerExist; j++) {
                if (castQuestion.getAnswerByIndex(i).getAnswerId() == (answersByIndex.get(j))) {
                    answerExist = true;
                }
            }
            if (!answerExist) {
                castQuestion.deleteAnswer(i);
                i--;
            }
        }
        castQuestion.answerIndication();

    }

    public void printManualExam() throws FileNotFoundException {
        Comparator<Question> c = new CompareByAnswerLength();
        Collections.sort(currentExam.getQuestions(), c);
        currentExam.loadExamQuestionsToFile();
        currentExam.loadExamSolutionsToFile();
        System.out.println(currentExam.getQuestions());
    }


    public void loadToBinaryFile() throws IOException {
        ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("questions.dat"));
        outFile.writeObject(questions);
        outFile.close();

    }


    public void updateStaticSerialNumber() {
        Question.setStaticSerial(this.getSize());


    }


    public void loadFromBinaryFile() throws IOException, ClassNotFoundException {
        ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("questions.dat"));
        this.questions = (List<Question>) inFile.readObject();
        inFile.close();

    }



    @Override
    public String toString() {
        if (questions.size() == 0) {
            return "There are no questions in the repository.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n" + "There are " + questions.size() + " questions: \n");
        for (int i = 0; i < questions.size(); i++) {
            sb.append(questions.get(i).toString() + "\n");
            sb.append("\n");

        }
        return sb.toString();
    }

    /**
     * @param o the object compared to each one of the questions in the repository.
     * @return a boolean indicating whether the passed object equals to one of the answers in the repository.
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Question)) {
            return false;
        }
        for (Question question : this.questions) {
            if (question.getQuestionText().equals(((Question) o).getQuestionText())) {
                return true;
            }
        }
        return false;
    }
    public Question getQuestionExam(int index){
        return (Question) currentExam.getQuestions().get(index);
    }

    public boolean addAnswer(String text, boolean indicator) {
        return ((MultiChoiceQuestion) questions.get(getSize() - 1)).addAnswer(text, indicator);
    }

    public void updateOpenQuestionAnswer(int serial, String text) {
        ((OpenQuestion) getQuestionById(serial)).setAnswer(text);
    }

    public void updateMultiChoiceAnswer(int serial, int answerNum, String text) {
        ((MultiChoiceQuestion) getQuestionById(serial)).getAnswerById(answerNum).setAnswer(text);
    }

    public void setAnswerTrueFalse(int serial, int answerNum, boolean indicator) {
        ((MultiChoiceQuestion) getQuestionById(serial)).getAnswerById(answerNum).setTrueFalse(indicator);
    }

    public void deleteAnswer(int serial, int answerNum) {
        ((MultiChoiceQuestion) getQuestionById(serial)).deleteAnswer(answerNum);

    }

    public int getNumOfAnswers(int serial) {
        return ((MultiChoiceQuestion) getQuestionById(serial)).getLogicalSize();

    }

    public boolean answerExists(int serial, String answer) {
        return ((MultiChoiceQuestion) getQuestionById(serial)).answerExists(answer);
    }

public void cloneExam() throws CloneNotSupportedException {
    Exam copyExam= this.currentExam.clone();
    System.out.println(copyExam.toString());

}


}
