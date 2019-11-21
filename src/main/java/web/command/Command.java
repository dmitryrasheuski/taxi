package web.command;

import appException.controller.AppRequestParameterException;
import appException.service.AppServiceException;
import web.JspPages;

public interface Command {

    JspPages execute() throws AppRequestParameterException, AppServiceException;
}
