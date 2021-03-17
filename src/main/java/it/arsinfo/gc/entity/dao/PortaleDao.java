package it.arsinfo.gc.entity.dao;

import it.arsinfo.gc.entity.model.Area;
import it.arsinfo.gc.entity.model.Portale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortaleDao extends JpaRepository<Portale,Long> {
    List<Portale> findByPortalCodeContainingIgnoreCase(String portal);
    List<Portale> findByArea(Area area);
}
