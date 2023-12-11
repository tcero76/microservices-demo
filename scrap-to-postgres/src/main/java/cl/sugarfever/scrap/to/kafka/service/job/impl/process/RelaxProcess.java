//package com.scraping.tcero.process;
//
//import com.scraping.tcero.listener.JobListener;
//import com.scraping.tcero.model.Imagen;
//import com.scraping.tcero.model.Servicio;
//import com.scraping.tcero.model.Ts;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class RelaxProcess implements ItemProcessor<Document, Ts> {
//
//private static final Logger log = LoggerFactory.getLogger(RelaxProcess.class);
//
//    @Autowired
//    private JobListener jobExecutionListener;
//
//    public RelaxProcess() {
//    }
//
//    @Override
//    public Ts process(Document document) throws Exception {
//        Ts ts = new Ts();
//        ts.setDescripcion(document.select("#panel_descripcion p").text());
//        Elements elems = document.select(".tabla-medidas tbody tr td");
//        ts.setEdad(Integer.valueOf(elems.get(0).text().replaceAll("[^0-9]","")));
//        ts.setMedidas(elems.get(3).text().replaceFirst("Medidas: ",""));
//        ts.setEstatura(elems.get(1).text()
//                .replaceFirst("Altura: ","")
//                .replaceFirst(" Mts.",""));
//        String servicios = document.select(".servicios-ficha p").get(0).childNodes().get(2).toString();
//        ts.setServicios(Stream.of(servicios.split(","))
//                .map(s -> new Servicio(s,false))
//                .collect(Collectors.toSet()));
//
//        try {
//            ts.setTelefono(document.select(".ficha-fono")
//                    .get(document.select(".ficha-fono").size()-1)
//                    .text());
//        } catch (IndexOutOfBoundsException e) {
//            log.error(e.getMessage());
//        }
//        ts.setNombre(document.select("p#nombre-escort").text());
//        Elements precio = document.select(".valor-ficha p");
//        if (precio.size()==3) {
//            ts.setValor(document.select(".valor-ficha p").get(0).toString().replaceAll("[^0-9]",""));
//        } else {
//            try{
//                ts.setValor(document.select(".valor-ficha p").get(0).childNode(1).toString());
//            } catch (IndexOutOfBoundsException e) {
//                log.error(e.getMessage());
//                log.info(document.select(".valor-ficha p").toString());
//                log.debug(ts.getNombre());
//            }
//        }
//        ts.setImagenes(document.select(".cont-galeria div img").stream()
//                .map(e -> e.attr("src"))
//                .filter(s -> !s.equals("/assets/img/banda-cero-retoque.png"))
//                .map(src -> new Imagen("http://relaxchile.cl"+src))
//                .collect(Collectors.toSet()));
//        ts.setImagen(ts.getImagenes().iterator().next().getUrl());
//        ts.setSitio(document.location());
//        ts.setCartel("relaxchile");
//        ts.setIdjob(this.jobExecutionListener.getIdJob());
//        ts.setIdpagina(Integer.valueOf(document.location().substring(document.location().indexOf("escort.index/")+13)));
//        ts.setCiudad("Santiago");
//        return ts;
//    }
//}
