package dao.interfaces;

import dao.impl.mysql.MysqlDaoFactory;
import dao.impl.orm.jpa.HibernateDataSource;
import dao.impl.orm.jpa.JpaDaoFactory;
import entity.order.Address;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public class AddressDaoTest {
    private AddressDao addressDao;
    private Address address;

    @Before
    public void setUp() throws Exception {
        addressDao = new JpaDaoFactory(HibernateDataSource.getInstance()).getAddressDao();
    }

    @After
    public void tearDown() throws Exception {
        if (address != null) addressDao.removeAddress(address);
    }

    @Test
    public void addAddressTest() throws SQLException {
        String title = "addAddressTest";
        address = createAndAddToDbNewAddress(title);

        try {
            addressDao.addAddress( new Address(title) );
        } catch (Exception ex) {
            Assert.assertEquals( ex.getClass() , SQLIntegrityConstraintViolationException.class );
        }

        Assert.assertEquals( address.getTitle() , title.toUpperCase() );
    }

    @Test
    public void deleteAddress() throws SQLException {
        address = createAndAddToDbNewAddress("deleteAddressTest");

        boolean success = addressDao.removeAddress(address);
        Assert.assertTrue(success);

        Optional<Address> receivedAddress = addressDao.getAddress( address.getTitle() );
        Assert.assertFalse( receivedAddress.isPresent() );
    }

    @Test
    public void getAddressById() throws SQLException {
        address = createAndAddToDbNewAddress("getAddressByIdTest");

        Address receivedAddress = addressDao.getAddress( address.getId() ).get();
        Assert.assertEquals(address, receivedAddress);
    }

    @Test
    public void getAddressByTitle() throws SQLException {
        String title = "getAddressByTitleTest";
        address = createAndAddToDbNewAddress(title);

        Address receivedAddress = addressDao.getAddress(title).get();
        Assert.assertEquals(address, receivedAddress);
    }

    private Address createAndAddToDbNewAddress(String title) throws SQLException {
        Address address = new Address(title);
        return addressDao.addAddress(address).get();
    }


}