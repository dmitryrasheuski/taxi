package dao.impl.orm.jpa;

import entity.order.Address;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class AddressDao extends AbstractDao<Address> implements dao.interfaces.AddressDao {
    private static final String getByTitle = "SELECT a FROM Address a WHERE a.title = :title";

    AddressDao(JpaDaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public Optional<Address> addAddress(Address address) throws SQLException {
        address.setTitle(
                address.getTitle().toUpperCase()
        );

        return addEntity(address);
    }

    @Override
    public boolean removeAddress(Address address) throws SQLException {
        return removeEntity(Address.class, address.getId());
    }

    @Override
    public Optional<Address> getAddress(long id) throws SQLException {
        return getEntity(Address.class, id);
    }

    @Override
    public Optional<Address> getAddress(String title) throws SQLException {
        Map<String, Object> parameters = new LinkedHashMap<>(1);
        parameters.put("title", title.toUpperCase() );

        List<Address> list = getEntities(getByTitle, parameters);

        return list.isEmpty() ?
                Optional.empty() :
                Optional.of( list.get(0) );
    }
}
