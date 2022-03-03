package com.tickets.booking.web.controller;

import com.tickets.booking.domain.security.User;
import com.tickets.booking.repository.security.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final GoogleAuthenticator googleAuthenticator;

    @GetMapping("/register2fa")
    public String register2fa() {

        User user = getUser();

        return GoogleAuthenticatorQRGenerator.getOtpAuthURL("A", user.getUsername(),
                googleAuthenticator.createCredentials(user.getUsername()));

    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/register2fa")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void confirm2Fa(@RequestParam Integer verifyCode) throws AuthenticationException {

        User user = getUser();

        if (googleAuthenticator.authorizeUser(user.getUsername(), verifyCode)) {
            User savedUser = userRepository.findByUsername(user.getUsername()).orElseThrow();
            savedUser.setUseGoogle2fa(true);
            userRepository.save(savedUser);
        } else {
            throw new AuthenticationException();
        }
    }

    //rearrange
    @PostMapping("/verify2fa")
    @ResponseStatus(HttpStatus.OK)
    public String verifyPostOf2Fa(@RequestParam Integer verifyCode) throws AuthenticationException {

        User user = getUser();

        if (googleAuthenticator.authorizeUser(user.getUsername(), verifyCode)) {
            ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setGoogle2faRequired(false);
            return "user/verify2fa";
        } else {
            throw new AuthenticationException();
        }
    }
}
