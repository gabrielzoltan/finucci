package hu.wup.hackathon.finucci.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagingController {
    
    private static final String VERIFY_TOKEN = "F.inucci";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    String home(@RequestParam("hub.verify_token") String verifyToken, @RequestParam("hub.challenge") String challenge) {
        if (VERIFY_TOKEN.equals(verifyToken)) {
            return challenge;
        } else {
            return "Error, wrong validation token.";
        }
    }

}
