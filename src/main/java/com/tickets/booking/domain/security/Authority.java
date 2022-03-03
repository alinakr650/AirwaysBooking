package com.tickets.booking.domain.security;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "authority_id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "permission")
    private String permission;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;
}
