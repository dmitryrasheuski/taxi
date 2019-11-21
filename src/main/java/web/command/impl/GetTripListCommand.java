package web.command.impl;

import appException.controller.AppRequestParameterException;
import appException.service.AppServiceException;
import entity.order.Order;
import entity.user.User;
import org.apache.log4j.Logger;
import service.impl.OrderService;
import service.interfaces.IGetTripList;
import web.JspPages;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class GetTripListCommand extends AbstractCommand  implements Command {
    private static final Logger logger = Logger.getLogger(GetTripListCommand.class);
    private IGetTripList service = new OrderService();
    private JspPages nextPage = JspPages.TRIP_LIST;

    GetTripListCommand(HttpServletRequest req){
        super(req);
    }

    @Override
    public JspPages execute() throws AppRequestParameterException, AppServiceException {
        logger.debug("start execute()");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
           throw new AppRequestParameterException("user is't authenticated");
        }
        List<Order> orderList = service.getTripList(user.getId());
        req.setAttribute("orderListForPassenger", orderList);

        logger.debug("finish execute()");
        return nextPage;
    }
}
