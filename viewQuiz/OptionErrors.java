package javafx.viewQuiz;

class OptionsErrors extends Exception {
    public static String error = "Error was found";
    public static String option1Error = "Option1 is Empty";
    public static String option2Error = "Option2 is Empty";
    public static String option3Error = "Option3 is Empty";

    public OptionsErrors(String errors) {
        super(errors);
    }
}
