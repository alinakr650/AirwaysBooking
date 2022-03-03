package com.tickets.booking.repository.security;

import com.tickets.booking.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Optional<Authority> findAuthorityByPermission(String permission);

}
