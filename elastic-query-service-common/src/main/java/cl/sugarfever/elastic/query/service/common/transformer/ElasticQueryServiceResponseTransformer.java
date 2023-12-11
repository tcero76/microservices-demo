package cl.sugarfever.elastic.query.service.common.transformer;

import cl.sugarfever.elastic.model.ScrapIndexModel;
import cl.sugarfever.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import org.springframework.stereotype.Component;

@Component
public class ElasticQueryServiceResponseTransformer {

    public ElasticQueryServiceResponseModel getResponseModel(ScrapIndexModel scrapIndexModel) {
        return ElasticQueryServiceResponseModel.builder()
                .id(scrapIndexModel.getId())
                .text(scrapIndexModel.getDescripcion())
                .build();
    }
}
