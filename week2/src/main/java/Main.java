import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.intelligence.tts.TextToSpeechService;
import com.almasb.fxgl.intelligence.tts.Voice;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class Main extends GameApplication {

    private ChoiceBox<Voice> choiceBox;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1700);
        gameSettings.addEngineService(TextToSpeechService.class);
    }

    @Override
    protected void initGame() {
        FXGL.getService(TextToSpeechService.class)
                .readyProperty()
                .addListener((o, wasReady, isReady) -> {
                    System.out.println("isReady: " + isReady);

//                    FXGL.getService(TextToSpeechService.class).speak("to be or not to be, that is the question");

                    choiceBox.setItems(FXCollections.observableList(
                            FXGL.getService(TextToSpeechService.class).getVoices()
                    ));

                });

        FXGL.getService(TextToSpeechService.class).start();
    }


    @Override
    protected void initUI() {
        var textField = new TextField();
        textField.setFont(Font.font(18));
        textField.setOnAction(evt -> {

            String text = textField.getText();
            String type = identifyType(text);

            FXGL.getService(TextToSpeechService.class).speak(text + " is a " + type);

        });

        choiceBox = new ChoiceBox<Voice>();
        choiceBox.setPrefWidth(300);

        FXGL.addUINode(textField, 50, 50);
        FXGL.addUINode(choiceBox, 50, 150);
    }

    private static String identifyType(String text) {

        if (text.equals("true") || text.equals("false")) {
            return "boolean";
        }

        try {
            Integer.parseInt(text);
            return "int";
        }
        catch (NumberFormatException e) {}

        try {
            // 168d识别成double了。。
            Double.parseDouble(text);
            return "double";
        }
        catch (NumberFormatException e) {}

        if (text.length() == 1 && !Character.isDigit(text.charAt(0))) {
            return "char";
        }

        return "unknown";

    }

    public static void main(String[] args) {
        launch(args);
    }
}
