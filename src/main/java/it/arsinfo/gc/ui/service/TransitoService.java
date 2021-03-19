package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.model.Area;
import it.arsinfo.gc.entity.model.Carrello;
import it.arsinfo.gc.entity.model.Portale;
import it.arsinfo.gc.entity.model.Transito;

import java.util.List;

public interface TransitoService extends  EntityService<Transito>{

    List<Transito> findAll(Carrello carrello, Portale portale);
}
