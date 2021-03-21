package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.dao.CarrelloDao;
import it.arsinfo.gc.entity.dao.PortaleDao;
import it.arsinfo.gc.entity.dao.TransitoDao;
import it.arsinfo.gc.entity.model.Carrello;
import it.arsinfo.gc.entity.model.Portale;
import it.arsinfo.gc.entity.model.Transito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TransitoServiceImpl implements TransitoService {

    @Autowired
    private TransitoDao dao;

    @Autowired
    private PortaleDao portaleDao;

    @Autowired
    private CarrelloDao carrelloDao;

    private static final Logger log = LoggerFactory.getLogger(TransitoServiceImpl.class);


    @Override
    public Transito save(Transito entity) {
        if (entity == null) {
            log.error("save: null entity cannot be saved");
            return null;
        }
        return dao.save(entity);
    }

    @Override
    public void delete(Transito entity) {
        dao.delete(entity);
    }

    @Override
    public Transito findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public List<Transito> findAll() {
        return dao.findAll();
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public Transito add() {
        return new Transito();
    }

    @Override
    public List<Transito> findAll(Carrello carrello, Portale portale, Date start, Date end) {
        if (carrello == null && portale == null)
            return populate(dao.findByTimeBetween(start,end));
        if (carrello == null)
            return populate(dao.findByPortaleAndTimeBetween(portale,start,end));
        if (portale == null)
            return populate(dao.findByCarrelloAndTimeBetween(carrello,start,end));

        return populate(dao.findByPortaleAndCarrelloAndTimeBetween(portale,carrello,start,end));
    }

    private List<Transito> populate(List<Transito> transiti) {
        final Map<Long,Carrello> carrelloMap = carrelloDao.findAll().stream().collect(Collectors.toMap(Carrello::getId, Function.identity())) ;
        final Map<Long,Portale> portaleMap = portaleDao.findAll().stream().collect(Collectors.toMap(Portale::getId, Function.identity())) ;
        return transiti.stream().map(t -> {
            Transito nt = new Transito();
            nt.setId(t.getId());
            nt.setTime(t.getTime());
            nt.setCarrello(carrelloMap.get(t.getCarrello().getId()));
            nt.setPortale(portaleMap.get(t.getPortale().getId()));
            return nt;
        }).collect(Collectors.toList());

    }
}
