import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exceptions.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SchedulerView extends BorderPane implements FinalsAndMsg, Days {
	private Model schedulerModel;
	private ObservableList<Course> allCourses;
	private ObservableList<Course> chosenCourses;
	private TextField scheduler[][] = new TextField[HOURS_SIZE][DAYS_SIZE];
	private Day allDays[] = new Day[DAYS_SIZE];
	ComboBox<Course> cbAllCourses = new ComboBox<>();
	ComboBox<Integer> cbAllShows = new ComboBox<>();
	ComboBox<Slot> cbAllSlots = new ComboBox<>();
	ComboBox<Course> cbChosenCourse = new ComboBox<>();
	Button btChooseCourse = new Button("Choose course");
	Button btRemoveCourse = new Button("Remove Course");
	int chosenShow;

	public SchedulerView(Model schedulerModel) {
		this.schedulerModel = schedulerModel;
		initDays();
		chosenShow = DEFAULT_NEG_VAL;
		allCourses = FXCollections.observableArrayList(this.schedulerModel.getAllCourses());
		GridPane centerPane = initGridPane();
		setCenter(centerPane);
		GridPane bottomPane = initBottomPane();
		setBottom(bottomPane);

	}

	private GridPane initBottomPane() {

		GridPane bottomPane = new GridPane();
		chosenCourses = FXCollections.observableArrayList(new ArrayList<>());
		cbAllCourses.getItems().addAll(allCourses);
		cbChosenCourse.getItems().addAll(chosenCourses);
		initCbAllCourses();
		initAllShows();
		initChooseCourseButton();
		initRemoveButton();
		bottomPane.add(cbAllCourses, 0, 0);
		bottomPane.add(cbAllShows, 1, 0);
		bottomPane.add(cbAllSlots, 2, 0);
		bottomPane.add(btChooseCourse, 3, 0);
		bottomPane.add(new Label("chosen Courses"), 4, 0);
		bottomPane.add(cbChosenCourse, 6, 0);
		bottomPane.add(btRemoveCourse, 7, 0);
		return bottomPane;
	}

	private void initRemoveButton() {
		btRemoveCourse.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					if (chosenCourses.isEmpty())
						throw new CourseListEmptyException(COURSE_LIST_EMPTY);
					Course courseToRemove = cbChosenCourse.getValue();
					if (courseToRemove == null)
						throw new SelectCourseException(SELECT_COURSE_ERROR);
					removeFromChosenCourse(courseToRemove);
				} catch (CourseListEmptyException e) {
					showAlert(ERROR, e.getMessage(), AlertType.ERROR);
				} catch (SelectCourseException e) {
					showAlert(ERROR, e.getMessage(), AlertType.ERROR);

				}

			}

		});

	}

	private void removeFromChosenCourse(Course courseToRemove) {
		Collection<Slot> allSlots = courseToRemove.getShowById(courseToRemove.getChosenShow()).getAllSlots().values();
		for (Slot temp : allSlots) {
			for (int i = temp.getStartHour(); i < temp.getFinishHour(); i++) {
				allDays[temp.getDay() - 1].removeCourse(i);
				scheduler[i - MIN_HOUR][temp.getDay() - 1].clear();
			}
		}
		courseToRemove.setChosenShow(DEFAULT_NEG_VAL);// remove chosen show
		chosenCourses.remove(courseToRemove);
		allCourses.add(courseToRemove);
		cbAllCourses.getItems().clear();
		cbChosenCourse.getItems().clear();
		cbAllCourses.getItems().addAll(allCourses);
		cbChosenCourse.getItems().addAll(chosenCourses);
		cbAllShows.getItems().clear();
		cbAllSlots.getItems().clear();

	}

	private void initChooseCourseButton() {
		btChooseCourse.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Course CourseToAdd = cbAllCourses.getValue();
				try {
					if (CourseToAdd != null) {
						if (cbAllShows.getValue() == null)
							throw new ShowNullException(SELECT_SHOW_ERROR);
						Show allSlots = CourseToAdd.getAllShows().get(cbAllShows.getValue());

						searchConflicts(allSlots);
					} else if (allCourses.isEmpty())
						throw new CourseListEmptyException(COURSE_LIST_EMPTY);
					else
						throw new SelectCourseException(SELECT_COURSE_ERROR);
				} catch (CourseListEmptyException e) {
					showAlert(ERROR, e.getMessage(), AlertType.ERROR);

				} catch (SelectCourseException e) {
					showAlert(ERROR, e.getMessage(), AlertType.ERROR);

				} catch (SlotHoursException e) {
					showAlert(ERROR, e.getMessage(), AlertType.ERROR);

				} catch (ShowNullException e) {
					showAlert(ERROR, e.getMessage(), AlertType.ERROR);

				}
			}
		});
	}

	private void searchConflicts(Show allSlots) throws SlotHoursException {
		for (Slot temp : allSlots.getAllSlots().values()) {
			for (int startHour = temp.getStartHour(); startHour < temp.getFinishHour(); startHour++) {
				if (allDays[temp.getDay() - 1].getHour(startHour).getCourseCode() != -1) {
					throw new SlotHoursException(HOUR_OUCCPIED);

				}
			}
		}
		chosenShow = cbAllShows.getValue();// set chosen show
		addCoursetoScheduler(allSlots); // add course
	}

	private void addCoursetoScheduler(Show allSlots) {

		Course courseToAdd = cbAllCourses.getValue();
		courseToAdd.setChosenShow(chosenShow);
		for (Slot temp : allSlots.getAllSlots().values()) {
			for (int startHour = temp.getStartHour(); startHour < temp.getFinishHour(); startHour++) {
				allDays[temp.getDay() - 1].setCourse(startHour, courseToAdd.getCourseName(),
						courseToAdd.getCourseCode(), temp.getRoom());
				scheduler[startHour - MIN_HOUR][temp.getDay() - 1]
						.setText(allDays[temp.getDay() - 1].getHour(startHour).toString());
			}
		}
		chosenCourses.add(courseToAdd);

		allCourses.remove(courseToAdd);
		cbAllCourses.getItems().clear();
		cbChosenCourse.getItems().clear();
		cbAllCourses.getItems().addAll(allCourses);
		cbChosenCourse.getItems().addAll(chosenCourses);
		cbAllShows.getItems().clear();
		cbAllSlots.getItems().clear();
		chosenShow = DEFAULT_NEG_VAL;// clear chosenShow
	}

	private void initAllShows() {
		cbAllShows.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cbAllSlots.getItems().clear();//// clear combo
				if (cbAllShows.getValue() != null) {
					ObservableList<Slot> options = FXCollections.observableArrayList(new ArrayList<>(
							cbAllCourses.getValue().getAllShows().get(cbAllShows.getValue()).getAllSlots().values()));
					cbAllSlots.getItems().addAll(options);
				}
			}

		});

	}

	private void initCbAllCourses() {
		cbAllCourses.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (cbAllCourses.getValue() != null) {
					cbAllShows.getItems().clear();// clear combo
					ObservableSet<Integer> options = FXCollections
							.observableSet(cbAllCourses.getValue().getAllShows().keySet());
					cbAllShows.getItems().addAll(options);
				}
			}

		});
	}

	private GridPane initGridPane() {
		GridPane gPane = new GridPane();
		int i = 1, j;
		for (AllDays temp : Days.AllDays.values()) {
			TextField tf = new TextField(temp.name());
			tf.setEditable(false);
			tf.setAlignment(Pos.CENTER);
			tf.setStyle("-fx-text-inner-color: blue;");
			tf.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
			gPane.add(tf, i++, 0);
		}
		i = 7;
		for (j = 1; j <= HOURS_SIZE; j++) {
			TextField tf = new TextField(Integer.toString(i++) + ":00");
			tf.setEditable(false);
			tf.setStyle("-fx-text-inner-color: red;");
			tf.setFont(Font.font("Ariel", FontWeight.BOLD, 14));
			tf.setAlignment(Pos.CENTER);
			gPane.add(tf, 0, j);

		}
		for (i = 0; i < HOURS_SIZE; i++) {
			for (j = 0; j < DAYS_SIZE; j++) {
				scheduler[i][j] = new TextField();
				scheduler[i][j].setEditable(false);
				gPane.add(scheduler[i][j], j + 1, i + 1);
			}
		}
		return gPane;
	}

	private void initDays() {
		for (int i = 0; i < DAYS_SIZE; i++) {
			allDays[i] = new Day(i + 1);
		}

	}

	private void showAlert(String headerText, String contentText, AlertType type) {
		Alert viewAlert = new Alert(type);
		viewAlert.setHeaderText(headerText);
		viewAlert.setContentText(contentText);
		viewAlert.showAndWait();

	}

}
