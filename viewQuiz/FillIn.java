package javafx.viewQuiz;

class FillIn extends Question {
    FillIn() {}

    public String toString() {
        String replace = this.getDescription().replace("{blank}", "_____");
        return replace;

    }
}
