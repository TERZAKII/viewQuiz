package javafx.viewQuiz;

abstract class Question {
    protected String description,answer;

    Question() {}

    public void setDescription(String newDescription) {
        description = newDescription;
    }
    public String getDescription() {
        return this.description;
    }
    public void setAnswer(String newAnswer) {
        answer = newAnswer;
    }
    public String getAnswer() {
        return this.answer;
    }
}

