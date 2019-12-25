package web.command.impl;

import appException.controller.AppRequestParameterException;
import appException.service.AppServiceException;
import entity.car.Car;
import entity.order.Order;
import org.apache.log4j.Logger;
import service.impl.CarService;
import service.impl.OrderService;
import service.interfaces.ICreateOrder;
import service.interfaces.IGetCar;
import web.JspPages;
import web.command.Command;

import javax.servlet.http.HttpServletRequest;

class CreateOrderCommand extends AbstractCommand  implements Command {
    private static final Logger logger = Logger.getLogger(CreateOrderCommand.class);
    private ICreateOrder service = new OrderService();
    private IGetCar carService = new CarService();
    private JspPages nextPage = JspPages.ORDER_RESPONSE;

    CreateOrderCommand(HttpServletRequest req){
        super(req);
    }

    @Override
    public JspPages execute() throws AppRequestParameterException, AppServiceException {
        logger.debug("start execute()");

        int phone = getPhone();
        String from = getParameter("from", true);
        String where = getParameter("where", true);
        String comments = getParameter("comments", false);
        Order order = service.createOrder(phone, from, where, comments);
        Car car = order.getCar();
        req.getSession().setAttribute("car", car);

        logger.debug("finish execute()");
        return nextPage;

    }

}
