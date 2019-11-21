package service.interfaces;

import appException.dao.AppSqlException;
import appException.service.AppServiceException;
import entity.order.Order;

import java.sql.SQLException;

public interface ICreateOrder {

    Order createOrder(int phone, String from, String where, String comments) throws AppServiceException;
}
