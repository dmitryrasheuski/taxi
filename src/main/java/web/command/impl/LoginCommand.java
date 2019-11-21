package web.command.impl;

import appException.controller.AppRequestParameterException;
import appException.service.AppServiceException;
import entity.user.User;
import org.apache.log4j.Logger;
import service.impl.UserService;
import service.interfaces.ILogin;
import web.JspPages;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class LoginCommand extends AbstractCommand  implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);
    private ILogin service = new UserService();
    private JspPages nextPage = JspPages.ORDER;

    LoginCommand(HttpServletRequest req){
        super(req);
    }

    @Override
    public JspPages execute() throws AppRequestParameterException, AppServiceException {
        logger.debug("start execute()");

        int phone = getPhone();
        String password = getParameter("password", true);
        User user = service.login(phone, password);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        logger.debug("finish execute()");
        return nextPage;
    }
}
