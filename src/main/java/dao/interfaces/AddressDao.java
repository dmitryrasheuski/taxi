package dao.interfaces;

import entity.order.Address;

import java.sql.SQLException;
import java.util.Optional;

public interface AddressDao {
    Optional<Address> getAddress(long id) throws SQLException;
    Optional<Address> getAddress(String title) throws SQLException;
}
