package org.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.intelligence.tts.TextToSpeechService;
import com.almasb.fxgl.net.http.HttpClientService;

public class SpeechOutApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.addEngineService(HttpClientService.class);
        gameSettings.addEngineService(TextToSpeechService.class);

    }


    @Override
    protected void initUI() {
        FXGL.getService(TextToSpeechService.class).start();

        FXGL.getService(TextToSpeechService.class)
                .readyProperty()
                .subscribe(() -> {
                    var voices = FXGL.getService(TextToSpeechService.class).getVoices();

                    System.out.println(voices);

                    if (voices.size() > 1) {
                        FXGL.getService(TextToSpeechService.class).setSelectedVoice(voices.get(1));
                    }
                });


        AppView view = new AppView();
        FXGL.addUINode(view);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
