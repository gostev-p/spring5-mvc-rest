package guru.springframework.api.v1.mapper;

import guru.springframework.model.CustomerDTO;
import guru.springframework.domain.Customer;
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
        customer.setFirstname(JOHN);
        customer.setLastname(DOE);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertNotNull(customerDTO);
        assertEquals(JOHN, customerDTO.getFirstname());
        assertEquals(DOE, customerDTO.getLastname());
    }

    @Test
    public void customerDtoToCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(JOHN);
        customerDTO.setLastname(DOE);

        Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(customerDTO);

        assertNotNull(customer);
        assertEquals(JOHN, customer.getFirstname());
        assertEquals(DOE, customer.getLastname());
    }
}