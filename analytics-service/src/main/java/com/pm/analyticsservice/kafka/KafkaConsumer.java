package com.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import player.events.PlayerEvent;

@Service
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics="player", groupId="analytics-service")
    public void consumeEvent(byte[] event){
        try {
            PlayerEvent playerEvent = PlayerEvent.parseFrom(event);
            log.info("Received Player Event: [PlayerId={}, PlayerName={}, PlayerEmail={} ]", playerEvent.getPlayerId(),
                    playerEvent.getName(), playerEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing event {}", e.getMessage());
        }

    }
}
