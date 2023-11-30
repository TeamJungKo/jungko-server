package com.jungko.jungko_server.product.infrastructure;

import com.jungko.jungko_server.product.domain.ProductKeyword;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProductKeywordRepository extends JpaRepository<ProductKeyword, Long> {

	@Query("select pk from ProductKeyword pk where pk.product.id = :productId")
	List<ProductKeyword> findAllByProductId(Long productId);
}
