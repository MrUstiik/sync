package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CategoryActionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryActionDTO.class);
        CategoryActionDTO categoryActionDTO1 = new CategoryActionDTO();
        categoryActionDTO1.setId(1L);
        CategoryActionDTO categoryActionDTO2 = new CategoryActionDTO();
        assertThat(categoryActionDTO1).isNotEqualTo(categoryActionDTO2);
        categoryActionDTO2.setId(categoryActionDTO1.getId());
        assertThat(categoryActionDTO1).isEqualTo(categoryActionDTO2);
        categoryActionDTO2.setId(2L);
        assertThat(categoryActionDTO1).isNotEqualTo(categoryActionDTO2);
        categoryActionDTO1.setId(null);
        assertThat(categoryActionDTO1).isNotEqualTo(categoryActionDTO2);
    }
}
