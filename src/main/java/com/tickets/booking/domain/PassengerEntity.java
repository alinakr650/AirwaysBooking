package com.tickets.booking.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tickets.booking.domain.nationalities.Nationality;
import com.tickets.booking.domain.security.User;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "passengers")
public class PassengerEntity implements Serializable {

    private static final long serialVersionUID = 14444L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Size(min = 2, message = "First name should have at least 2 characters")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Size(min = 2, message = "Last name should have at least 2 characters")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @Column(name = "nationality", nullable = false)
    @Enumerated(EnumType.STRING)
    private Nationality nationality;

    @OneToMany(mappedBy = "passengerEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<TicketEntity> ticketEntities = new ArrayList<>();

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PassengerEntity passengerEntity = (PassengerEntity) o;
        return id != null && Objects.equals(id, passengerEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
