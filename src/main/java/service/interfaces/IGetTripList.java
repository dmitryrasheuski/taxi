package service.interfaces;

import appException.dao.AppSqlException;
import appException.service.AppServiceException;
import entity.order.Order;

import java.sql.SQLException;
import java.util.List;

public interface IGetTripList {

    List<Order> getTripList(long idUser) throws AppServiceException;
}
