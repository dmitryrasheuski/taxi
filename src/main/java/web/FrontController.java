package web;

import appException.controller.AppRequestParameterException;
import appException.service.AppServiceException;
import dao.interfaces.DaoFactory;
import org.apache.log4j.Logger;
import web.command.Command;
import web.command.impl.CommandTypes;
import web.requestHandler.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/frontController/*")
public class FrontController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(FrontController.class);

    @Override
    public void init() throws ServletException {
        DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Start service(req, resp). URI: " + req.getRequestURI());

        RequestHandler requestHandler = RequestHandler.getHandler();
        Command  command = CommandTypes.getCommand(req);
        try {
            JspPages nextPage = requestHandler.handle(command);
            HttpSession session = req.getSession();
            session.setAttribute("previousPage", session.getAttribute("nextPage"));
            session.setAttribute("nextPage", nextPage);
        }catch (AppServiceException | AppRequestParameterException ex){
            logger.info("service(req, resp) catch (AppServiceException | AppRequestParameterException ex) {} : requestHandler.handle(Command) throw exception", ex);
            req.setAttribute("exception", ex);
        }

        logger.debug("Finish service(req, resp). URI: " + req.getRequestURI());
        logger.debug("_______________________________________________________________________________________________________________________");
        req.getRequestDispatcher(JspPages.DEFAULT.getPagePath()).forward(req,resp);
    }

    @Override
    public void destroy() {
        DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL).closeDatasource();
    }
}
