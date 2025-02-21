package org.example;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.intelligence.tts.TextToSpeechService;

public class OSModel implements AppModel {
    @Override
    public void speak(String text) {

        FXGL.getService(TextToSpeechService.class).speak(text);
    }
}
