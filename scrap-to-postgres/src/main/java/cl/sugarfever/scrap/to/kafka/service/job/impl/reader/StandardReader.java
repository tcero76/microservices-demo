package cl.sugarfever.scrap.to.kafka.service.job.impl.reader;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@StepScope
@Slf4j
public class StandardReader implements ItemStreamReader<Document> {
    private Iterator<String> urlCursor;
    private Iterator<String> listado;
    private String idpagina;
    private String url;
    @Override
    public Document read() {
        if (listado.hasNext()) {
            idpagina = listado.next();
            log.info("idpagina: {}", idpagina);
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
    private Document getFicha(String id) {
        Document doc = null;
            String url = this.url + id;
            try {
                doc = Jsoup.connect(url)
                        .header("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:66.0) Gecko/20100101 Firefox/66.0")
                        .header("X-Requested-With", "XMLHttpRequest").ignoreContentType(true).get();
            } catch (IOException e) {
                log.error("Error al leer ficha: " + id + "sitio: " + url);
            }
        return doc;
    }
    private Set<String> listarIds(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .header("Content-type", " text/html; charset=UTF-8")
                    .ignoreContentType(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements e = doc.select("article[class*=bloque-]");
        log.info("Reader: Encontradas {}", e.size());
        Set<String> idfichas = new HashSet<String>();
        for (Element el : e) {
            idfichas.add(el.getElementsByClass("btnModelo").attr("href"));
        }
        log.info("Fichas: Se encontraron {}", idfichas.size());
        return idfichas;
    }
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.urlCursor = Stream.
                of("https://laplayaescort.cl","https://planetaescort.cl")
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
