package wtchrs.SpringCommunity.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"wtchrs.SpringCommunity.view", "wtchrs.SpringCommunity.common"})
@EntityScan(basePackages = {"wtchrs.SpringCommunity.common.entity"})
@EnableJpaRepositories(basePackages = "wtchrs.SpringCommunity.common.repository")
@EnableJpaAuditing
public class ViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViewApplication.class, args);
    }

}
