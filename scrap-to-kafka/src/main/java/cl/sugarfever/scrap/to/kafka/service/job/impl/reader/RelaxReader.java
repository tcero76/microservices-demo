package cl.sugarfever.scrap.to.kafka.service.job.impl.reader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@StepScope
public class RelaxReader implements ItemReader<Document> {
    
    private static final Logger log = LoggerFactory.getLogger(RelaxReader.class);

    Iterator<String> listado;

    public RelaxReader() {
        this.listado = listado().iterator();
    }

    @Override
    public Document read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (listado.hasNext()) {
            return ficha(listado.next());
        }
        return null;
    }

    private static Document ficha(String i) {
        String url = "https://relaxchile.cl/ficha-escort/ficha-escort.index/" + i;
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .ignoreContentType(true)
//                    .validateTLSCertificates(false)
                    .get();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return doc;
    }

    private static Set<String> listado() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://relaxchile.cl/")
                    .header("Content-type", " text/html; charset=UTF-8")
                    .ignoreContentType(true)
//                    .validateTLSCertificates(false)
                    .header("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:74.0) Gecko/20100101 Firefox/74.0")
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Language","es-CL,es;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Accept-Encoding","gzip, deflate, br")
                    .get();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return doc
                .select("div.destacadas-vip a.ficha-escort-img.minitip")
                .stream()
                .map(elem -> elem.attr("data-item-id"))
                .collect(Collectors.toSet());
    }


}
