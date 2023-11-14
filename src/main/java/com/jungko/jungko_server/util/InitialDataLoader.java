package com.jungko.jungko_server.util;

import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "dev"})
@RequiredArgsConstructor
public class InitialDataLoader implements ApplicationRunner {

	@Value("${jungko.sql.init.product-category}")
	private String initProductCategory;
	private final DataSource dataSource;
	private final ProductCategoryRepository productCategoryRepository;

	@Override
	public void run(ApplicationArguments args) throws SQLException {
		if (productCategoryRepository.count() == 0) {
			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
			resourceDatabasePopulator.addScript(new ClassPathResource(initProductCategory));
			resourceDatabasePopulator.execute(dataSource);
		}
	}
}
