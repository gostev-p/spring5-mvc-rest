package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    public static final String JOHN = "John";
    public static final String DOE = "Doe";
    public static final long ID = 1L;
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testListAllCustomers() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(ID);
        customer.setFirstname(JOHN);
        customer.setLastname(DOE);

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setFirstname("Joe");
        customer2.setLastname("Newman");

        List<CustomerDTO> customers = Arrays.asList(customer, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers", Matchers.hasSize(2)));
    }

    @Test
    public void testGetCustomerByFirstname() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstname(JOHN);
        customerDTO.setLastname(DOE);

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)));
    }

    @Test
    public void testCreateNewCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");
        customerDTO.setLastname("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname(customerDTO.getLastname());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.equalTo("Fred")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_url", Matchers.equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");
        customerDTO.setLastname("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname(customerDTO.getLastname());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.equalTo("Fred")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.equalTo("Flintstone")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_url", Matchers.equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname("Flintstone");
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AbstractRestControllerTest.asJsonString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.equalTo("Fred")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.equalTo("Flintstone")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_url", Matchers.equalTo("/api/v1/customers/1")));
    }
}