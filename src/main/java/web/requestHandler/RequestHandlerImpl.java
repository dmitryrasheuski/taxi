package web.requestHandler;

import appException.controller.AppRequestParameterException;
import appException.service.AppServiceException;
import web.JspPages;
import web.command.Command;

class RequestHandlerImpl implements RequestHandler{

    private RequestHandlerImpl(){}
    static RequestHandler getInstance(){
        return new RequestHandlerImpl();
    }

    @Override
    public JspPages handle(Command command) throws AppRequestParameterException, AppServiceException {
        return command.execute();
    }

}
