package cl.sugarfever.gateway.controller;

import cl.sugarfever.gateway.model.QueryServiceFallbackModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
@Slf4j
public class FallbackController {
    @Value("${server.port}")
    private String port;
    @GetMapping("/documents/health")
    public String health() {
        return "gatewayHealth";
    }

    @PostMapping("/query-fallback")
    public ResponseEntity<QueryServiceFallbackModel> queryServiceFallback() {
        log.info("Returning fallback result for elastic-query-service! on port {}", port);
        return ResponseEntity.ok(QueryServiceFallbackModel.builder()
                .fallbackMessage("Fallback result for elastic-query-service!")
                .build());
    }
}
