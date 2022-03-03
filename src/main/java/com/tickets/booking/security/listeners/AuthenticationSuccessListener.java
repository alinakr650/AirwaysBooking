package com.tickets.booking.security.listeners;

import com.tickets.booking.domain.security.LoginSuccess;
import com.tickets.booking.domain.security.User;
import com.tickets.booking.repository.security.LoginSuccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;


@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationSuccessListener {

    private final LoginSuccessRepository loginSuccessRepository;

    @EventListener
    public void listen(AuthenticationSuccessEvent event){

        log.debug("User Logged In Okay");

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
            LoginSuccess.LoginSuccessBuilder builder = LoginSuccess.builder();

            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();

            if(token.getPrincipal() instanceof User){
                User user = (User) token.getPrincipal();
                builder.user(user);

                log.debug("User name logged in: " + user.getUsername() );
            }

            if(token.getDetails() instanceof WebAuthenticationDetails){
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();

                log.debug("Source IP: " + details.getRemoteAddress());
                builder.sourceIp(details.getRemoteAddress());
            }

            LoginSuccess loginSuccess = loginSuccessRepository.save(builder.build());

            log.debug("Login Success saved. Id: " + loginSuccess.getId());
        }

    }
}
