package net.zerotodev.api.quiz.cofing;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component @RequiredArgsConstructor
public class KafkaConsumer {
    @KafkaListener(topics="sample", groupId = "myGroup")
    public void consume(String message) throws IOException {
        System.out.println("Consumer Message is "+ message);
    }
}
