package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CategoryActionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryAction.class);
        CategoryAction categoryAction1 = new CategoryAction();
        categoryAction1.setId(1L);
        CategoryAction categoryAction2 = new CategoryAction();
        categoryAction2.setId(categoryAction1.getId());
        assertThat(categoryAction1).isEqualTo(categoryAction2);
        categoryAction2.setId(2L);
        assertThat(categoryAction1).isNotEqualTo(categoryAction2);
        categoryAction1.setId(null);
        assertThat(categoryAction1).isNotEqualTo(categoryAction2);
    }
}
