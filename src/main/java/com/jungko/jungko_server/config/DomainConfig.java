package com.jungko.jungko_server.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.jungko.jungko_server")
@EnableJpaRepositories("com.jungko.jungko_server")
@EnableTransactionManagement
public class DomainConfig {
}
