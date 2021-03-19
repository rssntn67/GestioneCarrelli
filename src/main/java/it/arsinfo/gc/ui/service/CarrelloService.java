package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.model.Carrello;

import java.util.List;

public interface CarrelloService extends EntityService<Carrello>{
    List<Carrello> findAll(String filterText);

}
