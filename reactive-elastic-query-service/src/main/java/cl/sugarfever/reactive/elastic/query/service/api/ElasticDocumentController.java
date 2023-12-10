package cl.sugarfever.reactive.elastic.query.service.api;

import cl.sugarfever.elastic.query.service.common.model.ElasticQueryServiceRequestModel;
import cl.sugarfever.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import cl.sugarfever.reactive.elastic.query.service.business.ElasticQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.awt.*;

@RestController
@RequestMapping(value = "/documents")
@Slf4j
@AllArgsConstructor
public class ElasticDocumentController {
    private final ElasticQueryService elasticQueryService;

    @PostMapping(value = "/get-doc-by-text", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ElasticQueryServiceResponseModel> getDocumentByText(
            @RequestBody @Valid ElasticQueryServiceRequestModel elasticQueryServiceRequestModel
            ) {
        Flux<ElasticQueryServiceResponseModel> response =
                elasticQueryService.getDocumentByText((elasticQueryServiceRequestModel.getText()));
        response = response.log();
        log.info("Returning from query reactive service for text {}!", elasticQueryServiceRequestModel.getText());
        return response;
    }
}
