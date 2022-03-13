package com.tickets.booking.web.controller;

import com.tickets.booking.domain.security.User;
import com.tickets.booking.repository.security.UserRepository;
import com.tickets.booking.security.AuthenticationRequest;
import com.tickets.booking.security.AuthenticationResponse;
import com.tickets.booking.security.JpaUserDetailsService;
import com.tickets.booking.security.UserService;
import com.tickets.booking.services.exceptions.InvalidEmailOrPasswordException;
import com.tickets.booking.services.util.JwtUtil;
import com.tickets.booking.web.model.PassengerDto;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.apache.http.auth.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final GoogleAuthenticator googleAuthenticator;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public UserController(UserRepository userRepository, AuthenticationManager authenticationManager,
                          GoogleAuthenticator googleAuthenticator, UserService userService,
                          UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.googleAuthenticator = googleAuthenticator;
        this.userService = userService;
        this.userDetailsService = new JpaUserDetailsService(userRepository);
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUser(@RequestParam String username, @RequestParam String password, @RequestBody PassengerDto passengerDto)
            throws RoleNotFoundException {
        userService.createNewUser(username, password, passengerDto);
    }

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

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername()
                    , authenticationRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new InvalidEmailOrPasswordException("Invalid Email or Password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
