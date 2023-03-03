package javafx.viewQuiz;

import java.util.*;
import java.io.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.Label;
import javafx.scene.control.*;

public class AddQuestion {
	public void start(String fileCreater) throws Exception{
		Stage stage = new Stage();
		stage.setMinHeight(250);
		stage.setMinWidth(350);

		HBox choose;
		BorderPane borderPane;
		Button test,fill;
		choose = new HBox();
		borderPane = new BorderPane();
		test = new Button("Test");
		fill = new Button("Fill in");
		
		test.setMinHeight(250);
		fill.setMinHeight(250);

		test.setMinWidth(250);
		fill.setMinWidth(250);

		test.setFont(Font.font("Arial", 28));
		fill.setFont(Font.font("Arial", 28));


		choose.getChildren().addAll(test, fill);
		choose.setSpacing(8);
		choose.setAlignment(Pos.CENTER);

		Scene scene = new Scene(choose);
		stage.setScene(scene);
		stage.setTitle("Question Adder");
		stage.show();

		test.setOnAction(e -> {
			GridPane gridPane = new GridPane();
			
			Label answerLabel, descriptionLabel, option1, option2, option3;
			answerLabel = new Label("Answer: ");descriptionLabel = new Label("Description: ");option1 = new Label("Option 1: ");option2 = new Label("Option 2: ");option3 = new Label("Option 3: ");
			answerLabel.setFont(Font.font("Arial",12));descriptionLabel.setFont(Font.font("Arial",12));option1.setFont(Font.font("Arial",12));option2.setFont(Font.font("Arial",12));option3.setFont(Font.font("Arial",12));

			TextField answerText, descriptionText, option1Text, option2Text, option3Text;
			answerText = new TextField();descriptionText = new TextField();option1Text = new TextField();option2Text = new TextField();option3Text = new TextField();

			gridPane.add(answerLabel, 0, 1); gridPane.add(answerText, 1, 1);
			
			gridPane.add(descriptionLabel, 0, 0); gridPane.add(descriptionText, 1, 0);
		
			gridPane.add(option1, 0, 2); gridPane.add(option1Text, 1, 2);

			gridPane.add(option2, 0, 3); gridPane.add(option2Text, 1, 3);

			gridPane.add(option3, 0, 4); gridPane.add(option3Text, 1, 4);
			gridPane.setVgap(5);

			Button addButton;
			HBox boxForButton;
			Insets insets;
			addButton = new Button("Add");
			boxForButton = new HBox();
			addButton.setFont(Font.font("Arial", 12));
			boxForButton.getChildren().add(addButton);
			boxForButton.setAlignment(Pos.CENTER);
			insets = new Insets(10, 10, 10, 10);
			boxForButton.setPadding(insets);

			Stage primaryStage;
			StackPane gridPaneForStage;
			Text text;
			Scene scene2;
			Insets insetsGrid;
			primaryStage = new Stage();
			gridPaneForStage = new StackPane();
			text = new Text();
			primaryStage.setMinWidth(250);
			primaryStage.setMinHeight(150);
			text.setFont(Font.font("Arial", 22));
			gridPaneForStage.getChildren().add(text);
			insetsGrid = new Insets(8,8,8,8);
			gridPaneForStage.setPadding(insetsGrid);
			scene2 = new Scene(gridPaneForStage);
			primaryStage.setScene(scene2);

			addButton.setOnAction(ea -> {
				if (!descriptionText.getText().equals("")) {
					if (!answerText.getText().isEmpty()) {
						if (!option1Text.getText().isEmpty()) {
							if (!option2Text.getText().isEmpty()) {
								if (!option3Text.getText().isEmpty()) {
									try {
										FileWriter fw;
										PrintWriter output;
										File file;
										Scanner scanner;

										fw = new FileWriter(fileCreater, true);
										output = new PrintWriter(fw);
										file = new File(fileCreater);
										scanner = new Scanner(file);
				
										if (scanner.hasNext()) {
											output.println();
											output.println(descriptionText.getText());
											output.println(answerText.getText());
											output.println(option1Text.getText());
											output.println(option2Text.getText());
											output.println(option3Text.getText());
										} else {
											output.println(descriptionText.getText());
											output.println(answerText.getText());
											output.println(option1Text.getText());
											output.println(option2Text.getText());
											output.println(option3Text.getText());
										}
										output.close();
				
										option3Text.setText("");
										option2Text.setText("");
										option1Text.setText("");
										answerText.setText("");
										descriptionText.setText("");
				
										text.setText(InvalidQuizFormatException.addQuestion);
										primaryStage.show();
									} catch (FileNotFoundException i) {
										System.out.println(OptionsErrors.error);
									} catch (IOException i) {
										System.out.println(OptionsErrors.error);
									}
				
								} else {
									text.setText(OptionsErrors.option3Error);
									primaryStage.show();
								}
							} else {
								text.setText(OptionsErrors.option2Error);
								primaryStage.show();
							}
						} else {
							text.setText(OptionsErrors.option1Error);
							primaryStage.show();
						}
					} else {
						text.setText(InvalidQuizFormatException.emptyAnswer);
						primaryStage.show();
					}
				} else {
					text.setText(InvalidQuizFormatException.emptyDescription);
					primaryStage.show();
				}
			});

			borderPane.setCenter(gridPane);
			borderPane.setBottom(boxForButton);
			Insets insets1 = new Insets(5,5,5,5);
			borderPane.setPadding(insets1);
			scene.setRoot(borderPane);
		});

		fill.setOnAction(e -> {
			GridPane gridPane = new GridPane();
			Label descriptionLabel = new Label("Description (answer -> {blank}): ");
			descriptionLabel.setFont(Font.font("Arial",15));
			descriptionLabel.setWrapText(true);
			Label answerLabel = new Label("Answer: ");
			answerLabel.setFont(Font.font("Arial",15));

			TextField descriptionText = new TextField();
			TextField answerText = new TextField();

			gridPane.add(descriptionLabel, 0, 0);
			gridPane.add(descriptionText, 1, 0);
			gridPane.add(answerLabel, 0, 1);
			gridPane.add(answerText, 1, 1);
			gridPane.setVgap(5);

			Button addButton = new Button("Add");
			addButton.setFont(Font.font("Arial", 15));
			HBox boxForButton = new HBox();
			boxForButton.getChildren().add(addButton);
			boxForButton.setPadding(new Insets(10, 10, 10, 10));
			boxForButton.setAlignment(Pos.CENTER);

			Stage primaryStage = new Stage();
			primaryStage.setMinWidth(200);
			primaryStage.setMinHeight(100);
			StackPane gridPaneForStage = new StackPane();
			Text text = new Text();
			text.setFont(Font.font("Arial", 20));
			gridPaneForStage.getChildren().add(text);
			gridPaneForStage.setPadding(new Insets(8,8,8,8));

			Scene scene2 = new Scene(gridPaneForStage);
			primaryStage.setScene(scene2);

			addButton.setOnAction(ea -> {
				if (!descriptionText.getText().equals("")) {
					if (!answerText.getText().isEmpty()) {
						try {
							FileWriter fw = new FileWriter(fileCreater, true);
							PrintWriter output = new PrintWriter(fw);
							File file = new File(fileCreater);
							Scanner ch = new Scanner(file);

							if (ch.hasNext()) {
								output.println();
							}
							output.println(descriptionText.getText());
							output.println(answerText.getText());
							output.close();

							answerText.setText("");
							descriptionText.setText("");

							text.setText(InvalidQuizFormatException.addQuestion);
							primaryStage.show();
						} catch (FileNotFoundException i) {
							System.out.println(OptionsErrors.error);
						} catch (IOException i) {
							System.out.println(OptionsErrors.error);
						}
					} else {
						text.setText(InvalidQuizFormatException.emptyAnswer);
						primaryStage.show();
					}
				} else {
					text.setText(InvalidQuizFormatException.emptyDescription);
					primaryStage.show();
				}
			});

			borderPane.setCenter(gridPane);
			borderPane.setBottom(boxForButton);
			Insets insets = new Insets(4,4,4,4);
			borderPane.setPadding(insets);
			scene.setRoot(borderPane);
		});
	}
}