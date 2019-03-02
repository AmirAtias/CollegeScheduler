import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.ListIterator;
import exceptions.CourseCodeException;
import exceptions.CourseCreditException;
import exceptions.CourseNameException;
import exceptions.SlotLectureException;
import exceptions.SlotRoomException;
import exceptions.SlotHoursException;
import exceptions.ShowNullException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

public class Controller implements FinalsAndMsg, Days { // the controller
	Model schedulerModel;
	int schedulerCounter;
	ArrayList<Course> allCourses;
	private TextField tfCourseName = new TextField();
	private TextField tfCourseCode = new TextField();
	private TextField tfCredits = new TextField();
	protected Course tmpCourse;
	protected Show tempShow;
	private int numOfShows = DEFAULT_VAL;
	private String courseName;
	private int courseCode;
	private double credits;
	private Stage primaryStage;
	private Scene primaryScene;
	private Scene showScene;
	private Scene courseScene;
	private Button createCourse = new Button("Create Course!");
	private Button btAddCourse = new Button("add Course");
	private Button btCreateScheduler = new Button("create Scheduler");
	private Button btCreateNewShow = new Button("create a new Show!");
	private Button btDoneCreateCourse = new Button("done create course!");
	private InputCheckFunc checkInput;

	public Controller(int schedulerCounter, Model schedulerModel, Stage primaryStage) {
		this.schedulerCounter = schedulerCounter;
		this.schedulerModel = schedulerModel;
		this.primaryStage = primaryStage;
		primaryScene = initPrimaryWindow();
		showScene = initShowWindow();
		courseScene = initCourseWindow();
		checkInput = new InputCheckFunc();
		setPrimaryScene();
	}
	

	private Scene initPrimaryWindow() {
		FlowPane pane = new FlowPane();
		pane.setPadding(new Insets(30, 25, 25, 25));
		pane.setHgap(30);
		pane.getChildren().add(btAddCourse);
		pane.getChildren().add(btCreateScheduler);
		btAddCourse.setOnAction(e -> {
			setCourseWindow();
		});
		
		btCreateScheduler.setOnAction(e -> createNewView());
		Scene scene = new Scene(pane, 325, 75);
		return scene;

	}
	// create view
		private void createNewView() {
			Stage stgView = new Stage();
			SchedulerView view = new SchedulerView(schedulerModel);
			Scene scene = new Scene(view, 2000,1000);
			stgView.setScene(scene);
			stgView.show();
			stgView.setAlwaysOnTop(true);
			stgView.setTitle("View");
			
		}

	private void setCourseWindow() {
		primaryStage.setTitle("add a new course");
		primaryStage.setScene(courseScene);
		primaryStage.centerOnScreen();
	}

	private void setPrimaryScene() {
		primaryStage.setScene(primaryScene);
		primaryStage.show(); // Display the stage
		primaryStage.setAlwaysOnTop(false);
		primaryStage.setResizable(false);
		primaryStage.setTitle("College Scheduler");
		primaryStage.centerOnScreen();
		// primaryStage.setOnCloseRequest(e -> Platform.exit());
	}

	private Scene initCourseWindow() {
		BorderPane CreateCoursePane = new BorderPane();
		CreateCoursePane.setPadding(new Insets(15, 15, 15, 15));
		GridPane panel1 = new GridPane();
		tfCourseName.setMaxWidth(250);
		tfCourseCode.setMaxWidth(250);
		tfCredits.setMaxWidth(250);
		createCourse.setMaxWidth(250);
		panel1.add(new Label("CourseName "), 0, 0);
		panel1.add(new Label("CoursCode"), 0, 1);
		panel1.add(new Label("Credits"), 0, 2);
		panel1.setHgap(10);
		panel1.setVgap(30);
		GridPane panel2 = new GridPane();
		panel2.add(tfCourseName, 0, 0);
		panel2.add(tfCourseCode, 0, 1);
		panel2.add(tfCredits, 0, 2);
		panel2.add(createCourse, 0, 3);
		panel2.setHgap(10);
		panel2.setVgap(20);
		CreateCoursePane.setCenter(panel2);
		CreateCoursePane.setLeft(panel1);
		createCourse.setOnAction(e -> createCourse());
		return new Scene(CreateCoursePane, 400, 225);

	}

	public String getCourseName() {
		return courseName;
	}

	private void setCourseName(String courseName) throws CourseNameException { // can use this function only from
		if (checkInput.StringContainInputError(courseName))
			throw new CourseNameException(MISSING_INPUT_ERROR); // tfCourseName
		if (!checkInput.CheckCourseName(courseName))
			throw new CourseNameException(COURSE_NAME_NOT_VALID_ERROR);
		this.courseName = courseName;

	}

	public int getCourseCode() {
		return courseCode;
	}

	private void setCourseCode(String strCourseCode) throws CourseCodeException {// can use this function only from
		if (checkInput.StringContainInputError(strCourseCode))
			throw new CourseCodeException(MISSING_INPUT_ERROR);
		if (!checkInput.stringContainDigitsOnly(strCourseCode))
			throw new CourseCodeException(NOT_ONLY_DIGITS_ERROR);
		int courseCode = Integer.parseInt(strCourseCode);
		if (!allCourses.isEmpty()) {
			Iterator<Course> it = allCourses.iterator();
			while (it.hasNext()) {
				if (it.next().getCourseCode() == courseCode)
					throw new CourseCodeException(COURSE_EXIST_ERROR);
			}
		}
		this.courseCode = courseCode;

	}

	public double getCredits() {
		return credits;
	}

	private void setCredits(String strCredits) throws CourseCreditException {// can use this function only from
		if (checkInput.StringContainInputError(strCredits))
			throw new CourseCreditException(MISSING_INPUT_ERROR); // tfCredits
		if (!checkInput.stringContainDigitsOnly(strCredits))
			throw new CourseCreditException(NOT_ONLY_DIGITS_ERROR);

		double credits = Double.parseDouble(strCredits);

		if (credits < MIN_VAL_OF_CREDITS || credits > MAX_VAL_OF_CREDITS) {
			throw new CourseCreditException(NOT_IN_CREDITS_RANGE_ERROR);
		}
		this.credits = credits;
	}

	private ObservableList<String> hoursObservableList() {
		ObservableList<String> hoursOptions = FXCollections.observableArrayList("07:00", "08:00", "09:00", "10:00",
				"11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
				"22:00", "23:00");
		return hoursOptions;
	}

	private void createCourse() {
		allCourses = schedulerModel.getAllCourses();// load all from model
		if (tfCourseName.getText() == null || tfCourseName.getText().trim().isEmpty())// check if one of the text field
																						// // is empty
			tfCourseName.setText(MISSING_INPUT_ERROR);//
		if (tfCourseCode.getText() == null || tfCourseCode.getText().trim().isEmpty())
			tfCourseCode.setText(MISSING_INPUT_ERROR);
		if (tfCredits.getText() == null || tfCredits.getText().trim().isEmpty())
			tfCredits.setText(MISSING_INPUT_ERROR);
		else {
			try {
				setCourseName(tfCourseName.getText().trim());
				setCourseCode(tfCourseCode.getText().trim());
				setCredits(tfCredits.getText().trim());
				tmpCourse = new Course(getCredits(), getCourseCode(), getCourseName());
				tfCourseName.clear();// clear textfields
				tfCourseCode.clear();
				tfCredits.clear();
				createShow();
			} catch (CourseNameException ex) {
				if (!ex.getMessage().equals(MISSING_INPUT_ERROR)) {
					showAlert(INPUT_NOT_VALID, ex.getMessage(), AlertType.ERROR);
					System.out.println(ex.getMessage());
					tfCourseName.clear();
				}
			} catch (CourseCodeException ex) {
				if (!ex.getMessage().equals(MISSING_INPUT_ERROR)) {
					showAlert(INPUT_NOT_VALID, ex.getMessage(), AlertType.ERROR);
					System.out.println(ex.getMessage());
					tfCourseCode.clear();
				}
			} catch (CourseCreditException ex) {
				if (!ex.getMessage().equals(MISSING_INPUT_ERROR)) {
					showAlert(INPUT_NOT_VALID, ex.getMessage(), AlertType.ERROR);
					System.out.println(ex.getMessage());
					tfCredits.clear();
				}
			}
		}

	}

	private void showAlert(String headerText, String contentText, AlertType type) {
		Alert controllerAlert = new Alert(type);
		controllerAlert.setHeaderText(headerText);
		controllerAlert.setContentText(contentText);
		controllerAlert.showAndWait();
	}

	private void createShow() {
		numOfShows++;
		primaryStage.setScene(showScene);
		primaryStage.centerOnScreen();
		primaryStage.setTitle("show number " + numOfShows);
		tempShow = new Show();

	}

	private Scene initShowWindow() {
		GridPane slotPane = new GridPane();
		for (int i = 0; i < MAX_NUM_OF_SLOTS; i++) {
			int slotNum = i + 1;
			TextField tfLec = new TextField();
			TextField tfRoom = new TextField();
			ComboBox<Days.AllDays> daysComBox = new ComboBox<>();
			daysComBox.setValue(Days.AllDays.Sunday);// set default
			ComboBox<String> startHourComBox = new ComboBox<>(hoursObservableList());
			startHourComBox.setValue(hoursObservableList().get(DEFAULT_VAL));// set default
			ComboBox<String> finishHourComBox = new ComboBox<>(hoursObservableList());
			finishHourComBox.setValue(hoursObservableList().get(DEFAULT_VAL));// set default
			daysComBox.getItems().setAll(Days.AllDays.values());
			Button createSlot = new Button("Create slot!");
			slotPane.add(new Label("Slot number:" + slotNum), 0, i);
			slotPane.add(new Label("Lecture:"), 1, i);
			slotPane.add(tfLec, 2, i);
			slotPane.add(new Label("Room:"), 3, i);
			slotPane.add(tfRoom, 4, i);
			slotPane.add(new Label("Day"), 5, i);
			slotPane.add(daysComBox, 6, i);
			slotPane.add(new Label("Start"), 7, i);
			slotPane.add(startHourComBox, 8, i);
			slotPane.add(new Label("Finish"), 9, i);
			slotPane.add(finishHourComBox, 10, i);
			slotPane.add(createSlot, 11, i);
			createSlot.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if (tfLec.getText() == null || tfLec.getText().trim().isEmpty())
						tfLec.setText(MISSING_INPUT_ERROR);
					if (tfRoom.getText() == null || tfRoom.getText().trim().isEmpty())
						tfRoom.setText(MISSING_INPUT_ERROR);
					try {
						int day, startHour, finishHour;
						String lecture, room;
						room = tfRoom.getText().trim();
						lecture = tfLec.getText().trim();
						checkInput.CheckRoomInput(room);
						checkInput.checkLectureInput(lecture);
						day = daysComBox.getValue().getIntValue();
						startHour = checkInput.FromStringToInt(startHourComBox.getValue());
						finishHour = checkInput.FromStringToInt(finishHourComBox.getValue());
						checkInput.checkHoursInput(startHour, finishHour);
						Slot tmpSlot = new Slot(room, lecture, day, startHour, finishHour);
						checkIfSlotExistAllCourses(tmpSlot);
						tempShow.addSlotDetails(slotNum, tmpSlot);
						showAlert(SUCCESSFUL, SUCCESS_ADD_SLOT, AlertType.CONFIRMATION);

						tfLec.clear();// clear text fields and set default for combo box
						tfRoom.clear();
						daysComBox.setValue(Days.AllDays.Sunday);// set default
						startHourComBox.setValue(hoursObservableList().get(DEFAULT_VAL));// set default
						finishHourComBox.setValue(hoursObservableList().get(DEFAULT_VAL));// set default

					} catch (SlotRoomException ex) {
						showAlert(INPUT_NOT_VALID, ex.getMessage(), AlertType.ERROR);
					} catch (SlotLectureException ex) {
						showAlert(INPUT_NOT_VALID, ex.getMessage(), AlertType.ERROR);
					} catch (SlotHoursException ex) {
						showAlert(INPUT_NOT_VALID, ex.getMessage(), AlertType.ERROR);

					}
				}
			});
		}
		BorderPane borderPane = new BorderPane();
		FlowPane btPane = new FlowPane();
		btPane.setPadding(new Insets(50, 5, 50, 500));
		borderPane.setPadding(new Insets(50, 50, 50, 50));
		btCreateNewShow.setOnAction(e -> addAndCreateShow());
		btDoneCreateCourse.setOnAction(e -> doneCreateCourse());
		btPane.getChildren().add(btCreateNewShow);
		btPane.getChildren().add(btDoneCreateCourse);
		btPane.setHgap(40);
		slotPane.setVgap(10);
		slotPane.setHgap(20);
		borderPane.setTop(slotPane);
		borderPane.setCenter(btPane);
		return new Scene(borderPane, 1400, 350);
	}

	private void doneCreateCourse() {
		try {
			if (!(tempShow.getAllSlots().isEmpty())) {
				tmpCourse.addShow(numOfShows, tempShow);
				schedulerModel.addCourse(tmpCourse);
				setPrimaryScene();
				numOfShows = DEFAULT_VAL;
			} else
				throw new ShowNullException(SHOW_NULL_EXCEPTION);
		} catch (ShowNullException ex) {
			showAlert(SHOW_NULL, ex.getMessage(), AlertType.ERROR);
		}
	}

	private void addAndCreateShow() {
		try {
			if (!(tempShow.getAllSlots().isEmpty())) {
				tmpCourse.addShow(numOfShows, tempShow);
				createShow();
			} else
				throw new ShowNullException(SHOW_NULL_EXCEPTION);
		} catch (ShowNullException ex) {
			showAlert(SHOW_NULL, ex.getMessage(), AlertType.ERROR);
		}
	}

	public int getNumOfShows() {
		return numOfShows;
	}

	private void checkIfSlotExistAllCourses(Slot slotForCheck) throws SlotLectureException, SlotRoomException {
		ListIterator<Course> it = allCourses.listIterator();
		while (it.hasNext()) {
			checkIfSlotExist(it.next(), slotForCheck, false);
		}
		checkIfSlotExist(tmpCourse, slotForCheck, true);// check the new course
	}

	private void checkIfSlotExist(Course courseForCheck, Slot slotForCheck, boolean checkTheNewShow)
			throws SlotLectureException, SlotRoomException {
		if (courseForCheck.getAllShows() != null || checkTheNewShow) {// check if there is another slots
			HashMap<Integer, Show> allShows = courseForCheck.getAllShows();
			Set<Integer> keysSet = allShows.keySet();
			Iterator<Integer> it = keysSet.iterator();
			while (it.hasNext() || checkTheNewShow) { // run until check all shows
				Show currentShow = null;
				HashMap<Integer, Slot> tempSlots;// slots of current show
				Set<Integer> slotsKeys;
				Iterator<Integer> slotsIt = null;
				if (it.hasNext())// check if there is another show to check
					currentShow = allShows.get(it.next());
				else if (checkTheNewShow) {// check the new show
					currentShow = tempShow;
					checkTheNewShow = false;
				}
				tempSlots = currentShow.getAllSlots();
				slotsKeys = tempSlots.keySet();
				slotsIt = slotsKeys.iterator();
				while (slotsIt.hasNext()) {// run on all slots in current show
					Slot tempSlot = tempSlots.get(slotsIt.next());
					if (tempSlot.getDay() == slotForCheck.getDay()) {// first check the days
						if (tempSlot.getStartHour() >= slotForCheck.getStartHour() && // check the hours
								tempSlot.getStartHour() <= slotForCheck.getFinishHour()
								|| tempSlot.getFinishHour() >= slotForCheck.getStartHour()
										&& tempSlot.getFinishHour() <= slotForCheck.getFinishHour()) {
							if (tempSlot.getLecture().equals(slotForCheck.getLecture()))
								throw new SlotLectureException(LECTURE_TEACHING_ERROR);
							else if (tempSlot.getRoom().equals(slotForCheck.getRoom()))
								throw new SlotRoomException(ROOM_OCCUPIED_ERROR);
						}
					}
				}
			}
		}
	}

}

class InputCheckFunc implements FinalsAndMsg {
	protected boolean StringContainInputError(String strForCheck) {// check if the textField contain input error msg
		if (strForCheck.trim().equals(MISSING_INPUT_ERROR))
			return true;
		return false;
	}

	protected boolean stringContainDigitsOnly(String strForCheck) {
		strForCheck = strForCheck.trim();
		for (int i = 0; i < strForCheck.length(); i++) {
			if (strForCheck.charAt(i) < MIN_VAL_OF_ASCII_DIGIT || strForCheck.charAt(i) > MAX_VAL_OF_ASCII_DIGIT)
				return false;
		}
		return true;

	}

	protected boolean CheckCourseName(String strForCheck) {
		strForCheck = strForCheck.trim();
		for (int i = 0; i < strForCheck.length(); i++) {
			if (!(strForCheck.charAt(i) >= MIN_VAL_OF_ASCII_DIGIT && strForCheck.charAt(i) <= MAX_VAL_OF_ASCII_DIGIT
					|| strForCheck.charAt(i) >= MIN_VAL_OF_ASCII_CHAR && strForCheck.charAt(i) <= MAX_VAL_OF_ASCII_CHAR
					|| strForCheck.charAt(i) == VAL_OF_SPACE_ASCII || strForCheck.charAt(i) == VAL_OF_AMPERSAND_ASCII
					|| strForCheck.charAt(i) == VAL_DASH_ASCII)) // check the input

				return false;
		}
		return true;
	}

	protected void checkHoursInput(int startHour, int finishHour) throws SlotHoursException {
		if (startHour == finishHour)
			throw new SlotHoursException(START_AND_FINISH_EQUAL);
		if (startHour > finishHour)
			throw new SlotHoursException(START_BIGGER_THEN_FINISH);

	}

	protected void checkLectureInput(String strForCheck) throws SlotLectureException {
		strForCheck = strForCheck.trim();
		for (int i = 0; i < strForCheck.length(); i++) {
			if (!(strForCheck.charAt(i) >= MIN_VAL_OF_ASCII_DIGIT && strForCheck.charAt(i) <= MAX_VAL_OF_ASCII_DIGIT
					|| strForCheck.charAt(i) >= MIN_VAL_OF_ASCII_CHAR && strForCheck.charAt(i) <= MAX_VAL_OF_ASCII_CHAR
					|| strForCheck.charAt(i) == VAL_OF_SPACE_ASCII || strForCheck.charAt(i) == VAL_OF_QUOTE_ASCII
					|| strForCheck.charAt(i) == VAL_OF_APOSTROPHE_ASCII || strForCheck.charAt(i) == VAL_DASH_ASCII))
				throw new SlotLectureException(LECTURE_NOT_VALID_ERROR);
		}

	}

	protected void CheckRoomInput(String strForCheck) throws SlotRoomException {
		strForCheck = strForCheck.trim();
		for (int i = 0; i < strForCheck.length(); i++) {
			if (!(strForCheck.charAt(i) >= MIN_VAL_OF_ASCII_DIGIT && strForCheck.charAt(i) <= MAX_VAL_OF_ASCII_DIGIT
					|| strForCheck.charAt(i) >= MIN_VAL_OF_ASCII_CHAR && strForCheck.charAt(i) <= MAX_VAL_OF_ASCII_CHAR
					|| strForCheck.charAt(i) == VAL_OF_SPACE_ASCII || strForCheck.charAt(i) == VAL_OF_QUOTE_ASCII
					|| strForCheck.charAt(i) == VAL_OF_APOSTROPHE_ASCII || strForCheck.charAt(i) == VAL_DASH_ASCII))
				throw new SlotRoomException(ROOM_NOT_VALID_ERROR);
		}

	}

	protected int FromStringToInt(String hour) { // casting the hour from string to int
		switch (hour) {
		case "07:00":
			return 7;
		case "08:00":
			return 8;
		case "09:00":
			return 9;
		case "10:00":
			return 10;
		case "11:00":
			return 11;
		case "12:00":
			return 12;
		case "13:00":
			return 13;
		case "14:00":
			return 14;
		case "15:00":
			return 15;
		case "16:00":
			return 16;
		case "17:00":
			return 17;
		case "18:00":
			return 18;
		case "19:00":
			return 19;
		case "20:00":
			return 20;
		case "21:00":
			return 21;
		case "22:00":
			return 22;
		default:
			return 23;

		}
	}

}
