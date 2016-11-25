package hu.wup.hackathon.finucci.controller;

import hu.wup.hackathon.finucci.model.messagereceived.MessengerMessage;
import hu.wup.hackathon.finucci.model.sendapi.Attachment;
import hu.wup.hackathon.finucci.model.sendapi.Button;
import hu.wup.hackathon.finucci.model.sendapi.Element;
import hu.wup.hackathon.finucci.model.sendapi.Payload;
import hu.wup.hackathon.finucci.model.sendapi.Response;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${finucci.login.url}")
    private String loginUrl;

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
                    hu.wup.hackathon.finucci.model.sendapi.Messaging reply = processMessage(messaging.getSender().getId(), messaging.getMessage().getText());

                    sendReply(reply);
                });
            });
        }
        return "OK";
    }
    
    private void sendReply(hu.wup.hackathon.finucci.model.sendapi.Messaging response) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("accessToken", "EAAQZBzHgrghUBADdHowZBkhN8zdjtQDBfZARnh9WQXUcsPK6nA2thEx219PzbdgVgNW9vOZCiZArNpJ405KQVvaZCER2yKZACEsnZAFMZCfMCZAuUASawgDg4q7TZA6EvAZARnYZBnRfXKMOIgbYre5LgLdvyM1zcZAH5jJ1CK5YhEkoH0MQZDZD");
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        
        ResponseEntity<Response> resp = restTemplate.postForEntity("https://graph.facebook.com/v2.6/me/messages?access_token={accessToken}", response, Response.class, uriVariables);
        System.out.println("Response status: " + resp.getStatusCode());
    }
    
    private hu.wup.hackathon.finucci.model.sendapi.Messaging createSimpleResponse(String recipientId, String receivedMessage) {
        hu.wup.hackathon.finucci.model.sendapi.Messaging response = new hu.wup.hackathon.finucci.model.sendapi.Messaging();
        hu.wup.hackathon.finucci.model.sendapi.Recipient recipient = new hu.wup.hackathon.finucci.model.sendapi.Recipient();
        recipient.setId(recipientId);
        hu.wup.hackathon.finucci.model.sendapi.Message message = new hu.wup.hackathon.finucci.model.sendapi.Message();
        message.setText(receivedMessage);
        response.setRecipient(recipient);
        response.setMessage(message);
        return response;
    }
    
    private hu.wup.hackathon.finucci.model.sendapi.Messaging createLoginResponse(String recipientId) {
        hu.wup.hackathon.finucci.model.sendapi.Messaging response = new hu.wup.hackathon.finucci.model.sendapi.Messaging();
        hu.wup.hackathon.finucci.model.sendapi.Recipient recipient = new hu.wup.hackathon.finucci.model.sendapi.Recipient();
        recipient.setId(recipientId);
        hu.wup.hackathon.finucci.model.sendapi.Message message = new hu.wup.hackathon.finucci.model.sendapi.Message();
        Attachment attachment = new Attachment();
        attachment.setType("template");
        Payload payload = new Payload();
        payload.setTemplateType("generic");
        Element element = new Element();
        element.setTitle("Welcome to Chat.Up");
        element.setImageUrl("http://wup.digital/wp-content/themes/wup/assets/images/mup/slide-1-button.png");
        Button button = new Button();
        button.setType("account_link");
        button.setUrl(loginUrl);
        element.getButtons().add(button);
        payload.getElements().add(element);
        attachment.setPayload(payload);
        message.setAttachment(attachment);
        response.setRecipient(recipient);
        response.setMessage(message);
        return response;
    }

    private hu.wup.hackathon.finucci.model.sendapi.Messaging processMessage(String recipient, String message) {
        List<String> commands = Arrays.asList("send money", "I want to send money", "i want to send money", "pénzt szeretnék küldeni", "pénzt szeretnék utalni",
                "utalni szeretnék", "utalás", "pénzküldés");
        List<String> names = Arrays.asList("Zoli, Adri, Gabi, András");
//        List<String> values =


        if (commands.contains(message)) {
            return createSimpleResponse(recipient, "Kinek szeretnél utalni?");
        } else if (names.contains(message)) {
            return createSimpleResponse(recipient, "Mennyit szeretnél utalni?");
        } else if (message.equals("5000")) {
            return createSimpleResponse(recipient, "Biztos hogy el szeretnéd utalni?");
        } else if (message.equals("igen")) {
            return createSimpleResponse(recipient, "Átutalva");
        } else if (message.equals("hello")) {
            return createLoginResponse(recipient);
        }
        return createSimpleResponse(recipient, "Sajnálom, nem tudom értelmezni az üzenetet.");
    }


}
