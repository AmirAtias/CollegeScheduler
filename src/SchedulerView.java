import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SchedulerView extends BorderPane implements FinalsAndMsg, Days {
	private Model schedulerModel;
	private List<Course> allCourses;
	private List<Course> chosenCourses;
	private TextField scheduler[][] = new TextField[HOURS_SIZE][DAYS_SIZE];
	private Day allDays[] = new Day[DAYS_SIZE];

	public SchedulerView(Model schedulerModel) {
		this.schedulerModel = schedulerModel;
		initDays();
		allCourses = schedulerModel.getAllCourses();
		GridPane centerPane = initGridPane();
		setCenter(centerPane);
		GridPane bottomPane = initBottomPane();
		setBottom(bottomPane);
	}

	private GridPane initBottomPane() {
		GridPane bottomPane = new GridPane();
		chosenCourses = new ArrayList<>();
		ObservableList<Course> options = FXCollections.observableArrayList(allCourses);
		ComboBox<Course> cbAllCourses = new ComboBox<Course>(options);
		ComboBox<Integer> cbAllShows = new ComboBox<>();
		ComboBox<Slot> cbAllSlots = new ComboBox<>();
		cbAllCourses.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				cbAllShows.getItems().clear();//clear combo
				ObservableSet<Integer> options = FXCollections
						.observableSet(cbAllCourses.getValue().getAllShows().keySet());
				System.out.println(options);
				cbAllShows.getItems().addAll(options);
			}

		});
		cbAllShows.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				cbAllSlots.getItems().clear();////clear combo
				ObservableList<Slot> options = FXCollections.observableArrayList(new ArrayList<>(
						cbAllCourses.getValue().getAllShows().get(cbAllShows.getValue()).getAllSlots().values()));
				cbAllSlots.getItems().addAll(options);
			}

		});

		bottomPane.add(cbAllCourses, 0, 0);
		bottomPane.add(cbAllShows, 1, 0);
		bottomPane.add(cbAllSlots, 2, 0);
		return bottomPane;
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
		for (int i = 0; i < 7; i++) {
			allDays[i] = new Day(i + 1);
		}

	}

}
