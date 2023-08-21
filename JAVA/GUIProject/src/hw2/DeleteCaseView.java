//Qinzhi Peng, qinzhip
package hw2;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeleteCaseView extends CaseView {
	
	public DeleteCaseView(String header) {
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
