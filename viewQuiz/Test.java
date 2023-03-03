package javafx.viewQuiz;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.System.arraycopy;
import static java.lang.System.out;
import static java.util.Collections.shuffle;

class Test extends Question {
    protected String[] options = new String[4];
    protected ArrayList<Character> labels = new ArrayList();

    public Test() {
        int bytes = 65;
        IntStream.range(0, 4).forEach(list -> this.labels.add((char) (bytes + list)));
    }

    public void setOptions(String[] bytes) {
        arraycopy(bytes, 0, this.options, 0, 3);
    }
    public String getOptionAt(int newOption) {
        return this.options[newOption];
    }

    public String toString() {
        String descryption = this.getDescription();
        List list = Arrays.asList(this.options);
        list.set(3, this.getAnswer());
        shuffle(list);
        int var3 = 1;

        Iterator iterator = list.iterator();
        if (iterator.hasNext()) {
            do {
                descryption +="\n" +  this.labels.get(var3 - 1) + ") " + ((String) iterator.next()) + (var3++ < 4 ? "\n" : "");
            } while (iterator.hasNext());
        }

        return descryption;
    }


    public static Scanner file;
    public static Quiz questionTyp1;
    public static String str1;
    public static String str2;
    protected String name;
    protected ArrayList<Question> questions = new ArrayList();

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
    public void start() throws Exception{
        out.println("========================================================\n");
        out.printf("WELCOME TO \"%s\" QUIZ!\n", this.getName());
        out.println("________________________________________________________\n");
        int questionTyp1 = this.questions.size();
        int quiz = 0;
        int test = 1;
        Collections.shuffle(this.questions);
        Scanner str1 = new Scanner(System.in);

        Iterator str2 = this.questions.iterator();
        if (str2.hasNext()) {
            do {
                Question question = (Question) str2.next();
                out.println(test++ + ". " + question);
                out.println("---------------------------");
                if (!(question instanceof FillIn)) {
                    if (question instanceof Test) {
                        out.print("Enter the choice: ");

                        int size;
                        size = str1.nextLine().charAt(0) - 65;
                        while (size < 0 || size >= this.questions.size()) {
                            out.print("Invalid choice! Try again (Ex: A, B, ...): ");
                            size = str1.nextLine().charAt(0) - 65;
                        }

                        if (!((Test) question).getOptionAt(size).equals(question.getAnswer())) {
                            out.println("Incorrect!");
                        } else {
                            out.println("Correct!");
                            ++quiz;
                        }
                    }
                } else {
                    out.print("Type your answer: ");
                    if (!str1.nextLine().toLowerCase().equals(question.getAnswer().toLowerCase())) {
                        out.println("Incorrect!");
                    } else {
                        out.println("Correct!");
                        ++quiz;
                    }
                }
                out.println("________________________________________________________\n");
            } while (str2.hasNext());
        }

        float number = (float) quiz / (float)questionTyp1 * 100.0F;
        out.printf("Correct Answers: %d/%d (%.1f%%)\n", quiz, questionTyp1, number);
    }
}
