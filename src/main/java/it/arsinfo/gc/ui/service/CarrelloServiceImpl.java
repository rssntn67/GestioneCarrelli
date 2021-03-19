package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.dao.CarrelloDao;
import it.arsinfo.gc.entity.model.Carrello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrelloServiceImpl implements CarrelloService {

    @Autowired
    private CarrelloDao dao;

    private static final Logger log = LoggerFactory.getLogger(CarrelloServiceImpl.class);


    @Override
    public Carrello save(Carrello entity) {
        if (entity == null) {
            log.error("save: null entity cannot be saved");
            return null;
        }
        return dao.save(entity);
    }

    @Override
    public void delete(Carrello entity) {
        dao.delete(entity);
    }

    @Override
    public Carrello findById(Long id) {
        return dao.findById(id).orElse(null);

    }

    @Override
    public List<Carrello> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Carrello> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return findAll();
        }
        return dao.findByScanCodeContainingIgnoreCase(stringFilter);
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public Carrello add() {
        return new Carrello();
    }

}
