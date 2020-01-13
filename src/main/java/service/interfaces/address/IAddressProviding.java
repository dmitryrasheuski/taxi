package service.interfaces.address;

import entity.order.Address;

import java.util.Optional;

public interface IAddressProviding {
    Optional<Address> getAddress(String title);
}
