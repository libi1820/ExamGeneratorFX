package View;

import Listeners.SystemUIEventListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class SystemView implements AbstractSystemView{

private List<SystemUIEventListener> allListeners = new ArrayList<>();
private Scene menuScene, displayQuestionsScene, addQuestionScene;
public SystemView (Stage theStage) {

theStage.setTitle("Exam Generator");

VBox vbMenu = new VBox();
Button displayQuestionsButton = new Button("Display Questions");
displayQuestionsButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent actionEvent) {
        for (SystemUIEventListener l : allListeners) {
            l.displayQuestionToModel();
        }
    }
});
Button addQuestionButton = new Button("Add question");



vbMenu.getChildren().addAll(displayQuestionsButton, addQuestionButton);
vbMenu.setSpacing(5);
vbMenu.setPadding(new Insets(10));
theStage.setScene(new Scene(vbMenu, 750, 450));

theStage.show();




}
    @Override
    public void registerListener(SystemUIEventListener listener) {
        allListeners.add(listener);
    }




    @Override
    public void displayQuestions(String s) {
        JOptionPane.showMessageDialog(null,s);
      //  System.out.println(s);

    }
}
