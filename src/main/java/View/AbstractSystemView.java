package View;

import Listeners.SystemEventListener;
import Listeners.SystemUIEventListener;

public interface AbstractSystemView {
    void registerListener(SystemUIEventListener listener);
    void displayQuestions(String s);


}
