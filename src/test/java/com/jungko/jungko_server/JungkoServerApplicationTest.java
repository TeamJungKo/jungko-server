package com.jungko.jungko_server;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.area.domain.SidoArea;
import com.jungko.jungko_server.area.domain.SiggArea;
import com.jungko.jungko_server.product.domain.ProductCategory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JungkoServerApplicationTest {

	@PersistenceContext
	EntityManager em;

	@Test
	@DisplayName("PRODUCT_CATEGORY가 들어 있는지 확인")
	void checkProductCategoryData() {
		Assertions.assertFalse(
				em.createQuery("select pc from ProductCategory pc", ProductCategory.class)
						.getResultList()
						.isEmpty());
	}

	@Test
	@DisplayName("EMD_AREA가 들어 있는지 확인")
	void checkEmdAreaData() {
		Assertions.assertFalse(em.createQuery("select ea from EmdArea ea", EmdArea.class)
				.getResultList()
				.isEmpty());
	}

	@Test
	@DisplayName("SIGG_AREA가 들어 있는지 확인")
	void checkSiggAreaData() {
		Assertions.assertFalse(em.createQuery("select sga from SiggArea sga", SiggArea.class)
				.getResultList()
				.isEmpty());
	}

	@Test
	@DisplayName("SIDO_AREA가 들어 있는지 확인")
	void checkSidoAreaData() {
		Assertions.assertFalse(em.createQuery("select sga from SidoArea sga", SidoArea.class)
				.getResultList()
				.isEmpty());
	}
}