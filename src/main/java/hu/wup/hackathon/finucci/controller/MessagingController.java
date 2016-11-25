package hu.wup.hackathon.finucci.controller;

import hu.wup.hackathon.finucci.model.messagereceived.MessengerMessage;
import hu.wup.hackathon.finucci.model.sendapi.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
                    System.out.println("Received message: " + messaging.getMessage().getText());
                    sendReply(messaging.getSender().getId(), messaging.getMessage().getText());
                });
            });
        }
        return "OK";
    }
    
    private void sendReply(String recipientId, String receivedMessage) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("accessToken", "EAAQZBzHgrghUBADdHowZBkhN8zdjtQDBfZARnh9WQXUcsPK6nA2thEx219PzbdgVgNW9vOZCiZArNpJ405KQVvaZCER2yKZACEsnZAFMZCfMCZAuUASawgDg4q7TZA6EvAZARnYZBnRfXKMOIgbYre5LgLdvyM1zcZAH5jJ1CK5YhEkoH0MQZDZD");
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        hu.wup.hackathon.finucci.model.sendapi.Messaging response = new hu.wup.hackathon.finucci.model.sendapi.Messaging();
        hu.wup.hackathon.finucci.model.sendapi.Recipient recipient = new hu.wup.hackathon.finucci.model.sendapi.Recipient();
        recipient.setId(recipientId);
        hu.wup.hackathon.finucci.model.sendapi.Message message = new hu.wup.hackathon.finucci.model.sendapi.Message();
        message.setText(receivedMessage);
        response.setRecipient(recipient);
        response.setMessage(message);
        ResponseEntity<Response> resp = restTemplate.postForEntity("https://graph.facebook.com/v2.6/me/messages?access_token={accessToken}", response, Response.class, uriVariables);
        System.out.println("Response status: " + resp.getStatusCode());
    }

}
