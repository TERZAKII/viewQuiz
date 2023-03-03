package javafx.viewQuiz;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class QuizPane extends BorderPane {
    protected Button next,previous,submit,addQuestion;
    protected HBox textArea;
    protected VBox toTheCenter;
    protected Text textForStatus;

    QuizPane(Button next, Button previous, Button submit, Button addNewQuestion, HBox newTextArea, VBox toTheCenter, Text textForStatus) {
        this.next = next;
        this.previous = previous;
        this.submit = submit;
        addQuestion = addNewQuestion;
        textArea = newTextArea;
        this.toTheCenter = toTheCenter;
        this.textForStatus = textForStatus;
        
        HBox boxToBottom;
        Insets insets;
        boxToBottom = new HBox();
        insets = new Insets(7,7,7,7);

        addQuestion.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 10));
        textForStatus.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 10));
        submit.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 10));
        boxToBottom.getChildren().addAll(this.addQuestion,this.textForStatus, this.submit);
        boxToBottom.setPadding(insets);
        boxToBottom.setSpacing(35);
        boxToBottom.setAlignment(Pos.TOP_CENTER);
        super.setRight(next);
        super.setLeft(previous);
        super.setTop(textArea);
        super.setBottom(boxToBottom);
        super.setCenter(toTheCenter);

    }

    public void setTextStatus(String newText){
        textForStatus.setText(newText);
    }
    
    public String getTextStatus()
    {
        return this.textForStatus.getText();
    }

    public Button nextButton()
    {
        return this.next;
    }

    public Button previousButton()
    {
        return this.previous;
    }

    public Button submitButton()
    {
        return this.submit;
    }

    public Button getAddQuestionButton()
    {
        return this.addQuestion;
    }
    
    public VBox toTheCenter()
    {
        return this.toTheCenter;
    }
}
