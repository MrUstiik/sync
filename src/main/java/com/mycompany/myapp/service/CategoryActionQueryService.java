package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CategoryAction;
import com.mycompany.myapp.repository.CategoryActionRepository;
import com.mycompany.myapp.service.dto.CategoryActionCriteria;
import com.mycompany.myapp.service.dto.CategoryActionDTO;
import com.mycompany.myapp.service.mapper.CategoryActionMapper;
import io.github.jhipster.service.QueryService;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for executing complex queries for {@link CategoryAction} entities in the database.
 * The main input is a {@link CategoryActionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoryActionDTO} or a {@link Page} of {@link CategoryActionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoryActionQueryService extends QueryService<CategoryAction> {
    private final Logger log = LoggerFactory.getLogger(CategoryActionQueryService.class);

    private final CategoryActionRepository categoryActionRepository;

    private final CategoryActionMapper categoryActionMapper;

    public CategoryActionQueryService(CategoryActionRepository categoryActionRepository, CategoryActionMapper categoryActionMapper) {
        this.categoryActionRepository = categoryActionRepository;
        this.categoryActionMapper = categoryActionMapper;
    }

    /**
     * Return a {@link List} of {@link CategoryActionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoryActionDTO> findByCriteria(CategoryActionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoryAction> specification = createSpecification(criteria);
        return categoryActionMapper.toDto(categoryActionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategoryActionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoryActionDTO> findByCriteria(CategoryActionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoryAction> specification = createSpecification(criteria);
        return categoryActionRepository.findAll(specification, page).map(categoryActionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoryActionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoryAction> specification = createSpecification(criteria);
        return categoryActionRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoryActionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategoryAction> createSpecification(CategoryActionCriteria criteria) {
        Specification<CategoryAction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategoryAction_.id));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUuid(), CategoryAction_.uuid));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CategoryAction_.name));
            }
            if (criteria.getMcc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMcc(), CategoryAction_.mcc));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryId(), CategoryAction_.categoryId));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), CategoryAction_.enabled));
            }
            if (criteria.getIconUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIconUrl(), CategoryAction_.iconUrl));
            }
            if (criteria.getDefaultOrderId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDefaultOrderId(), CategoryAction_.defaultOrderId));
            }
            if (criteria.getAddedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAddedDate(), CategoryAction_.addedDate));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), CategoryAction_.updatedDate));
            }
            if (criteria.getRegions() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegions(), CategoryAction_.regions));
            }
            if (criteria.getTags() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTags(), CategoryAction_.tags));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProcessId(), CategoryAction_.processId));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), CategoryAction_.source));
            }
            if (criteria.getActionType() != null) {
                specification = specification.and(buildSpecification(criteria.getActionType(), CategoryAction_.actionType));
            }
            if (criteria.getCategoryIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoryIdId(),
                            root -> root.join(CategoryAction_.categoryId, JoinType.LEFT).get(Category_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
