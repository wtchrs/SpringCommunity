package wtchrs.SpringCommunity.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"wtchrs.SpringCommunity.view", "wtchrs.SpringCommunity.common"})
public class ViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViewApplication.class, args);
    }
}
