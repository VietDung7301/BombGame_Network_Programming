package application;
import model.User;
import netwoork.Connect;
import controller.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Connect connect=new Connect();
			String[] result=connect.SendAndRecvData("#c000#", 5500);
			User user=new User(result[3], result[2]);
			ViewManager manager = new ViewManager(user,connect);
			primaryStage = manager.getMainStage();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
