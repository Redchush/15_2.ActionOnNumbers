package root.view;


import root.service.Action;
import root.view.impl.ConsoleView;

public class ViewFactory {

    private static ViewFactory instance;

    private ViewFactory(){}

    public static ViewFactory getInstance() {
        if (instance == null)
        {
            instance = new ViewFactory();
        }
        return instance;
    }

    public View getView(Action action) {
       return new ConsoleView(action);
    }
}

