package cl.sugarfever.elastic.index.client;

import cl.sugarfever.elastic.index.client.repository.ScrapElasticsearchIndexRepository;
import cl.sugarfever.elastic.index.client.service.ElasticIndexClient;
import cl.sugarfever.elastic.model.ScrapIndexModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class ScrapElasticRepositoryClient implements ElasticIndexClient<ScrapIndexModel> {
    private final ScrapElasticsearchIndexRepository scrapElasticsearchIndexRepository;

    public ScrapElasticRepositoryClient(ScrapElasticsearchIndexRepository scrapElasticsearchIndexRepository) {
        this.scrapElasticsearchIndexRepository = scrapElasticsearchIndexRepository;
    }
    @Override
    public List<String> save(List<ScrapIndexModel> documents) {
        log.info("ScrapIndexModel: Se enviaron {}", documents.size());
        Iterable<ScrapIndexModel> all = scrapElasticsearchIndexRepository.findAll();
        List<ScrapIndexModel> scrapIndexModelAll = new ArrayList<>();
        all.forEach(scrapIndexModelAll::add);
        log.info("En BD encontró {}", scrapIndexModelAll.size());
        List<ScrapIndexModel> scrapIndexModels = documents.stream().flatMap(scrapIndexModel -> {
                    return scrapIndexModelAll.stream().filter(scrapIndexModel1 -> scrapIndexModel.equals(scrapIndexModel1));
                })
                .collect(Collectors.toList());
        log.info("ScrapIndexModel: Se eliminarán {} ", scrapIndexModels.size());
        scrapElasticsearchIndexRepository.deleteAll(scrapIndexModels);
        Iterable<ScrapIndexModel> all1 = scrapElasticsearchIndexRepository.findAll();
        List<ScrapIndexModel> repositoryResponse =
                (List<ScrapIndexModel>) scrapElasticsearchIndexRepository.saveAll(documents);
        List<String> ids = repositoryResponse.stream()
                .map(ScrapIndexModel::getId)
                .collect(Collectors.toList());
        log.info("ScrapIndexModel: Almacenó {}", ids.size());
        return ids;
    }
}
