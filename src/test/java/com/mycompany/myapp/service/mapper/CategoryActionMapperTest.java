package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryActionMapperTest {

    private CategoryActionMapper categoryActionMapper;

    @BeforeEach
    public void setUp() {
        categoryActionMapper = new CategoryActionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(categoryActionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(categoryActionMapper.fromId(null)).isNull();
    }
}
