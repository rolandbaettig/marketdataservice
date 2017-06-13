package ch.steponline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 08.05.2017
 * Time: 12:57
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan(basePackages = "ch.steponline")
@ComponentScan(basePackages = "ch.steponline")
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class,args);
    }
}
