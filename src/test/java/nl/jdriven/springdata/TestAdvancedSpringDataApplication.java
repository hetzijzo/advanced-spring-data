package nl.jdriven.springdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAdvancedSpringDataApplication {

    public static void main(String[] args) {
        SpringApplication.from(AdvancedSpringDataApplication::main)
                .with(TestAdvancedSpringDataApplication.class).run(args);
    }
}
