//https://spring.io/guides/gs/rest-service/
package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Application class
 * This class contains setting notations for Spring.
 * Can be run to boot up Spring.
 * @author dion
 */

@SpringBootApplication
@ComponentScan(basePackages = {"rest"})
@EnableSwagger2
public class Application {

    /**
     * Boots up Spring, Hosts the Game and Sends info to the visuals.
     * @param args
     */
    public static void main(String[] args) {
      
        SpringApplication.run(Application.class, args);
         
        SocketToUnity.run();

        HostGame.run();

    }
    
}
