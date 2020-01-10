package web.servlets.car;

import entity.car.Car;
import service.interfaces.car.ICarRegistering;
import web.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/carRegistration")
public class CarRegistration extends HttpServlet {
    private static ICarRegistering service = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Car car = ServletUtils.getCarFromRequest(req);
        service.registerCar(car);

    }
}
