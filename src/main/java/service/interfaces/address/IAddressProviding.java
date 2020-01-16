package service.interfaces.address;

import entity.order.Address;

import java.util.Optional;

public interface IAddressProviding {
    /**
     * @param title title of address
     * @return Optional describing result of database query, otherwise return empty Optional
     */
    Optional<Address> getAddress(String title);
}
