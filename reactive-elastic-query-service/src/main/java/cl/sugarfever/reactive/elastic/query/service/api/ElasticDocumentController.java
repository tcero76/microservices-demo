package cl.sugarfever.reactive.elastic.query.service.api;

import cl.sugarfever.elastic.query.service.common.model.ElasticQueryServiceRequestModel;
import cl.sugarfever.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import cl.sugarfever.reactive.elastic.query.service.business.ElasticQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/documents")
@Slf4j
@AllArgsConstructor
public class ElasticDocumentController {
    private final ElasticQueryService elasticQueryService;

    public Mono<ServerResponse> flux(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Flux.just(1, 2, 3, 4)
                                .log(), Integer.class
                );
    }
    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("health");
    }
    @PostMapping(value = "/get-doc-by-text", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ElasticQueryServiceResponseModel> getDocumentByText(@RequestBody @Valid ElasticQueryServiceRequestModel elasticQueryServiceRequestModel) {
        Flux<ElasticQueryServiceResponseModel> response =
                elasticQueryService.getDocumentByText((elasticQueryServiceRequestModel.getText()));
        response = response.log();
        log.info("Returning from query reactive service for text {}!", elasticQueryServiceRequestModel.getText());
        return response;
    }
}
