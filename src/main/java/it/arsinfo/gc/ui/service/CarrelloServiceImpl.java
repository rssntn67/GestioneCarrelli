package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.dao.CarrelloDao;
import it.arsinfo.gc.entity.model.Carrello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CarrelloServiceImpl implements EntityService<Carrello>{
    @Autowired
    private CarrelloDao carrelloDao;

    private static final Logger log = LoggerFactory.getLogger(CarrelloDao.class);


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
        return carrelloDao.findById(id).get();
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

    @PostConstruct
    public void populateCarrelli() {
        carrelloDao
                .saveAll(
                        Stream.of("ca00001","ca00002","ca00003","ca00004")
                                .map(scanCode ->  new Carrello(scanCode))
                                .collect(Collectors.toList()));
    }
}
