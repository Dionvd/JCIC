//https://spring.io/guides/gs/rest-service/
package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Application class
 * Contains setting notations for Spring.
 * Can be run to boot up Spring.
 * @author dion
 */
@SpringBootApplication
@ComponentScan(basePackages = {"rest"})
@EnableSwagger2
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class}) //would disable the SPRING error whitepage and revert to APACHE error page.
public class Application {

    /**
     * Boots up Spring. 
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}
