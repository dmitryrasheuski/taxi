package dao.interfaces;

import entity.order.Address;

import java.util.Optional;

public interface AddressDao {
    Optional<Address> getAddress(long id);
    Optional<Address> getAddress(String title);
}
