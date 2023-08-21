//Qinzhi Peng, qinzhip
package hw3;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

public class CyberCop extends Application {

	public static final String DEFAULT_PATH = "data"; // folder name where data files are stored
	public static final String DEFAULT_HTML = "/CyberCop.html"; // local HTML
	public static final String APP_TITLE = "Cyber Cop"; // displayed on top of app

	CCView ccView = new CCView();
	CCModel ccModel = new CCModel();

	CaseView caseView; // UI for Add/Modify/Delete menu option

	GridPane cyberCopRoot;
	Stage stage;

	static Case currentCase; // points to the case selected in TableView.

	public static void main(String[] args) {
		launch(args);
	}

	/** start the application and show the opening scene */
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setTitle("Cyber Cop");
		cyberCopRoot = ccView.setupScreen();
		setupAction();
		setupBindings();
		Scene scene = new Scene(cyberCopRoot, ccView.ccWidth, ccView.ccHeight);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		ccView.webEngine.load(getClass().getResource(DEFAULT_HTML).toExternalForm());
		primaryStage.show();
	}

	/**
	 * setupBindings() binds all GUI components to their handlers. It also binds
	 * disableProperty of menu items and text-fields with ccView.isFileOpen so that
	 * they are enabled as needed
	 */
	void setupBindings() {
		// binds disableProperty of closeFileMenuItem, saveFileMenuItem and
		// openFileMenuItem
		ccView.openFileMenuItem.disableProperty().bind(ccView.isFileOpen);
		ccView.closeFileMenuItem.disableProperty().bind(ccView.isFileOpen.not());
		ccView.saveFileMenuItem.disableProperty().bind(ccView.isFileOpen.not());

		// binds disableProperty of yearComboBox, clear, and search button when
		// isFileOpen is false
		ccView.yearComboBox.disableProperty().bind(ccView.isFileOpen.not());
		ccView.searchButton.disableProperty().bind(ccView.isFileOpen.not());
		ccView.clearButton.disableProperty().bind(ccView.isFileOpen.not());

		// binds disableProperty of add, modify, and delete MenuItem when isFileOpen is
		// false
		ccView.addCaseMenuItem.disableProperty().bind(ccView.isFileOpen.not());
		ccView.modifyCaseMenuItem.disableProperty().bind(ccView.isFileOpen.not());
		ccView.deleteCaseMenuItem.disableProperty().bind(ccView.isFileOpen.not());

		// binds disableProperty of chart MenuItem when isFileOpen is false
		ccView.caseCountChartMenuItem.disableProperty().bind(ccView.isFileOpen.not());
	}

	void setupAction() {
		// setOnAction for open, close, save, and exit MenuItem
		ccView.openFileMenuItem.setOnAction(new OpenFileMenuItemHandler());
		ccView.closeFileMenuItem.setOnAction(new CloseFileMenuItemHandler());
		ccView.exitMenuItem.setOnAction(new ExitMenuItemHandler());
		ccView.saveFileMenuItem.setOnAction(new SaveFileMenuItemHandler());

		// setOnAction for search, and clearButton
		ccView.searchButton.setOnAction(new SearchButtonHandler());
		ccView.clearButton.setOnAction(new ClearButtonHandler());

		// setOnAction for CaseMenuItem
		ccView.addCaseMenuItem.setOnAction(new CaseMenuItemHandler());
		ccView.modifyCaseMenuItem.setOnAction(new CaseMenuItemHandler());
		ccView.deleteCaseMenuItem.setOnAction(new CaseMenuItemHandler());

		// setOnAction for chart MenuItem
		ccView.caseCountChartMenuItem.setOnAction(new CaseCountChartMenuItemHandler());
	}

	private class OpenFileMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select file");
			fileChooser.setInitialDirectory(new File(DEFAULT_PATH)); // local path
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
			File file = null;
			if ((file = fileChooser.showOpenDialog(stage)) != null) { // if user choose a file to open
				ccView.isFileOpen.setValue(true); // set ccView.isFileOpen true
				ccModel.readCases(file.getAbsolutePath());
				ccModel.buildYearMapAndList();

				// updates messageLabel
				ccView.messageLabel.setText(ccModel.caseList.size() + " cases");

				// initialize each TextField or TextArea
				currentCase = ccModel.caseList.get(0);
				ccView.titleTextField.setText(currentCase.getCaseTitle());
				ccView.caseTypeTextField.setText(currentCase.getCaseTpye());
				ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
				ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());
				ccView.yearComboBox.setValue(currentCase.getCaseDate().substring(0, 4));

				// initialize yearCombobox and caseTableView
				ccView.yearComboBox.setItems(ccModel.yearList);
				ccView.caseTableView.setItems(ccModel.caseList);

				// initialize webEngine
				if (currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) { // if no link in data
					URL url = getClass().getClassLoader().getResource(DEFAULT_HTML); // default html
					if (url != null)
						ccView.webEngine.load(url.toExternalForm());
				} else if (currentCase.getCaseLink().toLowerCase().startsWith("http")) { // if external link
					ccView.webEngine.load(currentCase.getCaseLink());
				} else {
					URL url = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim()); // local link
					if (url != null)
						ccView.webEngine.load(url.toExternalForm());
				}

				// add a listener when select a new row
				ccView.caseTableView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Case>() {

					@Override
					public void onChanged(Change c) {
						if (c != null) {
							currentCase = ccView.caseTableView.getSelectionModel().getSelectedItems().get(0);

							// display new TextField
							ccView.titleTextField.setText(currentCase.getCaseTitle());
							ccView.caseTypeTextField.setText(currentCase.getCaseTpye());
							ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
							ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());
							ccView.yearComboBox.setValue(currentCase.getCaseDate().substring(0, 4));

							// initialize webEngine
							if (currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {
								URL url = getClass().getClassLoader().getResource(DEFAULT_HTML);
								if (url != null)
									ccView.webEngine.load(url.toExternalForm());
							} else if (currentCase.getCaseLink().toLowerCase().startsWith("http")) {
								ccView.webEngine.load(currentCase.getCaseLink());
							} else {
								URL url = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());
								if (url != null)
									ccView.webEngine.load(url.toExternalForm());
							}
						}
					}
				});

			} // end if fileChooser
		}
	} // end OpenFileMenuItemHandler

	private class SaveFileMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(DEFAULT_PATH));
			File file = fileChooser.showSaveDialog(stage);
			boolean iswrited = ccModel.writeCases(file.getAbsolutePath());
			if (iswrited == true) {
				ccView.titleTextField.clear();
				ccView.messageLabel.setText(file.getName() + " saved");
			} else {
				ccView.titleTextField.clear();
				ccView.messageLabel.setText("File save failed");
			}
		}
	}

	private class CloseFileMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// set ccView.isFileOpen false
			ccView.isFileOpen.setValue(false);

			// clear all text and reset
			ccView.messageLabel.setText("");
			ccView.titleTextField.clear();
			ccView.caseTypeTextField.clear();
			ccView.caseNumberTextField.clear();
			ccView.caseNotesTextArea.clear();
			ccView.yearComboBox.getItems().clear();
			ccView.caseTableView.getItems().clear();
			ccView.webEngine.load(getClass().getResource(DEFAULT_HTML).toExternalForm());
		}
	}

	private class ExitMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			System.exit(0);
		}
	}

	private class CaseCountChartMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			ccView.showChartView(ccModel.yearMap);
		}
	}

	private class SearchButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			// get text from boxes
			String searchTitle = ccView.titleTextField.getText().trim();
			String searchType = ccView.caseTypeTextField.getText().trim();
			String searchYear = ccView.yearComboBox.getValue();
			String searchNumber = ccView.caseNumberTextField.getText().trim();
			List<Case> matchingList = ccModel.searchCases(searchTitle, searchType, searchYear, searchNumber);

			// updates messageLabel
			ccView.messageLabel.setText(matchingList.size() + " cases");

			// set new currentCase if match something
			if (matchingList.size() != 0) {
				currentCase = matchingList.get(0);

				// display new TextField
				ccView.titleTextField.setText(currentCase.getCaseTitle());
				ccView.caseTypeTextField.setText(currentCase.getCaseTpye());
				ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
				ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());
				ccView.yearComboBox.setValue(currentCase.getCaseDate().substring(0, 4));

				// update new caseTableView
				ccView.caseTableView.setItems(FXCollections.observableArrayList(matchingList));

				// display new web-pages
				if (currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) { // if no link in data
					URL url = getClass().getClassLoader().getResource(DEFAULT_HTML); // default html
					if (url != null)
						ccView.webEngine.load(url.toExternalForm());
				} else if (currentCase.getCaseLink().toLowerCase().startsWith("http")) { // if external link
					ccView.webEngine.load(currentCase.getCaseLink());
				} else {
					URL url = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim()); // local link
					if (url != null)
						ccView.webEngine.load(url.toExternalForm());
				}
			}
		}
	}

	private class ClearButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			ccView.messageLabel.setText("");
			ccView.titleTextField.clear();
			ccView.caseTypeTextField.clear();
			ccView.caseNumberTextField.clear();
			ccView.caseNotesTextArea.clear();
			ccView.yearComboBox.setValue(null);
		}
	}

	private class CaseMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			// create a common handler for three menu items
			MenuItem caseMenuItem = (MenuItem) event.getSource();
			switch (caseMenuItem.getText()) {
			case "Add case":
				// build AddCaseView
				caseView = new AddCaseView("Add Case");
				caseView.updateButton.setText("Add Case");
				caseView.buildView();

				// bind AddButtonHandler to caseView's updateButton
				caseView.updateButton.setOnAction(new AddButtonHandler());
				break;

			case "Modify case":
				// build ModifyCaseView
				caseView = new ModifyCaseView("Modify Case");
				caseView.updateButton.setText("Modify Case");
				caseView.buildView();

				// set text by currentCase
				caseView.titleTextField.setText(currentCase.getCaseTitle());
				caseView.caseTypeTextField.setText(currentCase.getCaseTpye());
				caseView.caseDatePicker.setValue(LocalDate.parse((currentCase.getCaseDate())));
				caseView.caseNumberTextField.setText(currentCase.getCaseNumber());
				caseView.categoryTextField.setText(currentCase.getCaseCategory());
				caseView.caseLinkTextField.setText(currentCase.getCaseLink());
				caseView.caseNotesTextArea.setText(currentCase.getCaseNotes());

				// bind ModifyButtonHandler to caseView's updateButton
				caseView.updateButton.setOnAction(new ModifyButtonHandler());
				break;

			case "Delete case":
				// build DeleteCaseView
				caseView = new DeleteCaseView("Delete Case");
				caseView.updateButton.setText("Delete Case");
				caseView.buildView();

				// set text by currentCase
				caseView.titleTextField.setText(currentCase.getCaseTitle());
				caseView.caseTypeTextField.setText(currentCase.getCaseTpye());
				caseView.caseDatePicker.setValue(LocalDate.parse((currentCase.getCaseDate())));
				caseView.caseNumberTextField.setText(currentCase.getCaseNumber());
				caseView.categoryTextField.setText(currentCase.getCaseCategory());
				caseView.caseLinkTextField.setText(currentCase.getCaseLink());
				caseView.caseNotesTextArea.setText(currentCase.getCaseNotes());

				// bind ModifyButtonHandler to caseView's updateButton

				caseView.updateButton.setOnAction(new DeleteButtonHandler());
				break;
			}

			// setOnAction for caseView's clearButton
			caseView.clearButton.setOnAction((subevent) -> {
				caseView.titleTextField.clear();
				caseView.caseTypeTextField.clear();
				caseView.caseDatePicker.setValue(LocalDate.now());
				caseView.caseNumberTextField.clear();
				caseView.categoryTextField.clear();
				caseView.caseLinkTextField.clear();
				caseView.caseNotesTextArea.clear();
			});

			// setOnAction for caseView's closeButton
			caseView.closeButton.setOnAction((subevent) -> {
				caseView.stage.close();
			});
		}
	}

	private class AddButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// get text from each textBox
			Case newCase = null;
			String newTitle = caseView.titleTextField.getText().trim();
			String newType = caseView.caseTypeTextField.getText().trim();
			String newDate = caseView.caseDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String newNumber = caseView.caseNumberTextField.getText().trim();
			String newCategory = caseView.categoryTextField.getText().trim();
			String newLink = caseView.caseLinkTextField.getText().trim();
			String newNotes = caseView.caseNotesTextArea.getText().trim();
			try {
				if (newDate.equals("") || newTitle.equals("") || newType.equals("") || newNumber.equals("")) {
					throw new DataException("missing");
				} else if (ccModel.caseMap.containsKey(newNumber)) {
					throw new DataException("dupicate");
				} else {
					newCase = new Case(newDate, newTitle, newType, newNumber, newLink, newCategory, newNotes);

					// add a new case to caseList and caseMap
					ccModel.caseMap.put(newNumber, newCase);
					ccModel.caseList.add(ccModel.caseMap.get(newNumber));

					// update ccView's messageLabel and caseTableView
					ccView.messageLabel.setText(ccModel.caseList.size() + " cases");
					ccView.caseTableView.setItems(ccModel.caseList);
				}
			} catch (DataException e) {
				System.out.println("Data Error!");
			}
		}
	}

	private class ModifyButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			// get text from each textBox
			Case newCase = null;
			String newTitle = caseView.titleTextField.getText().trim();
			String newType = caseView.caseTypeTextField.getText().trim();
			String newDate = caseView.caseDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String newNumber = caseView.caseNumberTextField.getText().trim();
			String newCategory = caseView.categoryTextField.getText().trim();
			String newLink = caseView.caseLinkTextField.getText().trim();
			String newNotes = caseView.caseNotesTextArea.getText().trim();
			try {
				if (newDate.equals("") || newTitle.equals("") || newType.equals("") || newNumber.equals("")) {
					throw new DataException("missing");
				} else if (ccModel.caseMap.containsKey(newNumber)) {
					throw new DataException("dupicate");
				} else {
					newCase = new Case(newDate, newTitle, newType, newNumber, newLink, newCategory, newNotes);
					currentCase = newCase;

					// reset a existed case in the caseList and caseMap
					ccModel.caseMap.put(newNumber, currentCase);
					int index = ccView.caseTableView.getSelectionModel().getSelectedIndex();
					ccModel.caseList.set(index, currentCase);
				}
			} catch (DataException e) {
				System.out.println("Data Error!");
			}
		}
	}

	private class DeleteButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			if (ccModel.caseList.size() > 0) {
				// remove a existed case in the caseList and caseMap
				ccModel.caseList.remove(currentCase);
				ccModel.caseMap.remove(currentCase.toString());

				// set text by new currentCase
				caseView.titleTextField.setText(currentCase.getCaseTitle());
				caseView.caseTypeTextField.setText(currentCase.getCaseTpye());
				caseView.caseDatePicker.setValue(LocalDate.parse((currentCase.getCaseDate())));
				caseView.caseNumberTextField.setText(currentCase.getCaseNumber());
				caseView.categoryTextField.setText(currentCase.getCaseCategory());
				caseView.caseLinkTextField.setText(currentCase.getCaseLink());
				caseView.caseNotesTextArea.setText(currentCase.getCaseNotes());

				// update ccView's messageLabel
				ccView.messageLabel.setText(ccModel.caseList.size() + " cases");
			}
		}
	}

}
