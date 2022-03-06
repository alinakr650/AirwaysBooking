package com.tickets.booking.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class RoutePagedList extends PageImpl<RouteDto> implements Serializable {

    static final long serialVersionUID = 1114L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RoutePagedList(@JsonProperty("content") List<RouteDto> content,
                          @JsonProperty("number") int number,
                          @JsonProperty("size") int size,
                          @JsonProperty("totalElements") Long totalElements,
                          @JsonProperty("pageable") JsonNode pageable,
                          @JsonProperty("last") boolean last,
                          @JsonProperty("totalPages") int totalPages,
                          @JsonProperty("sort") JsonNode sort,
                          @JsonProperty("first") boolean first,
                          @JsonProperty("numberOfElements") int numberOfElements) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    public RoutePagedList(List<RouteDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public RoutePagedList(List<RouteDto> content) {
        super(content);
    }

}
