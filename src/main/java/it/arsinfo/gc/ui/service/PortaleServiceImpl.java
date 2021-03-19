package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.dao.PortaleDao;
import it.arsinfo.gc.entity.model.Area;
import it.arsinfo.gc.entity.model.Portale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortaleServiceImpl implements PortaleService {

    @Autowired
    private PortaleDao dao;

    private static final Logger log = LoggerFactory.getLogger(PortaleServiceImpl.class);


    @Override
    public Portale save(Portale entity) {
        if (entity == null) {
            log.error("save: null entity cannot be saved");
            return null;
        }
        return dao.save(entity);
    }

    @Override
    public void delete(Portale entity) {
        dao.delete(entity);
    }

    @Override
    public Portale findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public List<Portale> findAll() {
        return dao.findAll();
    }

    public List<Portale> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return findAll();
        }
        return dao.findByPortalCodeContainingIgnoreCase(stringFilter);
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public Portale add() {
        return new Portale();
    }

    @Override
    public List<Portale> findAll(String filterText, Area filterArea) {
        if (filterArea == null)
            return findAll(filterText);
        if (filterText == null)
            return dao.findByArea(filterArea);
        return dao.findByPortalCodeContainingIgnoreCaseAndArea(filterText,filterArea);
    }
}
