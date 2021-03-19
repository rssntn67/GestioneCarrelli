package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.model.Area;
import it.arsinfo.gc.entity.model.Portale;

import java.util.List;

public interface PortaleService extends  EntityService<Portale>{

    List<Portale> findAll(String filterText, Area filterArea);
}
