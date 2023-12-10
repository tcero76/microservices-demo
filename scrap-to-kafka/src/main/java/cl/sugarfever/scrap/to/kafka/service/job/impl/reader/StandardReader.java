package cl.sugarfever.scrap.to.kafka.service.job.impl.reader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@StepScope
public class StandardReader implements ItemStreamReader<Document> {

    private static final Logger log = LoggerFactory.getLogger(StandardReader.class);
    
    private Iterator<String> urlCursor;
    private Iterator<Integer> listado;
    private Integer idpagina;
    private String url;

    @Override
    public Document read() {
        if (listado.hasNext()) {
            idpagina = listado.next();
        } else {
            if (urlCursor.hasNext()) {
                url = urlCursor.next();
                listado = listarIds(url).iterator();
                if (listado.hasNext()) {
                    idpagina = listado.next();
                    log.info("idpagina: {}", idpagina);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return getFicha(idpagina);
    }
    private Document getFicha(Integer id) {
        Document doc = null;
            String url = this.url + "site/ficha?id=" + id + "&usuario=0";
            try {
                doc = Jsoup.connect(url)
                        .header("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:66.0) Gecko/20100101 Firefox/66.0")
                        .header("X-Requested-With", "XMLHttpRequest").ignoreContentType(true).get();
            } catch (IOException e) {
                log.error("Error al leer ficha: " + id + "sitio: " + url);
            }
        return doc;
    }
    private Set<Integer> listarIds(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .header("Content-type", " text/html; charset=UTF-8")
                    .ignoreContentType(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements e = doc.select("ul.portadas li");
        log.info("Reader: Encontradas {}", e.size());
        Set<Integer> idfichas = new HashSet<Integer>();
        for (Element el : e) {
            idfichas.add(Integer.valueOf(el.attr("idficha")));
        }
        log.info("Fichas: Se encontraron {}", idfichas.size());
        return idfichas;
    }
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.urlCursor = Stream.
                of("https://laplayaescort.cl/","https://planetaescort.cl/")
                .collect(Collectors.toSet()).iterator();
        this.url = urlCursor.next();
        this.listado = listarIds(url).iterator();
        this.idpagina = listado.next();
        log.info("Standard inicializado");
    }
    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        log.info("Standard updated");
    }
    @Override
    public void close() throws ItemStreamException {
        log.info("Standard closed");
    }
}
