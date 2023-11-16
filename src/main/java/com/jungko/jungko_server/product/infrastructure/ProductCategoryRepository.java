package com.jungko.jungko_server.product.infrastructure;

import com.jungko.jungko_server.product.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.util.List;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT c FROM ProductCategory c LEFT JOIN c.parentCategory p ORDER BY p.id ASC NULLS FIRST, c.id ASC")
    List<ProductCategory> findAllOrderByParentIdAscNullsFirstCategoryIdAsc();

    @Query("SELECT c FROM ProductCategory c ORDER BY c.parentCategory.id ")
    List<ProductCategory> findAllOrderByParentId();
}
