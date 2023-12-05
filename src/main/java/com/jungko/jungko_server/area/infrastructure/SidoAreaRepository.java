package com.jungko.jungko_server.area.infrastructure;

import com.jungko.jungko_server.area.domain.SidoArea;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SidoAreaRepository extends JpaRepository<SidoArea, Long> {

	@Query("SELECT s FROM SidoArea s " +
			"JOIN s.siggAreas sa " +
			"JOIN sa.emdAreas ea")
	Set<SidoArea> findAllWithSiggAndEmd();
}
