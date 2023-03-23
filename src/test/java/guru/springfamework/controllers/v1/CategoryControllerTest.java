package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.services.CategoryService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryControllerTest {

    public static final long ID_1 = 1L;
    public static final String JIM = "Jim";
    public static final long ID_2 = 2L;
    public static final String BOB = "Bob";
    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testListCategories() throws Exception {
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(ID_1);
        categoryDTO1.setName(JIM);

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(ID_2);
        categoryDTO2.setName(BOB);

        List<CategoryDTO> categoryDTOS = Arrays.asList(categoryDTO1, categoryDTO2);

        when(categoryService.getAllCategories()).thenReturn(categoryDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories", Matchers.hasSize(2)));
    }

    @Test
    public void testGetByNameCategories() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID_1);
        categoryDTO.setName(JIM);

        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/Jim")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(JIM)));
    }
}