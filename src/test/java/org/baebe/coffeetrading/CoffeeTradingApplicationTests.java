package org.baebe.coffeetrading;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;

@SpringBootTest
class CoffeeTradingApplicationTests {

    @Test
    void contextLoads() {

        List<String> allowedMethods = Arrays.stream(HttpMethod.values()).map(HttpMethod::name).toList();

        System.out.println("allowedMethods = " + allowedMethods);
    }

}
