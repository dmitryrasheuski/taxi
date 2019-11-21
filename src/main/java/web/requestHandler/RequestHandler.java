package web.requestHandler;

import appException.controller.AppRequestParameterException;
import appException.service.AppServiceException;
import web.JspPages;
import web.command.Command;

public interface RequestHandler {

    JspPages handle(Command command) throws AppRequestParameterException, AppServiceException;

    static RequestHandler getHandler(){
        return RequestHandlerImpl.getInstance();
    }
}
