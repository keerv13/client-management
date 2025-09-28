package com.pm.playerservices.kafka;

import com.pm.playerservices.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import player.events.PlayerEvent;

@Service
public class kafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(kafkaProducer.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public kafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Player player) {
        PlayerEvent event = PlayerEvent.newBuilder()
                .setPlayerId(player.getId().toString())
                .setName(player.getName())
                .setEmail(player.getEmail())
                .setEventType("PLAYER_CREATED")
                .build();
        try{
            kafkaTemplate.send("player", event.toByteArray());

        } catch (Exception e){
            log.error("Error sending PlayerCreated event: {}", event);
        }

    }
}
