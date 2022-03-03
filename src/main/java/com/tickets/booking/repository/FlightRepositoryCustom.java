package com.tickets.booking.repository;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Validated
@Repository
public interface FlightRepositoryCustom {

    List<UUID> searchFlightEntities(@NotBlank String origin,
                                    @Nullable String destination,
                                    @NotNull LocalDate dateOfDeparture,
                                    @Nullable BigDecimal priceLimit,
                                    @Nullable String orderByPriceAsc,
                                    @Nullable String orderByPriceDsc,
                                    @Nullable String orderByFlightDurationAsc);

}
