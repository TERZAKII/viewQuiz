package javafx.viewQuiz;

import java.util.*;
import java.lang.*;
import java.io.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.geometry.*;


public class ViewQuiz extends Application {
	protected Quiz quiz; protected ArrayList<Question> allQuestions; 
	protected File file; protected String fileName;
	protected Stage stageForException;

	int question = 0; 

	protected HashMap<QuizPane, Question> map = new HashMap<>();
	protected ArrayList<QuizPane> listOfQuizPanes;

	public void start(Stage primaryStage) {
		primaryStage.setTitle("QuizViewer");

		BorderPane borderPane = new BorderPane();
		primaryStage.setMinHeight(450);
		primaryStage.setMinWidth(450);
		primaryStage.setResizable(false);

		Scene scene = new Scene(borderPane);
		
		Button loadButton = new Button("Browse File");
		loadButton.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 18));
		loadButton.setOnAction(e -> {

			FileChooser chooseFile = new FileChooser(); 
			chooseFile.setTitle("Choose file");
			file = chooseFile.showOpenDialog(null);
			do {
				boolean ok = true;
				if (ok != true) {
					System.out.println("File cancelled");
				} else {
					fileName = file.getName();
					if (!fileName.substring(fileName.length() - 4).equals(".txt")) {
						ok = false;
						file = null;
						exceptionStage();
						try {
							quiz = Quiz.loadFromFile(fileName);
							allQuestions = quiz.getQuestions();

							long startTime = System.currentTimeMillis();

							for (int i = 0; i < allQuestions.size(); i++) {
								TextArea textArea = new TextArea();
								String des = allQuestions.get(i).getDescription();
								if (des.contains("{blank}")) {
									des = des.replace("{blank}", "______");
								}
								textArea.setText(des);
								textArea.setEditable(false);
								textArea.setScrollLeft(0);
								textArea.setWrapText(true);
								textArea.setFont(Font.font(14));
								textArea.setMaxHeight(150);

								HBox hboxForTextArea = new HBox();
								hboxForTextArea.getChildren().add(textArea);
								Insets insets;
								insets = new Insets(8, 8, 8, 8);
								hboxForTextArea.setPadding(insets);

								Button btPrev = new Button("<<");
								Button btNext = new Button(">>");
								btPrev.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 14));
								btNext.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 14));

								Button submit = new Button("Submit");
								Text textForStatus = new Text();
								Button addQuestion = new Button("Add Question");

								VBox btnOptions = new VBox();
								if (allQuestions.get(i) instanceof Test) {
									Test test;
									test = (Test) allQuestions.get(i);
									ArrayList<String> optionsList = new ArrayList<>();
									optionsList.add(test.getAnswer());
									int k = 0;
									while (k < 3) {
										optionsList.add(test.getOptionAt(k));
										k++;
									}

									Collections.shuffle(optionsList);

									RadioButton btn1 = new RadioButton(optionsList.get(0));
									RadioButton btn2 = new RadioButton(optionsList.get(1));
									RadioButton btn3 = new RadioButton(optionsList.get(2));
									RadioButton btn4 = new RadioButton(optionsList.get(3));
									btn1.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 12));
									btn2.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 12));
									btn3.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 12));
									btn4.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 12));

									btnOptions.getChildren().addAll(btn1, btn2, btn3, btn4);
								} else {
									TextField textField = new TextField();
									btnOptions.getChildren().add(textField);
								}
								btnOptions.setSpacing(5);
								btnOptions.setPadding(new Insets(8, 8, 8, 8));
								QuizPane quizPane = new QuizPane(btNext, btPrev, submit, addQuestion, hboxForTextArea, btnOptions, textForStatus);

								map.put(quizPane, allQuestions.get(i));
							}

							listOfQuizPanes = new ArrayList<>();
							map.keySet().forEach(statusQuiz -> listOfQuizPanes.add(statusQuiz));
							scene.setRoot(listOfQuizPanes.get(question));

							int i = 0;
							while (i < listOfQuizPanes.size()) {
								QuizPane statusQuiz = listOfQuizPanes.get(i);
								statusQuiz.setTextStatus("Status: " + (i + 1) + "/" + listOfQuizPanes.size());

								Button nextButton = statusQuiz.nextButton();
								nextButton.setOnAction(ea -> {
									if (question == listOfQuizPanes.size() - 1) {
										String s = statusQuiz.getTextStatus();
										if (!s.contains("End of Quiz!")) {
											s += "\nEnd of Quiz!";
										}
										statusQuiz.setTextStatus(s);
									} else {
										question++;
										scene.setRoot(listOfQuizPanes.get(question));
									}
								});

								Button prevButton = statusQuiz.previousButton();
								prevButton.setOnAction(ea -> {
									if (question == 0) {
										String s = statusQuiz.getTextStatus();
										if (!s.contains("Start of Quiz!")) {
											s += "\n"+ "Start of Quiz!";
										}
										statusQuiz.setTextStatus(s);
									} else {
										question--;
										scene.setRoot(listOfQuizPanes.get(question));
									}
								});

								Button submitButton = statusQuiz.submitButton();
								submitButton.setOnAction(ea -> {
									long endTime = System.currentTimeMillis();
									int correct = 0;
									for (int it = 0; it < listOfQuizPanes.size(); it++) {
										QuizPane quizListPane = listOfQuizPanes.get(it);
										Question q = map.get(quizListPane);

										String answer = q.getAnswer();
										answer = answer.toLowerCase();

										if (!(q instanceof Test)) {
											VBox vbox = quizListPane.toTheCenter;
											TextField field = (TextField) vbox.getChildren().get(0);

											String userAnswer = field.getText();
											userAnswer = userAnswer.trim();
											userAnswer = userAnswer.toLowerCase();

											if (answer.equals(userAnswer)) {
												correct++;
											}
										} else {
											VBox vbox = quizListPane.toTheCenter;

											RadioButton rb1;
											rb1 = (RadioButton) vbox.getChildren().get(0);
											RadioButton rb2;
											rb2 = (RadioButton) vbox.getChildren().get(1);
											RadioButton rb3;
											rb3 = (RadioButton) vbox.getChildren().get(2);
											RadioButton rb4;
											rb4 = (RadioButton) vbox.getChildren().get(3);

											String userAnswer = "";
											if (!rb1.isSelected()) {
												if (rb2.isSelected()) {
													userAnswer = rb2.getText();
												} else if (rb3.isSelected()) {
													userAnswer = rb3.getText();
												} else if (rb4.isSelected()) {
													userAnswer = rb4.getText();
												}
											} else {
												userAnswer = rb1.getText();
											}

											userAnswer = userAnswer.toLowerCase();

											if (!answer.equals(userAnswer)) {
												continue;
											}
											correct++;
										}
									}
									submitStage(correct, listOfQuizPanes.size(), endTime - startTime);
								});

								Button addQuestion = statusQuiz.getAddQuestionButton();
								addQuestion.setOnAction(ea -> {
									AddQuestion adderQuestion = new AddQuestion();
									try {
										adderQuestion.start(fileName);
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								});
								i++;
							}

						} catch (InvalidQuizFormatException ex) {
							ok = false;
							file = null;
							exceptionStage();
						}
					} else {
						try {
							quiz = Quiz.loadFromFile(fileName);
							allQuestions = quiz.getQuestions();
							long startTime = System.currentTimeMillis();

							{
								int i = 0;
								if (i < allQuestions.size()) {
									do {
										Question allQuestion = allQuestions.get(i);
										TextArea textArea = new TextArea();
										String des = allQuestion.getDescription();
										if (des.contains("{blank}")) {
											des = des.replace("{blank}", "______");
										}
										textArea.setText(des);
										textArea.setEditable(false);
										textArea.setScrollLeft(0);
										textArea.setWrapText(true);
										textArea.setFont(Font.font(14));
										textArea.setMaxHeight(150);

										HBox hboxForTextArea = new HBox();
										hboxForTextArea.getChildren().add(textArea);
										hboxForTextArea.setPadding(new Insets(8, 8, 8, 8));

										Button btPrev = new Button("<<");
										Button btNext = new Button(">>");
										btPrev.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 14));
										btNext.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 14));

										Button submit = new Button("Submit");
										Text textForStatus = new Text();
										Button addQuestion = new Button("Add Question");

										VBox btnOptions = new VBox();
										if (!(allQuestion instanceof Test)) {
											TextField textField = new TextField();
											btnOptions.getChildren().add(textField);
										} else {
											Test test;
											test = (Test) allQuestion;
											ArrayList<String> optionsList = new ArrayList<>();
											optionsList.add(test.getAnswer());
											int k = 0;
											if (k < 3) {
												do {
													optionsList.add(test.getOptionAt(k));
													k++;
												} while (k < 3);
											}

											Collections.shuffle(optionsList);

											RadioButton btn1 = new RadioButton(optionsList.get(0));
											RadioButton btn2 = new RadioButton(optionsList.get(1));
											RadioButton btn3 = new RadioButton(optionsList.get(2));
											RadioButton btn4 = new RadioButton(optionsList.get(3));
											btn1.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 14));
											btn2.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 14));
											btn3.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 14));
											btn4.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 14));

											btnOptions.getChildren().addAll(btn1, btn2, btn3, btn4);
										}
										btnOptions.setSpacing(5);
										btnOptions.setPadding(new Insets(8, 8, 8, 8));


										QuizPane quizPane = new QuizPane(btNext, btPrev, submit, addQuestion, hboxForTextArea, btnOptions, textForStatus);

										map.put(quizPane, allQuestion);
										i++;
									} while (i < allQuestions.size());
								}
							}

							listOfQuizPanes = new ArrayList<>();

							map.keySet().forEach(statusQuiz -> listOfQuizPanes.add(statusQuiz));

							scene.setRoot(listOfQuizPanes.get(question));

							int i = 0;
							if (i < listOfQuizPanes.size()) {
								do {
									QuizPane statusQuiz = listOfQuizPanes.get(i);

									statusQuiz.setTextStatus("Status: "
											+ (i + 1) + "/" + listOfQuizPanes.size());

									Button nextButton = statusQuiz.nextButton();
									nextButton.setOnAction(ea -> {
										if (question != listOfQuizPanes.size() - 1) {
											question++;
											scene.setRoot(listOfQuizPanes.get(question));
										} else {
											String s = statusQuiz.getTextStatus();
											if (!s.contains("End of Quiz!")) s += "\nEnd of Quiz!";
											statusQuiz.setTextStatus(s);
										}
									});

									Button prevButton = statusQuiz.previousButton();
									prevButton.setOnAction(ea -> {
										switch (question) {
											case 0 -> {
												String s = statusQuiz.getTextStatus();
												if (!s.contains("Start of Quiz!")) {
													s += "\nStart of Quiz!";
												}
												statusQuiz.setTextStatus(s);
											}
											default -> {
												question--;
												scene.setRoot(listOfQuizPanes.get(question));
											}
										}
									});

									Button submitButton = statusQuiz.submitButton();
									submitButton.setOnAction(ea -> {
										long endTime = System.currentTimeMillis();
										int correct = 0;
										int it = 0;
										if (it < listOfQuizPanes.size()) {
											do {
												QuizPane quizListPane = listOfQuizPanes.get(it);

												Question q = map.get(quizListPane);

												String answer = q.getAnswer();
												answer = answer.toLowerCase();

												if (!(q instanceof Test)) {
													VBox vbox = quizListPane.toTheCenter();
													TextField field = (TextField) vbox.getChildren().get(0);

													String userAnswer = field.getText();
													userAnswer = userAnswer.trim();
													userAnswer = userAnswer.toLowerCase();

													if (answer.equals(userAnswer)) {
														correct++;
													}
												} else {
													VBox vbox = quizListPane.toTheCenter();

													RadioButton rb1 = (RadioButton) vbox.getChildren().get(0);
													RadioButton rb2 = (RadioButton) vbox.getChildren().get(1);
													RadioButton rb3 = (RadioButton) vbox.getChildren().get(2);
													RadioButton rb4 = (RadioButton) vbox.getChildren().get(3);

													String userAnswer = "";
													if (rb1.isSelected()) userAnswer = rb1.getText();
													else if (rb2.isSelected()) {
														userAnswer = rb2.getText();
													} else {
														if (rb3.isSelected()) {
															userAnswer = rb3.getText();
														} else {
															if (rb4.isSelected()) {
																userAnswer = rb4.getText();
															}
														}
													}

													userAnswer = userAnswer.toLowerCase();

													if (!answer.equals(userAnswer)) {
													} else {
														correct++;
													}
												}
												it++;
											} while (it < listOfQuizPanes.size());
										}
										submitStage(correct, listOfQuizPanes.size(), endTime - startTime);
									});

									Button addQuestion = statusQuiz.getAddQuestionButton();
									addQuestion.setOnAction(ea -> {
										AddQuestion adderQuestion = new AddQuestion();
										try {
											adderQuestion.start(fileName);
										} catch (Exception exception) {
											exception.printStackTrace();
										}
									});
									i++;
								} while (i < listOfQuizPanes.size());
							}

						} catch (InvalidQuizFormatException ex) {
							ok = false;
							file = null;
							exceptionStage();
						}
					}
					if (ok == true) {
						break;
					}
				}
			} while (true);
		});

		borderPane.setCenter(loadButton);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void exceptionStage(){
		stageForException = new Stage();
		stageForException.setMinHeight(100);
		stageForException.setMinWidth(200);
		stageForException.setResizable(false);
		VBox borderPane = new VBox();
		HBox hbox = new HBox();

		Label lbForException = new Label(InvalidQuizFormatException.unquestioned);
		lbForException.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 15));
		lbForException.setAlignment(Pos.CENTER);
		Image image = new Image("error.png");
		ImageView viewImage = new ImageView(image);
		viewImage.setFitWidth(100);
		viewImage.setFitHeight(100);

		hbox.getChildren().add(lbForException);
		hbox.getChildren().add(viewImage);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(5,5,5,5));
		hbox.setSpacing(20);

		Label text = new Label();
		text.setText("Try to open another File");
		text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 15));
		text.setAlignment(Pos.CENTER);

		HBox hbox2 = new HBox();
		hbox2.getChildren().add(text);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.setPadding(new Insets(5,5,5,5));

		borderPane.getChildren().add(hbox);
		borderPane.getChildren().add(hbox2);

		Scene scene = new Scene(borderPane);
		stageForException.setScene(scene);
		stageForException.show();
	}



	public void submitStage(int	num1, int num2, long time) {
		Stage submitstage = new Stage();
		VBox borderPane = new VBox();
		time = time / 1000;
		long s = time%60;
		time = time/60;
		long m = time%60;
		time = time/60;
		long h = time%24;

		String formalTime = "";
		formalTime+= h > 9 ? h + ":" : "0" + h + ":";
		formalTime+= m > 9 ? m + ":" : "0" + m + ":";
		formalTime+= s > 9 ? Long.valueOf(s) : "0" + s;

		HBox hbox = new HBox();
		Label label = new Label();
		label.setText("It took "+formalTime+" to take exam to you.");
		label.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 15));
		label.setTextFill(Color.BLUE);
		hbox.getChildren().add(label);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(5,5,5,5));


		HBox hbox1 = new HBox();
		Label label1 = new Label("Number of correct answers: " + num1);
		label1.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 15));
		label1.setTextFill(Color.GREEN);
		Image image1 = new Image("correct.png");
		ImageView viewImage1 = new ImageView(image1);
		viewImage1.setFitWidth(75);
		viewImage1.setFitHeight(75);
		hbox1.getChildren().addAll(label1, viewImage1);
		hbox1.setAlignment(Pos.CENTER);
		hbox1.setPadding(new Insets(5,5,5,5));
		hbox1.setSpacing(20);

		HBox hbox2 = new HBox();
		Label label2 = new Label("Number of incorrect answers: " + (num2-num1));
		label2.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 15));
		label2.setTextFill(Color.RED);
		Image image2 = new Image("incorrect.png");
		ImageView viewImage2 = new ImageView(image2);
		viewImage2.setFitWidth(75);
		viewImage2.setFitHeight(75);
		hbox2.getChildren().addAll(label2, viewImage2);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.setPadding(new Insets(5,5,5,5));
		hbox2.setSpacing(20);

		HBox hbox3 = new HBox();
		Label label3 = new Label("You may try again...");
		label3.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 10));
		hbox3.getChildren().add(label3);
		hbox3.setAlignment(Pos.CENTER);
		hbox3.setPadding(new Insets(5,5,5,5));

		borderPane.getChildren().addAll(hbox, hbox1, hbox2, hbox3);

		Scene scene = new Scene(borderPane);
		submitstage.setScene(scene);
		submitstage.show();	
	}
}
