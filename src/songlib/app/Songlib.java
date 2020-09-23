package songlib.app;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import songlib.view.SonglibController;

public class Songlib extends Application{
	/**
	 * Starting the Application
	 * @param primaryStage
	 */
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlib/view/songlib.fxml"));
		
		GridPane root = (GridPane)loader.load();
		SonglibController listController = loader.getController();
		listController.init(primaryStage);
		
		Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Song Library");
        primaryStage.setResizable(false);
        
        //when the stage is closed
		primaryStage.setOnCloseRequest(event -> { 
		listController.close(); });
		 
        primaryStage.show();
		
	}
	/**
	 * Launching the stage
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
