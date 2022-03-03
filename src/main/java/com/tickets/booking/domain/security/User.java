package com.tickets.booking.domain.security;

import com.tickets.booking.domain.PassengerEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")}, // referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.EAGER)
    private PassengerEntity passenger;

    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(authority -> {
                    return new SimpleGrantedAuthority(authority.getPermission());
                })
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Builder.Default
    private Boolean accountNonExpired = true;

    @Builder.Default
    private Boolean accountNonLocked = true;

    @Builder.Default
    private Boolean credentialsNonExpired = true;

    @Builder.Default
    private Boolean enabled = true;

    @Builder.Default
    private Boolean useGoogle2fa = false;

    private String google2FaSecret;

    @Transient
    private Boolean google2faRequired = true;

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;
}
