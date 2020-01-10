package web.servlets.car;

import entity.car.Car;
import service.interfaces.car.ICarActivating;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/carDeactivation")
public class CarDeactivationServlet extends HttpServlet {
    private static ICarActivating service = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Car car = (Car) session.getAttribute("Car");
        service.deactivateCar(car);
        session.removeAttribute("car");

    }
}
