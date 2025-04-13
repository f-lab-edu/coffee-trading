package org.baebe.coffeetrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CoffeeTradingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeTradingApplication.class, args);
    }

}
