package webservice;

import entity.GameRules;
import entity.Main;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RulesController {

    @RequestMapping(value = "/rules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public GameRules rules() {
        return Main.self.getGames().get(0).getGameRules();
    }
    
    
}