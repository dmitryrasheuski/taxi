package web.command.impl;

import appException.controller.AppRequestParameterException;
import appException.service.AppServiceException;
import entity.user.User;
import org.apache.log4j.Logger;
import service.impl.UserService;
import service.interfaces.IRegistryUser;
import web.JspPages;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class RegistryCommand extends AbstractCommand  implements Command {
    private static final Logger logger = Logger.getLogger(RegistryCommand.class);
    private IRegistryUser service = new UserService();
    private JspPages nextPage = JspPages.REGISTRY_RESPONSE;

    RegistryCommand(HttpServletRequest req){
       super(req);
    }

    @Override
    public JspPages execute() throws AppRequestParameterException, AppServiceException {
        logger.debug("start execute()");

        User user = userByRequest();
        user = service.registryUser(user);
        req.getSession().setAttribute("user", user);

        logger.debug("finish execute()");
        return nextPage;

    }

}
