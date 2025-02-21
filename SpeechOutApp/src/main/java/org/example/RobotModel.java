package org.example;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.http.HttpClientService;

import java.util.Map;

public class RobotModel implements AppModel {
    @Override
    public void speak(String text) {

        FXGL.getTaskService().runAsync(
                FXGL.getService(HttpClientService.class)
                        .sendPUTRequestTask(
                                "https://rt-0143.robothespian.co.uk/tritium/text_to_speech/say",
                                text,
                                Map.ofEntries(
                                        Map.entry("Content-Type", "text/html; charset=utf-8"),
                                        Map.entry("X-Tritium-Auth-Token", "")
                                )
                        )
                        .onSuccess(resp -> {
                            System.out.println(resp.body());
                        })
                        .onFailure(e -> {
                            System.out.println("Error: " + e);
                        })
        );

    }
}
