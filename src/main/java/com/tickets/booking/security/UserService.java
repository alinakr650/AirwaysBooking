package com.tickets.booking.security;

import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.security.User;
import com.tickets.booking.repository.security.RoleRepository;
import com.tickets.booking.repository.security.UserRepository;
import com.tickets.booking.services.exceptions.InvalidEmailOrPasswordException;
import com.tickets.booking.services.exceptions.UserAlreadyRegisteredException;
import com.tickets.booking.services.util.RegexValidation;
import com.tickets.booking.web.mappers.PassengerMapper;
import com.tickets.booking.web.model.PassengerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PassengerMapper passengerMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public void createNewUser(String username, String password, PassengerDto passengerDto) throws RoleNotFoundException {

        Boolean validEmail = RegexValidation.validateEmail(username);
        Boolean validPassword = RegexValidation.validatePassword(password);

        if (!validEmail) {
            throw new IllegalArgumentException("The email must be valid");
        }


        if (!validPassword) {
            throw new IllegalArgumentException("The password must contain at least 8 characters and at most 20 characters.\n" +
                    "At least one digit.\n" +
                    "At least one upper case alphabet.\n" +
                    "At least one lower case alphabet.\n" +
                    "Zero white spaces.");
        }

        Optional<User> user = userRepository.findByUsername(username);
        PassengerEntity passengerEntity = passengerMapper.passengerDtoToPassenger(passengerDto);

        if (user.isEmpty()) {
            userRepository.save(User.builder()
                    .username(username)
                    .passenger(passengerEntity)
                    .password(passwordEncoder.encode(password))
                    .role(roleRepository.findRoleByName("PASSENGER")
                            .orElseThrow(RoleNotFoundException::new))
                    .build());
        } else if (user.isPresent() && user.get().getPassenger() == null) {
            userRepository.save(User.builder()
                    .id(user.get().getId())
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .passenger(passengerEntity)
                    .role(roleRepository.findRoleByName("PASSENGER")
                            .orElseThrow(RoleNotFoundException::new))
                    .build());
        } else if (user.isPresent() && user.get().getPassenger() != null)
            throw new UserAlreadyRegisteredException("User Already Registered");
    }
}
