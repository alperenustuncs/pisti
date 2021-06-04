package com.example.server.api;

import com.example.server.model.SecureToken;
import com.example.server.service.PlayerService;
import com.example.server.service.SecureTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/password")
public class PasswordResetController {


    @Autowired
    private SecureTokenService secTokenService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PlayerService playerService;

    /**
     * creates unique token for password resetting
     */
    @PostMapping("/request")
    public String resetPassword(@RequestParam String email) {
        try {
            System.out.println("sending email-resetPassword-controller");
            playerService.forgottenPassword(email);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception occurred");
            // log the error
        }
       return "email sent";
    }

    /** password is encrypted in frontend
     *  If the token is valid password can be changed
     */
    @PostMapping("/change")
    public String changePassword(@RequestParam String token, @RequestParam String password) {

        playerService.updatePassword(password, token);
        return "password changed";
    }


}
