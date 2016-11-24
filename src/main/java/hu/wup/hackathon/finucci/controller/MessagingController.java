package hu.wup.hackathon.finucci.controller;

import hu.wup.hackathon.finucci.model.MessengerMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagingController {
    
    private static final String VERIFY_TOKEN = "F.inucci";
    private static final String REQUEST_OBJECT_PAGE = "page";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    String registerWebhook(@RequestParam("hub.verify_token") String verifyToken, @RequestParam("hub.challenge") String challenge) {
        if (VERIFY_TOKEN.equals(verifyToken)) {
            return challenge;
        } else {
            return "Error, wrong validation token.";
        }
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    String receiveMessage(@RequestBody MessengerMessage messengerMessage) {
        if (REQUEST_OBJECT_PAGE.equals(messengerMessage.getObject())) {
            messengerMessage.getEntry().forEach((entry) -> {
                entry.getMessaging().forEach((messaging) -> {
                    System.out.println("Received message: " + messaging.getMessage());
                });
            });
            
        }
        return "OK";
    }

}
