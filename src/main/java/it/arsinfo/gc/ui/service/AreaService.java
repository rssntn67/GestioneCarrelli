package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.model.Area;

import java.util.List;

public interface AreaService extends EntityService<Area>{
    List<Area> findAll(String filterText);

}
