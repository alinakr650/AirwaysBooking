package com.tickets.booking.repository;

import com.tickets.booking.domain.PassengerEntity;
import com.tickets.booking.domain.TicketEntity;
import com.tickets.booking.domain.nationalities.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, UUID> {

    @Query("SELECT pe FROM PassengerEntity pe WHERE pe.firstName =:firstName AND pe.lastName=:lastName AND pe.passportNumber =:passportNumber AND pe.nationality =:nationality")
    Optional<PassengerEntity> findPassengerEntityByFirstNameAndLastNameAndPassportNumberAndNationality(@Param("firstName") @NotBlank String firstName,
                                                                                                       @Param("lastName") @NotBlank String lastName,
                                                                                                       @Param("passportNumber") @NotBlank String passportNumber,
                                                                                                       @Param("nationality") Nationality nationality);

    @Query("SELECT pe FROM PassengerEntity pe WHERE pe.id = :id")
    Optional<PassengerEntity> findPassengerEntityById(UUID id);

    @Query("SELECT te FROM TicketEntity te WHERE te.passengerEntity.id = :id")
    List<TicketEntity> findAllTicketsByPassengerId(@Param("id") UUID passengerId);

    @Query("SELECT pe FROM PassengerEntity pe WHERE pe.passportNumber =:passportNumber AND pe.nationality =:nationality")
    Optional<PassengerEntity> findPassengerByPassportNumberAndNationality(@Param("passportNumber") @NotBlank String passportNumber,
                                                                          @Param("nationality") Nationality nationality);

}
