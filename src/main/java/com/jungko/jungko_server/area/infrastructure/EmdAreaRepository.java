package com.jungko.jungko_server.area.infrastructure;

import com.jungko.jungko_server.area.domain.EmdArea;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface EmdAreaRepository extends JpaRepository<EmdArea, Long> {

	@Query("SELECT ea FROM EmdArea ea "
			+ "JOIN ea.siggArea sa "
			+ "JOIN sa.sidoArea sia "
			+ "WHERE ea.id = :emdAreaId")
	Optional<EmdArea> findEmdAreaByIdWithSiggAreaWithSidoArea(Long emdAreaId);
}
