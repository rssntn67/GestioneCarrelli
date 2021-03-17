package it.arsinfo.gc.entity.dao;

import it.arsinfo.gc.entity.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaDao extends JpaRepository<Area,Long> {
    List<Area> findByAreaCodeContainingIgnoreCase(String area);
    List<Area> findByAreaType(Area.AreaType type);
    List<Area> findByAreaCodeContainingIgnoreCaseAndAreaType(String area,Area.AreaType type);
}
