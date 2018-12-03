import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private Model SchedulerModel = new Model();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Controller schedulerController = new Controller(0, SchedulerModel, primaryStage);

	}

	public static void main(String[] args) {
		launch(args);

	}
}
