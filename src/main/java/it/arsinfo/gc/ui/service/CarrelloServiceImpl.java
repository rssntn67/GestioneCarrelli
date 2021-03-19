package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.dao.CarrelloDao;
import it.arsinfo.gc.entity.model.Carrello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CarrelloServiceImpl implements EntityService<Carrello>{

    @Autowired
    private CarrelloDao carrelloDao;

    private static final Logger log = LoggerFactory.getLogger(CarrelloServiceImpl.class);


    @Override
    public Carrello save(Carrello entity) {
        if (entity == null) {
            log.error("save: null entity cannot be saved");
            return null;
        }
        return carrelloDao.save(entity);
    }

    @Override
    public void delete(Carrello entity) {
        carrelloDao.delete(entity);
    }

    @Override
    public Carrello findById(Long id) {
        return carrelloDao.findById(id).orElse(null);

    }

    @Override
    public List<Carrello> findAll() {
        return carrelloDao.findAll();
    }

    @Override
    public List<Carrello> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return findAll();
        }
        return carrelloDao.findByScanCodeContainingIgnoreCase(stringFilter);
    }

    @Override
    public long count() {
        return carrelloDao.count();
    }

    @Override
    public Carrello add() {
        return new Carrello();
    }

    @PostConstruct
    public void populateCarrelli() {
        List<Carrello> list = new ArrayList<>();
        for (String scanCode : Arrays.asList("ca00001", "ca00002", "ca00003", "ca00004")) {
            Carrello carrello = new Carrello(scanCode);
            list.add(carrello);
        }
        carrelloDao
                .saveAll(
                        list);
    }
}
