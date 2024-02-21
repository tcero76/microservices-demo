package cl.sugarfever.scrap.to.kafka.service.job.impl.reader;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@StepScope
@Slf4j
public class RelaxReader implements ItemStreamReader<Document> {

    private Iterator<String> listado;

    @Override
    public Document read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (listado.hasNext()) {
            return ficha(listado.next());
        } else {
            this.listado = listado().iterator();
        }
        return null;
    }

    private static Document ficha(String i) {
        String url = "https://www.relaxchile.cl/ficha-escort/ficha-escort.index" + i;
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .get();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("FICHA: Descargada ficha: {}", i);
        return doc;
    }

    private static Set<String> listado() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://relaxchile.cl/")
                    .header("Content-type", " text/html; charset=UTF-8")
                    .ignoreContentType(true)
                    .header("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:74.0) Gecko/20100101 Firefox/74.0")
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Language","es-CL,es;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Accept-Encoding","gzip, deflate, br")
                    .get();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return doc
                .select(".home-chicas-items>a")
                .stream()
                .map(elem -> "/" + elem.attr("data-item-id") + "/" + elem.attr("data-item-name"))
                .collect(Collectors.toSet());
    }


    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.listado = listado().iterator();
        log.info("Open RelaxReader");
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        log.info("Update RelaxReader");
    }

    @Override
    public void close() throws ItemStreamException {
        log.info("Close RelaxReader");
    }
}
