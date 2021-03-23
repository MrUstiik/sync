package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CategoryActionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoryAction} and its DTO {@link CategoryActionDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategoryMapper.class })
public interface CategoryActionMapper extends EntityMapper<CategoryActionDTO, CategoryAction> {
    @Mapping(source = "categoryId.id", target = "categoryIdId")
    CategoryActionDTO toDto(CategoryAction categoryAction);

    @Mapping(source = "categoryIdId", target = "categoryId")
    CategoryAction toEntity(CategoryActionDTO categoryActionDTO);

    default CategoryAction fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoryAction categoryAction = new CategoryAction();
        categoryAction.setId(id);
        return categoryAction;
    }
}
