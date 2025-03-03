package org.example.week8;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

public class JavaSerializer implements Serializer {

    @Override
    public void serialize(List<Booking> bookings, String fileName) {
        try (
                var fos = new FileOutputStream(fileName);
                var oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(bookings);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public List<Booking> deserialize(String fileName) {
        try (
                var fis = new FileInputStream(fileName);
                var ois = new ObjectInputStream(fis);
        ) {
            List<Booking> list = (List<Booking>)ois.readObject();
            return list;

        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        return Collections.emptyList();
    }
}
