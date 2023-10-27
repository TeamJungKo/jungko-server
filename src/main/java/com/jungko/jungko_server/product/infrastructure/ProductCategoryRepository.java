package com.jungko.jungko_server.product.infrastructure;

import com.jungko.jungko_server.product.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
