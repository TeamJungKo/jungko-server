package com.jungko.jungko_server.product.infrastructure;

import com.jungko.jungko_server.product.domain.ProductCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.repository.query.Param;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

	@Query("SELECT c FROM ProductCategory c LEFT JOIN c.parentCategory p ORDER BY p.id ASC NULLS FIRST, c.id ASC")
	List<ProductCategory> findAllOrderByParentIdAscNullsFirstCategoryIdAsc();

	@Query("SELECT c FROM ProductCategory c ORDER BY c.parentCategory.id ")
	List<ProductCategory> findAllOrderByParentId();

	@Query("SELECT p FROM ProductCategory p JOIN FETCH p.parentCategory WHERE p.id = :id")
	Optional<ProductCategory> findByIdAndFetchParentEagerly(@Param("id") Long id);
}
