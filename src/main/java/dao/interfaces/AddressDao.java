package dao.interfaces;

import entity.order.Address;

import java.sql.SQLException;
import java.util.Optional;

public interface AddressDao {
    Optional<Address> addAddress(Address address) throws SQLException;
    boolean removeAddress(Address address) throws SQLException;
    Optional<Address> getAddress(long id) throws SQLException;
    Optional<Address> getAddress(String title) throws SQLException;

}
