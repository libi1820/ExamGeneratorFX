package Controller;
import Listeners.SystemEventListener;
import Listeners.SystemUIEventListener;
import View.AbstractSystemView;
import model.Manager;
public class SystemController implements SystemUIEventListener,SystemEventListener{
    private Manager model;
    private AbstractSystemView view;


    public SystemController(Manager model, AbstractSystemView view) {
        this.model = model;
        this.view = view;

        this.model.registerListeners(this);
        this.view.registerListener(this);
    //    this.model.getQuestions();


    }

    @Override
    public void displayQuestionInView(String s) {
        this.view.displayQuestions(s);

    }

    @Override
    public void addQuestionToView() {

    }

    @Override
    public void updateQuestionInView() {

    }

    @Override
    public void updateAnswerInView() {

    }

    @Override
    public void deleteAnswerInView() {

    }

    @Override
    public void generateManualExamInView() {

    }

    @Override
    public void generateAutomaticExamInView() {

    }

    @Override
    public void displayQuestionToModel() {
        model.getQuestions();
    }

    @Override
    public void addQuestionToModel() {

    }

    @Override
    public void updateQuestionToModel() {

    }

    @Override
    public void updateAnswerToModel() {

    }

    @Override
    public void deleteAnswerToModel() {

    }

    @Override
    public void generateManualExamToModel() {

    }

    @Override
    public void generateAutomaticExamToModel() {

    }

    @Override
    public void copyLastExam() {

    }
}
