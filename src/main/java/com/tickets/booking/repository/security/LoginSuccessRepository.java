package com.tickets.booking.repository.security;

import com.tickets.booking.domain.security.LoginSuccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginSuccessRepository extends JpaRepository<LoginSuccess, Integer> {
}
