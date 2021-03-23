package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CategoryAction;
import com.mycompany.myapp.repository.CategoryActionRepository;
import com.mycompany.myapp.service.dto.CategoryActionDTO;
import com.mycompany.myapp.service.mapper.CategoryActionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategoryAction}.
 */
@Service
@Transactional
public class CategoryActionService {
    private final Logger log = LoggerFactory.getLogger(CategoryActionService.class);

    private final CategoryActionRepository categoryActionRepository;

    private final CategoryActionMapper categoryActionMapper;

    public CategoryActionService(CategoryActionRepository categoryActionRepository, CategoryActionMapper categoryActionMapper) {
        this.categoryActionRepository = categoryActionRepository;
        this.categoryActionMapper = categoryActionMapper;
    }

    /**
     * Save a categoryAction.
     *
     * @param categoryActionDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoryActionDTO save(CategoryActionDTO categoryActionDTO) {
        log.debug("Request to save CategoryAction : {}", categoryActionDTO);
        CategoryAction categoryAction = categoryActionMapper.toEntity(categoryActionDTO);
        categoryAction = categoryActionRepository.save(categoryAction);
        return categoryActionMapper.toDto(categoryAction);
    }

    /**
     * Get all the categoryActions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CategoryActionDTO> findAll() {
        log.debug("Request to get all CategoryActions");
        return categoryActionRepository
            .findAll()
            .stream()
            .map(categoryActionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one categoryAction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoryActionDTO> findOne(Long id) {
        log.debug("Request to get CategoryAction : {}", id);
        return categoryActionRepository.findById(id).map(categoryActionMapper::toDto);
    }

    /**
     * Delete the categoryAction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoryAction : {}", id);
        categoryActionRepository.deleteById(id);
    }
}
