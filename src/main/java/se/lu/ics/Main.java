package se.lu.ics;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;  
import javafx.scene.Parent; 
import javafx.scene.Scene;  
import javafx.stage.Stage;
import se.lu.ics.controllers.MainViewController;
import se.lu.ics.controllers.ShipmentTabController;
import se.lu.ics.models.DataManager;
import se.lu.ics.models.DataService;
import se.lu.ics.models.ShipmentLogHandler;  

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        DataManager dataManager = DataManager.getInstance();
        DataService dataService = DataService.getInstance();
        ShipmentTabController stc = new ShipmentTabController();
        ShipmentLogHandler shipmentLogHandler = ShipmentLogHandler.getInstance();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Parent root = fxmlLoader.load();        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        MainViewController controller = fxmlLoader.getController();
        controller.populateTableView();
    }

    public static void main(String[] args) {
        launch(args);
    }


}