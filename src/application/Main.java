package application;
	
//import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class Main extends Application {
	Label results; // the results of the entered equation
	TextField equation; // textfield for user entry of equation - handles doubles with +,-,*,/ 
	Button logarithm;
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Calculator");
			Pane root = new Pane();
			
			results = new Label("Results");
			results.setLayoutX(200);
			results.setLayoutY(200);
			
			equation = new TextField("Enter Equation here");
			equation.setPrefColumnCount(25);
			equation.setLayoutX(75);
			equation.setLayoutY(150);
			equation.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					SingletonCalculator calculator = SingletonCalculator.getCalculator();
					if (calculator.setEquation(equation.getText())) {
						double result = calculator.calculate();
						results.setText("" + result);
					} else {
						results.setText("Invalid Equation");
					}
				}
			});
			
			logarithm = new Button("Log");
			logarithm.setLayoutX(25);
			logarithm.setLayoutY(150);
			logarithm.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					SingletonCalculator calculator = SingletonCalculator.getCalculator();
					if (calculator.setEquation(equation.getText())) {
						double result = calculator.logResult();
						results.setText("" + result);
					} else {
						results.setText("Invalid Equation");
					}
				}
			});
			
			root.getChildren().addAll(results,equation,logarithm);
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
