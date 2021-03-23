package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CategoryAction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CategoryAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryActionRepository extends JpaRepository<CategoryAction, Long>, JpaSpecificationExecutor<CategoryAction> {}
