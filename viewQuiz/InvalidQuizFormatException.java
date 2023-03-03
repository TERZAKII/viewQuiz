package javafx.viewQuiz;

class InvalidQuizFormatException extends Exception {
    public static String doesNotExistFile = "Such File does not exist";
    public static String addQuestion =  "The question added";
    public static String emptyAnswer =  "empty Answer field";
    public static String emptyDescription =  "empty Description field";
    public static String unquestioned =  "InvalidQuizFormatException unquestioned";

    public InvalidQuizFormatException(String exception) {
        super(exception);
    }
}
