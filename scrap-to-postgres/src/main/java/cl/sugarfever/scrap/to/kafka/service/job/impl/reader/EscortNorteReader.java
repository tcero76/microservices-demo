package cl.sugarfever.scrap.to.kafka.service.job.impl.reader;

import cl.sugarfever.postgres.model.Ts;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
@Slf4j
public class EscortNorteReader implements ItemReader<Document> {

    private int count = 0;
    private List<Integer> listado = listarIds("https://escortnorte.cl/_js/ajax/cargar_portadas_todos.php?index=1&format=json");;

    @Override
    public Document read() {
        if(count>=listado.size()) {
            return null;
        } else {
            count++;
            Integer idPagina = listado.get(count - 1);
            log.info("idPagina: procesando idPagina: {}", idPagina);
            return ficha(idPagina);
        }
    }

    private static List<Integer> listarIds(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:67.0) Gecko/20100101 Firefox/67.0")
                    .get();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        JSONArray arr = new JSONArray(doc.body().text());
        List<Integer> idfichas = new ArrayList<Integer>();
        for (Object j : arr) {
            JSONObject json = (JSONObject)j;
            idfichas.add(json.getInt("id"));
        }
        return idfichas;
    }

    private static Document ficha(Integer id) {
        Document doc = null;
        List<Ts> resp = new ArrayList<Ts>();
            String url = "https://escortnorte.cl/_html/ficha.php?fichaID=" + id;
            try {
                doc = Jsoup.connect(url)
                        .header("User-Agent",
                                "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:66.0) Gecko/20100101 Firefox/66.0")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .ignoreContentType(true)
                        .get();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        return doc;
    }
}
