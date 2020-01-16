package web.servlets.order;

import entity.order.Address;
import entity.order.Order;
import entity.user.User;
import service.interfaces.address.IAddressProviding;
import service.interfaces.order.IOrderCreating;
import service.interfaces.user.IUserService;
import web.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/orderCreation")
public class OrderCreationServlet extends HttpServlet {
    private static IOrderCreating orderService = null;
    private static IUserService userService = null;
    private static IAddressProviding addressProvider = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Order order = getOrder(req);
        orderService.createOrder(order);
        session.setAttribute("order", order);

    }

    private Order getOrder(HttpServletRequest req) {

        User passenger = getUserForOrder(req).orElse(null);
        Address from = getAddress("from", req);
        Address where = getAddress("where", req);
        String comment = ServletUtils.getParameterFromRequest("comment", req).orElse(null);

        return Order.builder()
                .passenger(passenger)
                .from(from)
                .where(where)
                .comment(comment)
                .build();
    }
    private Optional<User> getUserForOrder(HttpServletRequest req) {

        User passenger = (User) req.getSession().getAttribute("user");
        if (passenger == null) {

            passenger = ServletUtils.getUserFromRequest(req);

            Optional<User> res = userService.getUser(passenger);
            if( ! res.isPresent() ) res = userService.addUser(passenger);

            passenger = res.orElse(null);
        }

        return Optional.ofNullable(passenger);

    }
    private Address getAddress(String description, HttpServletRequest req) {

        String addressTitle = ServletUtils.getParameterFromRequest(description, req)
                .orElseThrow(() -> new NullPointerException("Parameter with '" + description +"' title is absent"));

        return addressProvider.getAddress(addressTitle)
                .orElseThrow(() -> new NullPointerException("The info about address is absent"));

    }

}
