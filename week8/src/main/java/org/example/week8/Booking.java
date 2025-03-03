package org.example.week8;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;


// domain specific object
public record Booking(
        long id,
        String name,
        LocalDate date,
        boolean isPremium
) implements Serializable {

    public String toStringJson() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.writer().writeValueAsString(this);
        }
        catch (IOException e) {
            System.out.println("Exception: " + e);
        }

        return toString();
    }
}
