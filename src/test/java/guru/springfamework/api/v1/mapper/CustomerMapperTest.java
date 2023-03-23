package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final long ID = 1L;
    public static final String JOHN = "John";
    public static final String DOE = "Doe";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(JOHN);
        customer.setLastname(DOE);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertNotNull(customerDTO);
        assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(JOHN, customerDTO.getFirstname());
        assertEquals(DOE, customerDTO.getLastname());
    }
}