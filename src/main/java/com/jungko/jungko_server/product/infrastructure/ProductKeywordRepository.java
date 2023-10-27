package com.jungko.jungko_server.product.infrastructure;

import com.jungko.jungko_server.product.domain.ProductKeyword;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductKeywordRepository extends JpaRepository<ProductKeyword, Long> {
}
