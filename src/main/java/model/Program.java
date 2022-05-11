package model;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The program class is the main class which handles user input and communicates directly with
 * the QuestionsRepository class and uses its functionality to generate exams.
 *
 * @author Alon Kigelman, Libi Alkalay
 */

public class Program implements MainMenu {

    public static void main(String[] args) throws CloneNotSupportedException {
        Manager repository = new Manager();
        Program program = new Program();


        try {
            repository.loadFromBinaryFile();
            repository.updateStaticSerialNumber();

        } catch (IOException e) {
            System.out.println();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner s = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            printMenu();
            boolean isValidInput = false;
            int option = -1;
            while (!isValidInput) {
                try {
                    option = s.nextInt();
                    if (option < 1 || option > 9) {
                        System.err.println("Invalid input, please enter a number between 1-9");
                    }
                    isValidInput = true;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.");
                    s.nextLine();
                }
            }

            switch (option) {
                case 1: {
                    program.displayQuestions(repository);
                }
                break;
                case 2: {
                    program.addQuestion(repository, s);

                }
                break;

                case 3: {
                    program.updateQuestion(repository, s);


                }
                break;

                case 4: {
                    program.updateAnswer(repository, s);

                }
                break;

                case 5: {
                    program.deleteAnswer(repository, s);

                }
                break;

                case 6: {
                    program.generateManualExam(repository, s);

                }
                break;
                case 7: {
                    program.generateAutomaticExam(repository, s);
                }
                break;
                case 8: {
                    program.copyExamToNewFile(repository, s);

                }
                case 9: {
                    try {
                        repository.loadToBinaryFile();
                        System.out.println("questions.dat has been updated.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("GoodBye!");
                    isRunning = false;
                }
                break;
            }

        }
    }


    public static void printMenu() {
        System.out.println("Please select an option:\r\n" + "1-Display all questions" +
                " and answers in the repository \r\n"
                + "2-Add a new question\r\n" + "3-Update a question's text\r\n"
                + "4-Update an answer's text\r\n" + "5-Delete an answer to a question \r\n"
                + "6-Generate an exam manually \r\n"
                + "7-Gene" +
                "" +
                "rate an exam automatically \r\n" + "8-Copy an existing exam to a new file. \r\n"
                + "9-exit. \r\n");

    }


    public static int checkIfValidSerial(Manager repository) throws InvalidSerialNumException {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter a question number:");
        int serial = s.nextInt();
        if (repository.getQuestionById(serial) == null) {
            throw new InvalidSerialNumException();
        }
        s.nextLine();
        return serial;
    }

    public static int checkIfValidAnswer(Manager repository, int serial) throws InvalidAnswerNumException {
        Scanner s = new Scanner(System.in);
        int answerNum = s.nextInt();
        if (answerNum < 1 || answerNum > repository.getNumOfAnswers(serial)) {
            throw new InvalidAnswerNumException();
        }
        s.nextLine();
        return answerNum;
    }


    @Override
    public void displayQuestions(Manager repository) {
        System.out.println(repository);

    }

    @Override
    public void addQuestion(Manager repository, Scanner s) {
        boolean isValid = false;
        System.out.println("Enter the question text:" + "\n");
        s.nextLine();
        String questionText = s.nextLine();
        int choice = 0;
        while (!isValid) {
            try {
                System.out.println("Choose the type of question:" + "\n"
                        + ("(1)multiple choice question" + "\n" + "(2)open-ended question"));
                choice = s.nextInt();
                if (choice != 1 && choice != 2) {
                    System.err.println("invalid input. Try again.");
                } else isValid = true;
            } catch (Exception e) {
                System.err.println("Invalid input. Try again.");
                s.nextLine();
            }
        }

        if (choice == 1) {
            repository.addMultiQuestion(questionText);
            int numOfInputs = 0;
            isValid = false;
            while (!isValid) {
                try {
                    System.out.println("how many answers do you want for this question?");
                    numOfInputs = s.nextInt();
                    if (numOfInputs < 2 || numOfInputs > 8) {
                        System.out.println("Invalid input, please enter a number between 2-8");
                    } else isValid = true;
                } catch (Exception e) {
                    System.err.println("invalid input, try again.");
                    s.nextLine();
                }
            }

            for (int i = 0; i < numOfInputs; i++) {
                System.out.println("Enter an answer followed by true or false.");
                s.nextLine();
                String input = s.nextLine();
                isValid = false;
                boolean indicator;
                while (!isValid) {
                    try {
                        indicator = s.nextBoolean();
                        if (!repository.addAnswer(input, indicator)) {
                            System.out.println("Enter an answer followed by true or false indicator again.");
                            s.nextLine();
                            input = s.nextLine();
                        } else isValid = true;
                    } catch (Exception e) {
                        System.err.println("Invalid input, please enter true or false indicator again ");
                        s.nextLine();
                    }
                }
                System.out.println("The answer has been added successfully.");

            }

        } else {
            System.out.println("Please enter an answer:");
            s.nextLine();
            String answer = s.nextLine();


            if (!repository.addOpenQuestion(questionText, answer)) {
                System.err.println("this question already exists in the repository");
            } else System.out.println("The question has been added successfully.");
        }

    }

    @Override
    public void updateQuestion(Manager repository, Scanner s) {
        System.out.println("Which question do you want to update?");
        boolean isValid = false;
        int serial = 0;
        while (!isValid) {
            try {
                serial = checkIfValidSerial(repository);
                isValid = true;
            } catch (Exception e) {
                System.err.println("Invalid input. Please try again.");

            }
        }
        s.nextLine();
        System.out.println("Enter the updated text:");
        String updatedText = s.nextLine();
        if (repository.questionTextExists(updatedText)) {
            System.err.println("This question text already exists.");

        } else {
            repository.updateQuestion(serial, updatedText);
        }

    }

    @Override
    public boolean updateAnswer(Manager repository, Scanner s) {
        boolean isValid = false;
        int serial = 0;
        while (!isValid) {
            try {
                serial = checkIfValidSerial(repository);
                isValid = true;
            } catch (Exception e) {
                System.err.println("Invalid input. Try again.");

            }

        }
        if (repository.isMultiChoiceQuestion(serial)) {
            isValid = false;
            int answerNum = 0;
            System.out.println("please choose answer number:" + "\n"
                    + repository.getQuestionById(serial).toString());
            while (!isValid) {
                try {
                    answerNum = checkIfValidAnswer(repository, serial);
                    isValid = true;
                } catch (Exception e) {
                    System.err.println("Invalid input. Please try again.");

                }
            }
            System.out.println("what is the updated answer and is it true or false");
            s.nextLine();
            String updated = s.nextLine();
            isValid = false;
            boolean indicator = false;
            while (!isValid) {
                try {
                    indicator = s.nextBoolean();
                    isValid = true;
                } catch (Exception e) {
                    System.err.println("Invalid input, please enter true or false indicator again ");
                    s.nextLine();
                }
            }
            if (repository.answerExists(serial, updated)) {
                System.out.println("This answer already exists for this question.");
                return false;

            } else {

                repository.updateMultiChoiceAnswer(serial, answerNum, updated);
                repository.setAnswerTrueFalse(serial, answerNum, indicator);
                System.out.println("The answer has been updated.");
                return true;
            }

        }
        else {
            System.out.println("what is the updated answer?");
            s.nextLine();
            String updated = s.nextLine();
            repository.updateOpenQuestionAnswer(serial, updated);
            System.out.println("The answer has been updated.");
            return true;
        }



    }

    @Override
    public boolean deleteAnswer(Manager repository, Scanner s) {
        boolean isValid = false;
        int serial = 0;
        while (!isValid) {
            try {
                serial = checkIfValidSerial(repository);
                isValid = true;
            } catch (Exception e) {
                System.err.println("Invalid input.Please Try again.");

            }
        }
        if (!(repository.isMultiChoiceQuestion(serial))) {
            System.err.println("There is no option to delete the answer for an open question.");
            return false;

        } else {
            int answerNum = 0;
            if (repository.getNumOfAnswers(serial) <= 2) {
                System.err.println("There is no option to delete because there are less than 3 answers.");
                return false;
            }
            {
                isValid = false;
                System.out.println("please choose answer number:" + "\n"
                        + repository.getQuestionById(serial).toString());
                while (!isValid) {
                    try {
                        answerNum = checkIfValidAnswer(repository, serial);
                        isValid = true;
                    } catch (Exception e) {
                        System.err.println("Invalid input. Try again");

                    }
                }
            }
try {
    repository.deleteAnswer(serial, answerNum - 1);
}
catch (Exception e) {
    e.getMessage();
}
            System.out.println("The answer has been deleted successfully.");
return true;
        }

    }

    @Override
    public void generateManualExam(Manager repository, Scanner s) {
        boolean isValidInput = false;
        int numOfQuestions = 0;
        while (!isValidInput) {
            try {
                System.out.println("How many questions do you want in the exam? ");
                numOfQuestions = s.nextInt();
                if (numOfQuestions < 1 || numOfQuestions > repository.getSize()) {
                    System.out.println("Invalid input. Please try again.");
                } else isValidInput = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                s.nextLine();
            }
        }

        ArrayList <Integer> examQuestionsId = new ArrayList<Integer>();
        int serial = 0;
        for (int i = 0; i < numOfQuestions; i++) {
            boolean isValid = false;
            while (!isValid) {
                try {
                    serial = checkIfValidSerial(repository);
                    isValid = true;
                    for (int j = 0; j < i && isValid; j++) {
                        if (serial == examQuestionsId.get(j)) {
                            System.out.println("This question is already in the exam. Try again");
                            isValid = false;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Try again");
                }
            }
            examQuestionsId.add(i,serial);
        }

        repository.createExam(examQuestionsId, numOfQuestions);
        for (int i = 0; i < numOfQuestions; i++) {
            if (repository.isMultiChoiceQuestion(examQuestionsId.get(i))) {
                int numOfInputs = 0;
                boolean isValid = false;
                while (!isValid) {
                    try {
                        System.out.println("how many answers do you want for this question?\n"
                                + repository.getQuestionById(examQuestionsId.get(i)).toString());
                        numOfInputs = s.nextInt();
                        if (numOfInputs < 2 || numOfInputs > 8 ||
                                numOfInputs > (repository.getNumOfAnswers(examQuestionsId.get(i)))) {
                            System.out.println("Invalid input, please enter a number between 2-" +
                                    (repository.getNumOfAnswers(examQuestionsId.get(i))));
                        } else isValid = true;
                    } catch (Exception e) {
                        System.out.println("invalid input. Please try again.");
                        s.nextLine();
                    }
                }
                ArrayList <Integer> answersByIndex = new ArrayList<Integer>();
                for (int j = 0; j < numOfInputs; j++) {
                    isValid = false;
                    int answerNum = 0;
                    System.out.println("please choose answer number:" + "\n"
                            + repository.getQuestionById(examQuestionsId.get(i)).toString());
                    while (!isValid) {
                        try {
                            answerNum = checkIfValidAnswer(repository, examQuestionsId.get(i));
                            isValid = true;
                            for (int k = 0; k < j && isValid; k++) {
                                if (answerNum  == answersByIndex.get(k)) {
                                    System.out.println("This answer already exists in the questions exam");
                                    isValid = false;
                                }
                            }
                        } catch (Exception e) {
                            System.err.println("Invalid input. Please try again");
                        }
                    }
                    answersByIndex.add(j,answerNum);
                }
                repository.pickAnswersExam(repository.getQuestionExam(i), answersByIndex);
            }
        }
        try {
            repository.printManualExam();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void generateAutomaticExam(Manager repository, Scanner s) {
        int numOfQuestions;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Amount of questions in the repository: "
                        + repository.getSize() + "." +
                        "\n" + "How many questions do you want in the exam? ");
                numOfQuestions = s.nextInt();
                repository.generateExam(numOfQuestions, repository);
                validInput = true;
            } catch (Exception e) {
                System.err.println("Invalid input. Please try again.");
                s.nextLine();
            }
        }

    }

    public void copyExamToNewFile(Manager repository, Scanner s) throws CloneNotSupportedException {

            repository.cloneExam();

    }
    }


