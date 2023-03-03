package javafx.viewQuiz;

import java.io.*;
import java.util.*;

import static java.lang.System.out;

class Quiz {
    protected static Scanner file;
    protected static Quiz questionTyp1;
    protected static String str1,str2,name;
    protected ArrayList<Question> questions = new ArrayList();

    public Quiz() {
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return this.name;
    }

    public void addQuestion(Question newDescription) {
        questions.add(newDescription);
    }

    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    public static Quiz loadFromFile(String file) throws InvalidQuizFormatException {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(file));
        } catch (FileNotFoundException fileNotFoundException) {
            out.println(InvalidQuizFormatException.doesNotExistFile);
            return null;
        }

        Quiz quiz = readFile(scanner);
        quiz.setName(file.substring(0, file.indexOf(46)));
        return quiz;
    }

    public static Quiz readFile(Scanner file) throws InvalidQuizFormatException {
        Quiz questionTyp1 = new Quiz();

        try {
            if (!file.hasNext()) {
                return questionTyp1;
            }
            String str1 = file.nextLine();
            String str2 = file.nextLine();
            fileHasNext(file, questionTyp1, str1, str2);
            if (file.hasNext()) {
                do {
                    str1 = file.nextLine();
                    str2 = file.nextLine();

                    fileHasNext(file, questionTyp1, str1, str2);
                } while (file.hasNext());
            }

            return questionTyp1;
        } catch (Exception var8) {
            throw new InvalidQuizFormatException(var8.getMessage());
        }
    }

    public static void fileHasNext(Scanner file, Quiz questionTyp1, String str1, String str2) {
        Quiz.file = file;
        Quiz.questionTyp1 = questionTyp1;
        Quiz.str1 = str1;
        Quiz.str2 = str2;
        FillIn quiz;
        if (!file.hasNext()) {
            quiz = new FillIn();
            quiz.setDescription(str1);
            quiz.setAnswer(str2);
            questionTyp1.addQuestion(quiz);
        } else {
            String str = file.nextLine();
            switch (str) {
                case "" -> {
                    quiz = new FillIn();
                    quiz.setDescription(str1);
                    quiz.setAnswer(str2);
                    questionTyp1.addQuestion(quiz);
                }
                default -> {
                    Test test = new Test();
                    test.setDescription(str1);
                    test.setAnswer(str2);
                    String[] strings = new String[]{str, file.nextLine(), file.nextLine()};
                    test.setOptions(strings);
                    questionTyp1.addQuestion(test);
                    if (!file.hasNext()) {
                        return;
                    }
                    file.nextLine();
                }
            }
        }
    }

    public String toString() {
        String questionTyp1 = "";
        questionTyp1 = questionTyp1 + "------------------\n";
        int quiz = 1;

        Iterator test = this.questions.iterator();
        if (test.hasNext()) {
            do {
                Question str1 = (Question) test.next();
                questionTyp1 = questionTyp1 + quiz + ". " + str1.toString() + "\n\n";
                ++quiz;
            } while (test.hasNext());
        }

        return questionTyp1;
    }

}