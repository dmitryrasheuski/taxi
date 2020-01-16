package service.impl.address;

import dao.interfaces.AddressDao;
import entity.order.Address;
import lombok.extern.log4j.Log4j;
import service.interfaces.address.IAddressProviding;

import java.sql.SQLException;
import java.util.Optional;

@Log4j
public class AddressProvider implements IAddressProviding {
    private AddressDao dao;

    public AddressProvider(AddressDao dao) {
        this.dao = dao;
    }

    @Override
    public Optional<Address> getAddress(String title) {
        if (title == null) return Optional.empty();

        Optional<Address> address = Optional.empty();
        try {
            address = dao.getAddress(title);
        } catch (SQLException ex) {
            log.error("searching Address with title: " + title, ex);
        }

        return address;

    }
}