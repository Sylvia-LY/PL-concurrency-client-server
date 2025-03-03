package org.example.week8;

import java.util.List;

public interface Serializer {
    void serialize(List<Booking> bookings, String fileName);

    List<Booking> deserialize(String fileName);
}
