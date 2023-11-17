package com.jungko.jungko_server.util;

import com.jungko.jungko_server.area.infrastructure.EmdAreaRepository;
import com.jungko.jungko_server.area.infrastructure.SidoAreaRepository;
import com.jungko.jungko_server.area.infrastructure.SiggAreaRepository;
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

	@Value("${jungko.sql.init.emd-area}")
	private String initEmdArea;

	@Value("${jungko.sql.init.sigg-area}")
	private String initSiggArea;

	@Value("${jungko.sql.init.sido-area}")
	private String initSidoArea;

	private final DataSource dataSource;
	private final ProductCategoryRepository productCategoryRepository;
	private final SidoAreaRepository sidoAreaRepository;
	private final SiggAreaRepository siggAreaRepository;
	private final EmdAreaRepository emdAreaRepository;

	@Override
	public void run(ApplicationArguments args) throws SQLException {
		if (productCategoryRepository.count() == 0) {
			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
			resourceDatabasePopulator.addScript(new ClassPathResource(initProductCategory));
			resourceDatabasePopulator.execute(dataSource);
		}

		if (sidoAreaRepository.count() == 0) {
			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
			resourceDatabasePopulator.addScript(new ClassPathResource(initSidoArea));
			resourceDatabasePopulator.execute(dataSource);
		}

		if (siggAreaRepository.count() == 0) {
			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
			resourceDatabasePopulator.addScript(new ClassPathResource(initSiggArea));
			resourceDatabasePopulator.execute(dataSource);
		}

		if (emdAreaRepository.count() == 0) {
			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
			resourceDatabasePopulator.addScript(new ClassPathResource(initEmdArea));
			resourceDatabasePopulator.execute(dataSource);
		}
	}
}
