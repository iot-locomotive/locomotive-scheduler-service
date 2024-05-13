package com.iot.scheduler.services;

import com.iot.scheduler.models.Locomotive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
public class ScheduleInfoTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduleInfoTask.class);
    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    WebClient webClient;

    @Scheduled(fixedRate = 10000)
    public void createLocomotiveData() {
        final String[] locoName = { "Oakland Line", "Observatory Line", "Hope Valley Tracks", "Midsummer Line" };
        final String[] locoDimension = { "10x10", "20x20", "30x30", "40x40" };

        Locomotive locomotive = Locomotive.builder()
                .locomotiveCode(UUID.randomUUID())
                .locomotiveName(locoName[RANDOM.nextInt(locoName.length)])
                .locomotiveDimension(locoDimension[RANDOM.nextInt(locoDimension.length)])
                .status(RANDOM.nextInt(3) + 1) // generate random status between 1 and 3
                .time(Instant.now().toString())
                .build();

        log.info(locomotive.toString());

        log.info("Inserting locomotive Data to Kafka via API NodeJS");

        Instant beforePost = Instant.now();
        webClient.post()
                .uri("/locomotive/produce")
                .body(Mono.just(locomotive), locomotive.getClass())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        Instant afterPost = Instant.now();

        Duration duration = Duration.between(beforePost, afterPost);
        log.info("Finish Inserting locomotive Data to Kafka via API NodeJS {} ms", duration.toMillis());

    }
}
