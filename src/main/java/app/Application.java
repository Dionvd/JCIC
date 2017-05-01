//https://spring.io/guides/gs/rest-service/
package app;

import app.bean.AdminPanelHandler;
import app.ui.AdminPanel;
import app.bean.SocketToUnity;
import app.bean.HostGame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Application class This class contains setting notations for Spring. Can be
 * run to boot up Spring.
 *
 * @author dion
 */
@SpringBootApplication
@EnableSwagger2
public class Application {

    /**
     * Boots up Spring, Hosts the Game and Sends game info to the visuals via
     * Sockets.
     *
     * @param args
     */
    public static void main(String[] args) {

        AdminPanel panel = new AdminPanel();
        
        SpringApplication.run(Application.class, args);

        SocketToUnity.run();
                
        AdminPanelHandler.run();
        
        panel.unlock();

        HostGame.run();
        
    }

}
