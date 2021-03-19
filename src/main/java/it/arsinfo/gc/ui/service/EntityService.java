package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.model.EntityBase;

import java.util.List;

public interface EntityService <S extends EntityBase> {
    S save(S entity);
    void delete(S entity);
    S findById(Long id);
    List<S> findAll();
    long count();
    S add();
}
