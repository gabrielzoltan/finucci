package hu.wup.hackathon.finucci.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    
    @RequestMapping("/login")
    public String login(Model model, @RequestParam("redirect_uri") String redirectUri, @RequestParam("account_linking_token") String accountLinkingToken) {
        model.addAttribute("redirectUri", redirectUri);
        model.addAttribute("accoutLinkingToken", accountLinkingToken);
        return "login";
    }
    
}
