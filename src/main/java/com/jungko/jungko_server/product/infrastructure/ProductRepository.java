package com.jungko.jungko_server.product.infrastructure;

import com.jungko.jungko_server.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
