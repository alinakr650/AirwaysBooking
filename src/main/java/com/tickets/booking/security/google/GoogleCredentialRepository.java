package com.tickets.booking.security.google;

import com.tickets.booking.domain.security.User;
import com.tickets.booking.repository.security.UserRepository;
import com.tickets.booking.services.exceptions.UserNotFoundException;
import com.warrenstrange.googleauth.ICredentialRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoogleCredentialRepository implements ICredentialRepository{

    private final UserRepository userRepository;

    public GoogleCredentialRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getSecretKey(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(UserNotFoundException::new);

        return user.getGoogle2FaSecret();
    }

    @Override
    public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {
        User user = userRepository.findByUsername(userName).orElseThrow();
        user.setGoogle2FaSecret(secretKey);
        user.setUseGoogle2fa(true);
        userRepository.save(user);
    }
}
