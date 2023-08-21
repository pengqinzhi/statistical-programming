//Qinzhi Peng, qinzhip
package hw3;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@SuppressWarnings("serial")
public class DataException extends RuntimeException {
	DataException(int errorNum) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Data Error");
		alert.setContentText(errorNum
				+ " cases rejected.\nThe file must have cases with\ntab seperated date, title, and case number!");
		alert.showAndWait();
	}

	DataException(String errorType) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Data Error");
		if (errorType.equals("dupicate")) {
			alert.setContentText("Dupicate case number!");
		}

		if (errorType.equals("missing")) {
			alert.setContentText("Case must have date, title, type, and case number!");
		}
		alert.showAndWait();
	}

}
