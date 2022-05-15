package Application;

import Controller.SystemController;
import View.AbstractSystemView;
import View.SystemView;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Manager;

public class Main extends Application {

    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Manager theModel = new Manager();
        AbstractSystemView view = new SystemView(primaryStage);
        SystemController controller = new SystemController(theModel, view);



    }
}
