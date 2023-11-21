package com.jungko.jungko_server.product.infrastructure;

import com.jungko.jungko_server.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p WHERE " +
			"(:keyword IS NULL OR p.title LIKE %:keyword%) AND " +
			"(:minPrice IS NULL OR p.price >= :minPrice) AND " +
			"(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
			"(:categoryId IS NULL OR p.productCategory = :categoryId) AND " +
			"(:areaId IS NULL OR p.area = :code)")
	Page<Product> searchProduct(@Param("keyword") String keyword,
			@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice,
			@Param("categoryId") Long categoryId,
			@Param("code") String code,
			Pageable pageable);
}
