package it.arsinfo.gc.entity.dao;

import it.arsinfo.gc.entity.model.Carrello;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarrelloDao extends JpaRepository<Carrello,Long> {
    Carrello findByScanCode(String scanCode);
    List<Carrello> findByScanCodeContainingIgnoreCase(String scan);
}
