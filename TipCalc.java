package application;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

public class TipCalc extends Application{

	Stage window;
	private final NumberFormat currency = NumberFormat.getCurrencyInstance();
	private final NumberFormat percent = NumberFormat.getPercentInstance();
	private BigDecimal tipPercentage = new BigDecimal(0.15);
	private TextField amountTextField;
	private Slider percentSlider;
	private TextField tipTextField;
	private TextField totalTextField;
	private Button calcButton;
	
	@Override
	public void start(Stage stage){
		window=stage;
		window.setTitle("Tip Calculator");
		GridPane layout = new GridPane();
		Scene scene = new Scene(layout, 210, 150);
		layout.setPrefWidth(layout.USE_COMPUTED_SIZE);
		layout.setPrefHeight(layout.USE_COMPUTED_SIZE);
		layout.setPadding(new Insets(5,5,5,5));
		layout.setHalignment(layout, HPos.RIGHT);
		layout.setVgap(5);
		layout.setHgap(5);
		
		Label amountLabel = new Label("Amount");
		GridPane.setConstraints(amountLabel, 0, 0);
		amountTextField = new TextField();
		GridPane.setConstraints(amountTextField, 1, 0);
		
		Label percentLabel = new Label("15%");
		GridPane.setConstraints(percentLabel, 0, 1);
		percentSlider = new Slider();
		percentSlider.setMax(30);
		percentSlider.setValue(15);
		percentSlider.setBlockIncrement(5);
		GridPane.setConstraints(percentSlider, 1, 1);
		
		currency.setRoundingMode(RoundingMode.HALF_UP);
		
		// Listens for when percentSlider changes and changes the text with it
		percentSlider.valueProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> ov,
							Number oldValue, Number newValue) {
						tipPercentage = BigDecimal.valueOf(newValue.intValue() / 100.0);
						percentLabel.setText(percent.format(tipPercentage));
					}
				});
		
		Label tipLabel = new Label("Tip");
		GridPane.setConstraints(tipLabel, 0, 2);
		tipTextField = new TextField();
		tipTextField.setEditable(false);
		GridPane.setConstraints(tipTextField, 1, 2);
		
		Label totalLabel = new Label("Total");
		GridPane.setConstraints(totalLabel, 0, 3);
		totalTextField = new TextField();
		totalTextField.setEditable(false);
		GridPane.setConstraints(totalTextField, 1, 3);
		
		calcButton = new Button("Calculate");
		calcButton.setMaxWidth(GridPane.USE_PREF_SIZE);
		calcButton.setOnAction(event -> {calculateButtonPress(event);});
		GridPane.setConstraints(calcButton, 1, 4);
		
		layout.getChildren().addAll(amountLabel, amountTextField, percentLabel, percentSlider, tipLabel, tipTextField, totalLabel, totalTextField, calcButton);
		
		window.setScene(scene);
		window.show();
		
	}
	
	public void calculateButtonPress(ActionEvent event) {
		try {
			BigDecimal amount = new BigDecimal(amountTextField.getText());
			BigDecimal tip = amount.multiply(tipPercentage);
			BigDecimal total = amount.add(tip);
			
			tipTextField.setText(currency.format(tip));
			totalTextField.setText(currency.format(total));
		}
		catch (NumberFormatException ex) {
			amountTextField.setText("Enter amount:");
			amountTextField.selectAll();
			amountTextField.requestFocus();
		}
	}

}
