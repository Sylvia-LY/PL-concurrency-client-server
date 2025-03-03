package org.example.week8;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.util.Collections;
import java.util.List;

/*
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
    <version>2.18.2</version>
</dependency>
 */
public class JsonSerializer implements Serializer {

    private ObjectMapper mapper = new ObjectMapper();

    public JsonSerializer() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void serialize(List<Booking> bookings, String fileName) {
        try {
            mapper.writer().writeValue(new File(fileName), bookings);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public List<Booking> deserialize(String fileName) {
        try {
            List<Booking> list = mapper.reader().readValue(new File(fileName), List.class);
            return list;

        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        return Collections.emptyList();
    }
}
