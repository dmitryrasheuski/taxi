package web.command.impl;

import org.apache.log4j.Logger;
import web.JspPages;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class GoToCommand extends AbstractCommand implements Command {
    private static final Logger logger = Logger.getLogger(GoToCommand.class);

    GoToCommand(HttpServletRequest req){
        super(req);
    }

    @Override
    public JspPages execute() {
        return  getNextPage();
    }

    private JspPages getNextPage(){
        logger.debug("start execute() start getNextPage()");

        String pathInfo = req.getPathInfo();
        if (pathInfo != null){
            Pattern pattern = Pattern.compile(".*/(\\w+\\.jsp)");
            Matcher matcher = pattern.matcher(pathInfo);
            if(matcher.find()){
                String pageName = matcher.group(1);
                return JspPages.getJspPageByPageName(pageName);
            }
        }

        logger.debug("finish execute() finish getNextPage()");
        return JspPages.ORDER;
    }
}
