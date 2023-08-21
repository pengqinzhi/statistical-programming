//Qinzhi Peng, qinzhip
package hw2;

import javafx.stage.Stage;
import javafx.scene.Scene;

public class AddCaseView extends CaseView {
	
	public AddCaseView(String header) {
		super(header);
	}

	@Override
	Stage buildView() {
		Scene scene = new Scene(updateCaseGridPane, CASE_WIDTH, CASE_HEIGHT);
		stage.setScene(scene);
		stage.show();
		return stage;
	}

}
