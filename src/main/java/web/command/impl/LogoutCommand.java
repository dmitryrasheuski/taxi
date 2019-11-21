package web.command.impl;

import org.apache.log4j.Logger;
import web.JspPages;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;

class LogoutCommand extends AbstractCommand  implements Command {
    private static final Logger logger = Logger.getLogger(LogoutCommand.class);
    private JspPages nextPage = JspPages.ORDER;

    LogoutCommand(HttpServletRequest req){
        super(req);
    }

    @Override
    public JspPages execute() {
        logger.debug("start execute()");
        req.getSession().invalidate();
        logger.debug("finish execute()");
        return nextPage;
    }
}
