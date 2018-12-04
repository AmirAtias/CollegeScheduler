import java.util.ArrayList;
import java.util.Iterator;
import exceptions.CourseCodeException;
import exceptions.CourseCreditException;
import exceptions.CourseNameException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller  implements FinalsAndMsg,Days,Hours { // the controller
	Model theModel;
	int schedulerCounter;
	ArrayList<Course> allCourses;
	private TextField tfCourseName = new TextField();
	private TextField tfCourseCode = new TextField();
	private TextField tfCredits = new TextField();
	private ComboBox<String> showsComBox = new ComboBox<>(numberOfShowsObservableList());
	private ComboBox<String> slotsComBox = new ComboBox<>(numberOfShowsObservableList());//same comboBOX like the show ComboBox
	private ComboBox<Days.AllDays>daysComBox=new ComboBox<>();
	private ComboBox<String>hoursComBox=new ComboBox<>(hoursObservableList());
	private Button createCourse = new Button("Create Course!");
	private Course tmpCourse;
	private Show tempShow;//
	private SlotDetails tempSlot;
	private String courseName;
	private int courseCode;
	private double credits;
	private Stage primaryStage;
	private Scene showScene;
	private Scene slotsScene;
	private Button btAddCourse = new Button("add Course");
	private Button btCreateScheduler = new Button("create Scheduler");
	private Button BtCreateSlots=new Button("create slots");
	private int numOfShows;
	private int numOfSlots;


	public Controller(int schedulerCounter, Model courses, Stage primaryStage) {
		this.schedulerCounter = schedulerCounter;
		this.theModel = courses;
		this.primaryStage = primaryStage;
		CreatePrimaryWindow();
		showScene=initShowWindow();
		allCourses = new ArrayList<>();
        daysComBox.getItems().setAll(Days.AllDays.values());
	}

	private void CreatePrimaryWindow() {
		FlowPane pane = new FlowPane();
		pane.setPadding(new Insets(30, 25, 25, 25));
		pane.setHgap(30);
		pane.getChildren().add(btAddCourse);
		pane.getChildren().add(btCreateScheduler);
		btAddCourse.setOnAction(e -> {
			createCourseWindow();
		});
		// btView.setOnAction(e -> createNewView());
		Scene scene = new Scene(pane, 325, 75);
		primaryStage.setScene(scene);
		primaryStage.show(); // Display the stage
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setResizable(false);
		primaryStage.setTitle("College Scheduler");
		// primaryStage.setOnCloseRequest(e -> Platform.exit());

	}

	private void createCourseWindow() {
		BorderPane CreateCoursePane = new BorderPane();
		CreateCoursePane.setPadding(new Insets(15, 15, 15, 15));
		GridPane panel1 = new GridPane();
		tfCourseName.setMaxWidth(250);
		tfCourseCode.setMaxWidth(250);
		tfCredits.setMaxWidth(250);
		showsComBox.setMaxWidth(250);
		createCourse.setMaxWidth(250);
		showsComBox.setValue((String) (numberOfShowsObservableList().get(0)));
		panel1.add(new Label("CourseName"), 0, 0);
		panel1.add(new Label("CoursCode"), 0, 1);
		panel1.add(new Label("Credits"), 0, 2);
		panel1.add(new Label("number of shows"), 0, 3);
		panel1.setHgap(10);
		panel1.setVgap(30);
		GridPane panel2 = new GridPane();
		panel2.add(tfCourseName, 0, 0);
		panel2.add(tfCourseCode, 0, 1);
		panel2.add(tfCredits, 0, 2);
		panel2.add(showsComBox, 0, 3);
		panel2.add(createCourse, 0, 4);
		panel2.setHgap(10);
		panel2.setVgap(20);
		CreateCoursePane.setCenter(panel2);
		CreateCoursePane.setLeft(panel1);
		createCourse.setOnAction(e -> createCourse());
		primaryStage.setScene(new Scene(CreateCoursePane, 400, 275));
		primaryStage.setTitle("add a new course");
	}
	public String getCourseName() {
		return courseName;
	}
	private void setCourseName(String courseName) throws CourseNameException { // can use this function only from
																				// tfCourseName
		if (!CheckCourseName(courseName))
			throw new CourseNameException(ERROR_COURSE_NAME_NOT_VALID);
		this.courseName = courseName;

	}
	
	public int getCourseCode() {
		return courseCode;
	}
	private void setCourseCode(String strCourseCode) throws CourseCodeException {// can use this function only from
																					// tfCourseCode
		if (!stringContainDigitsOnly(strCourseCode))
			throw new CourseCodeException(ERROR_NOT_ONLY_DIGITS);
		int courseCode = Integer.parseInt(strCourseCode);
		if (!allCourses.isEmpty()) {
			Iterator<Course> it = allCourses.iterator();
			while (it.hasNext()) {
				if (it.next().getCourseCode() == courseCode)
					throw new CourseCodeException(ERROR_COURSE_EXIST);
			}
		}
		this.courseCode = courseCode;

	}
	public double getCredits() {
		return credits;
	}
	private void setCredits(String strCredits) throws CourseCreditException {// can use this function only from
																				// tfCredits
		if (!stringContainDigitsOnly(strCredits))
			throw new CourseCreditException(ERROR_NOT_ONLY_DIGITS);

		double credits = Double.parseDouble(strCredits);
		
		if (credits < MIN_VAL_OF_CREDITS || credits > MAX_VAL_OF_CREDITS) {
			throw new CourseCreditException(ERROR_NOT_IN_CREDITS_RANGE);
		}
		this.credits = credits;
	}

	private ObservableList<String> numberOfShowsObservableList() {
		ObservableList<String> showsOptions = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7");
		return showsOptions;

	}
	private ObservableList<String> hoursObservableList() {
		ObservableList<String> hoursOptions = FXCollections.observableArrayList("07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
				"14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00");
		return hoursOptions;
	}
	


	private void setNumOfShows(int numOfShows) {// can use this function only from slotsComBox
		this.numOfShows = numOfShows;
	}

	private void createCourse() {
		if (tfCourseName.getText() == null || tfCourseName.getText().trim().isEmpty() || tfCourseCode.getText() == null
				|| tfCourseCode.getText().trim().isEmpty() || tfCredits.getText() == null
				|| tfCredits.getText().trim().isEmpty()) {// check if one of the text field is empty
			System.out.println("please enter all details!");
		} else {
			try {
			setCourseName(tfCourseName.getText().trim());
			setCourseCode(tfCourseCode.getText().trim());
			setCredits(tfCredits.getText().trim());
			setNumOfShows(Integer.parseInt(showsComBox.getValue()));
			tmpCourse = new Course(getCredits(),getCourseCode(),getNumOfShows(),getCourseName());
			initCourse();
			}catch(CourseNameException ex) {
			  System.out.println(ex.getMessage());
			  tfCourseName.clear();
			}catch(CourseCodeException ex) {
				System.out.println(ex.getMessage());
				  tfCourseCode.clear();
			}catch(CourseCreditException ex) {
				System.out.println(ex.getMessage());
				tfCredits.clear();
			}
		}

	}

	private void initCourse() {
		for(int i=1;i<=getNumOfShows();i++)
			primaryStage.setTitle("create show number "+ i);
			primaryStage.setScene(showScene);
			for( int j=0;j<getNumOfSlots();j++)
				System.out.println("amirrr");
		
	}

	private Scene initShowWindow() {
		BorderPane showPane=new BorderPane();
		showPane.setPadding(new Insets(15, 15, 15, 15));
		GridPane buttonsPane=new GridPane();
		slotsComBox.setValue((String) (numberOfShowsObservableList().get(0)));
		buttonsPane.setPadding(new Insets(15, 15, 15, 15));
		buttonsPane.add(slotsComBox, 0, 0);
		buttonsPane.add(BtCreateSlots, 0,1);
		showPane.setCenter(buttonsPane);
		BtCreateSlots.setOnAction(e->setNumOfSlots(Integer.parseInt(showsComBox.getValue())));
		return new Scene(showPane,400,200);
		
		
	}

	private void initSlotWindow() {
		// TODO Auto-generated method stub
	
	}

	public int getNumOfShows() {
		return numOfShows;
	}
	public int getNumOfSlots() {
		return numOfSlots;
	}

	public void setNumOfSlots(int numOfSlots) {
		this.numOfSlots = numOfSlots;
	}
	private boolean CheckCourseName(String strForCheck) {
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

	private boolean stringContainDigitsOnly(String strForCheck) {
		strForCheck = strForCheck.trim();
		for (int i = 0; i < strForCheck.length(); i++) {
			if (strForCheck.charAt(i) < MIN_VAL_OF_ASCII_DIGIT || strForCheck.charAt(i) > MAX_VAL_OF_ASCII_DIGIT)
				return false;
		}
		return true;

	}
}
