package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = VendorController.class)
public class VendorControllerTest {

    public static final long ID = 1L;
    public static final String VENDOR_NAME = "Vendor1 Name";
    @MockBean
    VendorService vendorService;
    @Autowired
    MockMvc mockMvc;
    VendorDTO vendorDTO_1;
    VendorDTO vendorDTO_2;

    @Before
    public void setUp() throws Exception {
        vendorDTO_1 = new VendorDTO(ID, VENDOR_NAME, VendorController.BASE_URL + "/1");
        vendorDTO_2 = new VendorDTO(2L, "Vendor 2", VendorController.BASE_URL + "/2");
    }

    @Test
    public void testGetAllVendors() throws Exception {
        List<VendorDTO> vendorDTOList = Arrays.asList(vendorDTO_1, vendorDTO_2);

        given(vendorService.getAllVendors()).willReturn(vendorDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendors", Matchers.hasSize(2)));
    }

    @Test
    public void testGetVendorById() throws Exception {
        given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO_1);

        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)));
    }

    @Test
    public void testCreateNewVendor() throws Exception  {
        given(vendorService.createNewVendor(any(VendorDTO.class))).willReturn(vendorDTO_1);

        mockMvc.perform(MockMvcRequestBuilders.post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(vendorDTO_1)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void testUpdateVendor() throws Exception  {
        given(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO_1);

        mockMvc.perform(MockMvcRequestBuilders.put(VendorController.BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AbstractRestControllerTest.asJsonString(vendorDTO_1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void testPatchVendor() throws Exception  {
        given(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO_1);

        mockMvc.perform(MockMvcRequestBuilders.patch(VendorController.BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AbstractRestControllerTest.asJsonString(vendorDTO_1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void testDeleteVendor() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders.delete(VendorController.BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        then(vendorService).should().deleteVendorById(anyLong());
    }

    @Test
    public void testGetVendorByIdNotFound() throws Exception  {
        given(vendorService.getVendorById(anyLong())).willThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}