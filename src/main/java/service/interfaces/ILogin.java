package service.interfaces;

import appException.service.AppServiceException;
import entity.user.User;


public interface ILogin {
    User login(int phone, String password) throws AppServiceException;
}
