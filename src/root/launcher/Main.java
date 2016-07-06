package root.launcher;

import root.service.Action;
import root.service.ServiceFactory;
import root.service.exception.ServiceException;
import root.view.View;
import root.view.ViewFactory;

public class Main {

    public static void main(String[] args) throws ServiceException {
        ServiceFactory factory = ServiceFactory.getInstance();
        Action action = factory.getAction();
        ViewFactory vFactory = ViewFactory.getInstance();
        View view = vFactory.getView(action);

       try{
           view.start(args[0]);
       } finally {
           view.closeStream();
       }
    }
}
