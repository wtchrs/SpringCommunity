package wtchrs.SpringCommunity.view.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"wtchrs.SpringCommunity.common.entity"})
@EnableJpaRepositories(basePackages = "wtchrs.SpringCommunity.common.entity")
@EnableJpaAuditing
public class JpaConfig {
}
