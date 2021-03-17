package it.arsinfo.gc.entity.dao;

import it.arsinfo.gc.entity.model.Area;
import it.arsinfo.gc.entity.model.Carrello;
import it.arsinfo.gc.entity.model.Portale;
import it.arsinfo.gc.entity.model.Transito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransitoDao extends JpaRepository<Transito,Long> {
    List<Transito> findByCarrello(Carrello carrello);
    List<Transito> findByPortale(Portale portale);
    List<Transito> findByTimeBetween(Date start,Date end);
//    List<Transito> findByCarrelloAndTimeBetween(Carrello carrello,Date start,Date end);
//    List<Transito> findByAreaAndTimeBetween(Area area,Date start,Date end);
}
