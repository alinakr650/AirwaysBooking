package com.tickets.booking.repository;

import com.tickets.booking.services.exceptions.MutuallyExclusiveSortingException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FlightRepositoryCustomImpl implements FlightRepositoryCustom {

    private final static String DATE_OF_DEPARTURE_PARAM = "dateOfDeparture";
    private final static String BASE_SEARCH_SQL_EXPRESSION = "SELECT CAST(fl.flight_id as varchar) FROM flight fl FULL JOIN route r ON r.route_id = fl.route_id WHERE r.origin =:origin AND fl.date_of_departure =:dateOfDeparture";
    private final static String ORDER_BY_PRICE_ASC = " ORDER BY fl.price ASC";
    private final static String ORDER_BY_PRICE_DESC = " ORDER BY fl.price DESC";
    private final static String ORDER_BY_FLIGHT_DURATION_ASC = " ORDER BY fl.flight_duration ASC";

    @PersistenceContext
    EntityManager em;

    @Override
    public List<UUID> searchFlightEntities(String origin, String destination, LocalDate dateOfDeparture,
                                           BigDecimal price, String orderByPriceAsc, String orderByPriceDsc,
                                           String orderByFlightDurationAsc) {
        final Map<String, Object> propertiesMap = new HashMap<>();

        final StringBuilder sqlExpression = new StringBuilder(BASE_SEARCH_SQL_EXPRESSION);

        propertiesMap.put("origin", origin);
        propertiesMap.put(DATE_OF_DEPARTURE_PARAM, dateOfDeparture);

        if (destination != null) {
            sqlExpression.append(" AND r.destination = :destination");
            propertiesMap.put("destination", destination);
        }

        if (price != null) {
            sqlExpression.append(" AND fl.price <= :price");
            propertiesMap.put("price", price);
        }

        if (orderByPriceAsc != null) {
            sqlExpression.append(ORDER_BY_PRICE_ASC);
        }

        if (orderByPriceDsc != null) {
            sqlExpression.append(ORDER_BY_PRICE_DESC);
        }

        if (orderByPriceAsc != null && orderByPriceDsc != null) {
            throw new MutuallyExclusiveSortingException("Mutually Exclusive Sorting");
        }

        if (orderByFlightDurationAsc != null) {
            sqlExpression.append(ORDER_BY_FLIGHT_DURATION_ASC);
        }

        final String expression = sqlExpression.toString();

        final Query main = em.createNativeQuery(expression);
        propertiesMap.forEach(main::setParameter);

        final List<String> result = main.getResultList();
        return result.stream().map(UUID::fromString).collect(Collectors.toList());
    }

}
