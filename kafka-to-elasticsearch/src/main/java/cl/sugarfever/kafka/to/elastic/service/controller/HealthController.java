package cl.sugarfever.kafka.to.elastic.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {


    @GetMapping
    public String health() {
        return "health";
    }
}
