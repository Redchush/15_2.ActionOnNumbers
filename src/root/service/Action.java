package root.service;

import root.service.exception.ServiceException;

import java.io.WriteAbortedException;

public interface Action {

    double doAction(String source) throws ServiceException, WriteAbortedException;

}
