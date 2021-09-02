package net.zerotodev.api.quiz.controller;
/**
import lombok.RequiredArgsConstructor;
import net.zerotodev.api.quiz.domain.Producers;
import net.zerotodev.api.quiz.service.KafkaSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/kafka")
public class KafkaController__ {
    private final KafkaSender kafkaSender;
    private final Producers producers;
    @GetMapping
    public String hello(){
        return "Hello Kafka";
    }

    @GetMapping("/producer")
    public String producer(@RequestParam("message") String message){
        System.out.println("############### Producer 진입 #############");
        kafkaSender.send(message);
        return "Message Sent to Kafka Successfully";
    }
    @GetMapping("/receiver")
    public void receiver(){
        producers.sendMessage("kafka-test","Good-Luck !!");
    }
}
*/