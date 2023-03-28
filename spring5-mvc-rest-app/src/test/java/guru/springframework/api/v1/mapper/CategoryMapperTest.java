package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryMapperTest {

    public static final String JOE = "Joe";
    private static final long ID = 1L;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {
        Category category = new Category();
        category.setId(ID);
        category.setName(JOE);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertNotNull(categoryDTO);
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(JOE, categoryDTO.getName());
    }

    @Test
    public void categoryDtoToCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID);
        categoryDTO.setName(JOE);

        Category category = CategoryMapper.INSTANCE.categoryDtoToCategory(categoryDTO);

        assertNotNull(category);
        assertEquals(Long.valueOf(ID), category.getId());
        assertEquals(JOE, category.getName());
    }
}