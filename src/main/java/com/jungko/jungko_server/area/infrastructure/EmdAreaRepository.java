package com.jungko.jungko_server.area.infrastructure;

import com.jungko.jungko_server.area.domain.EmdArea;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmdAreaRepository extends JpaRepository<EmdArea, Long> {
}
