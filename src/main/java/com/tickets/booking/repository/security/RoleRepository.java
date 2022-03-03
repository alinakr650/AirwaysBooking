package com.tickets.booking.repository.security;

import com.tickets.booking.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByName(String name);
}
