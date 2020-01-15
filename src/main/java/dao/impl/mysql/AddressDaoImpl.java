package dao.impl.mysql;

import dao.interfaces.AddressDao;
import entity.order.Address;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j
public class AddressDaoImpl extends AbstractDao<Address> implements AddressDao {
    private static final String getById = "SELECT id, title FROM address where id = ?";
    private static final String getByTitle = "SELECT id, title FROM address where title = ?";
    private static final String addAddress = "INSERT INTO address(title) VALUE (?)";

    AddressDaoImpl (MysqlDaoFactory factory){
        super(factory);
    }

    private Optional<Long> addAddress(Address address) throws SQLException {
        return addEntity(address,addAddress);
    }
    @Override
    public Optional<Address> getAddress(long id) throws SQLException {
        Object[] parameters = new Object[]{ id };

        return getEntity(parameters, getById).map((list) -> list.get(0));

    }
    @Override
    public Optional<Address> getAddress(String title) throws SQLException {
        Object[] parameters = new Object[]{title};
        Optional<Address> address;

        address = getEntity(parameters, getByTitle).map( (list) -> list.get(0) );
        if ( ! address.isPresent() ) address = addAddress( new Address(title) ).map( (id) -> new Address(id, title) );

        return address;

    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(String sqlInsert, Address entity) throws SQLException {
        PreparedStatement ps;

        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, entity.getTitle());

        return ps;

    }
    @Override
    Optional<List<Address>> handleResultSet(ResultSet rs) throws SQLException {
        if(!rs.next()) return Optional.empty();

        List<Address> list = new ArrayList<>();
        long id;
        String title;

        do{
            id = rs.getLong("id");
            title = rs.getString("title");
            list.add( new Address(id, title) );
        }while (rs.next());

        return Optional.of(list);
    }
}
