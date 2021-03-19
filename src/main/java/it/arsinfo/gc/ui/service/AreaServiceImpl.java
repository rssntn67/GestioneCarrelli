package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.dao.AreaDao;
import it.arsinfo.gc.entity.model.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao dao;

    private static final Logger log = LoggerFactory.getLogger(AreaServiceImpl.class);


    @Override
    public Area save(Area entity) {
        if (entity == null) {
            log.error("save: null entity cannot be saved");
            return null;
        }
        return dao.save(entity);
    }

    @Override
    public void delete(Area entity) {
        dao.delete(entity);
    }

    @Override
    public Area findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public List<Area> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Area> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return findAll();
        }
        return dao.findByAreaCodeContainingIgnoreCase(stringFilter);
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public Area add() {
        return new Area();
    }
}
