package org.chris.demo.prometheus.web;

import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/metric")
@RequiredArgsConstructor
public class MetricDemoController {

    private final MeterRegistry meterRegistry;

    @GetMapping
    public String create() {
        IntStream.range(0, 5).forEach(this::generateMetric);
        return "ok";
    }

    private void generateMetric(int i) {

        String trackingId = UUID.randomUUID().toString().replaceAll("-", "");

        Counter.builder("tracking_message")
            .tag("trackingId", trackingId)
            .register(meterRegistry)
            .increment();

        Counter.builder("tracking_message_in")
            .tag("message_type", "in")
            .register(meterRegistry)
            .increment();
    }

}
