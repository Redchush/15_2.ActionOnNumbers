package root.service;

import root.service.impl.ActionImpl;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){

        if (instance == null)
            synchronized (ServiceFactory.class){
                if (instance == null)
                    instance = new ServiceFactory();
            }
        return instance;
    }

    public Action getAction(){
        return new ActionImpl();
    }
}
