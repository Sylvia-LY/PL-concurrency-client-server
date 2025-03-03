package org.example.week8;

import com.almasb.fxgl.net.ws.LocalWebSocketServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

// 把module-info删了。。。
// --module-path "C:\Users\apple\Desktop\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml
public class BookingApp extends Application {

    private List<Booking> bookings = new ArrayList<>();
    private Serializer serializer = new JavaSerializer();

    private LocalWebSocketServer server = new LocalWebSocketServer("JavaBookingServer", 54000);

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    private Parent createContent() {
        server.addMessageHandler(input -> {

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                System.out.println(mapper.readValue(input, Booking.class).name());
            }
            catch (JsonProcessingException e) {
                System.out.println("Exception: " + e);
            }
        });

        server.start();

        TextField textFieldID = new TextField();
        textFieldID.setFont(Font.font(72));

        TextField textFieldName = new TextField();
        textFieldName.setFont(Font.font(72));

        DatePicker datePicker = new DatePicker();
        CheckBox checkBoxIsPremium = new CheckBox();

        Button btnSubmit = new Button("Submit");
        btnSubmit.setOnAction((evt) -> {
            var booking = new Booking(
                    Long.parseLong(textFieldID.getText()),
                    textFieldName.getText(),
                    datePicker.getValue(),
                    checkBoxIsPremium.isSelected()
            );
            bookings.add(booking);
        });

        Button btnPrint = new Button("Print");
        btnPrint.setOnAction((evt) -> {
            // serializer
            serializer.serialize(bookings, "bookings.data");
            var list = serializer.deserialize("bookings.data");
            System.out.println(list);

            // js client app
            var result = bookings
                    .stream()
                    .map(b -> b.toStringJson())
                    .toList()
                    .toString();

            server.send(result);
        });

        return new VBox(
                10,
                textFieldID,
                textFieldName,
                datePicker,
                checkBoxIsPremium,
                btnSubmit,
                btnPrint
        );
    }

    public static class Launcher {
        public static void main(String[] args) {
            Application.launch(BookingApp.class);
        }
    }
}
