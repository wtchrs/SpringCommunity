package wtchrs.SpringCommunity.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"wtchrs.SpringCommunity.api", "wtchrs.SpringCommunity.common"})
@EntityScan(basePackages = {"wtchrs.community.common.entity"})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
