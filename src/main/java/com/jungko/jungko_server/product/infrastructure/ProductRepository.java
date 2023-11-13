package com.jungko.jungko_server.product.infrastructure;

import com.jungko.jungko_server.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitle(String keyword);
}
