package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.dao.AreaDao;
import it.arsinfo.gc.entity.model.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AreaServiceImpl implements EntityService<Area>{

    @Autowired
    private AreaDao areaDao;

    private static final Logger log = LoggerFactory.getLogger(AreaServiceImpl.class);


    @Override
    public Area save(Area entity) {
        if (entity == null) {
            log.error("save: null entity cannot be saved");
            return null;
        }
        return areaDao.save(entity);
    }

    @Override
    public void delete(Area entity) {
        areaDao.delete(entity);
    }

    @Override
    public Area findById(Long id) {
        return areaDao.findById(id).orElse(null);
    }

    @Override
    public List<Area> findAll() {
        return areaDao.findAll();
    }

    @Override
    public List<Area> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return findAll();
        }
        return areaDao.findByAreaCodeContainingIgnoreCase(stringFilter);
    }

    @Override
    public long count() {
        return areaDao.count();
    }

    @Override
    public Area add() {
        return new Area();
    }

    @PostConstruct
    public void populateArea() {
        areaDao
                .saveAll(
                        Stream.of(1,2,3,4,5)
                                .map(scanCode ->  {
                                    Area area = new Area("area0000"+scanCode);
                                    if (scanCode == 1) {
                                        area.setAreaType(Area.AreaType.Arrivals);
                                        area.setDescription("Arrivi");
                                    }
                                    if (scanCode == 2) {
                                        area.setAreaType(Area.AreaType.Departures);
                                        area.setDescription("Partenze");
                                    }
                                    if (scanCode == 3) {
                                        area.setAreaType(Area.AreaType.Parking);
                                        area.setDescription("Parcheggio Sosta Breve");
                                    }
                                    if (scanCode == 4) {
                                        area.setAreaType(Area.AreaType.Private);
                                        area.setDescription("Riservata operatori aeroportuali");
                                    }
                                    if (scanCode == 5) {
                                        area.setAreaType(Area.AreaType.Terminal);
                                        area.setDescription("terminale A5");
                                    }
                                    return area;
                                })
                                .collect(Collectors.toList()));
    }
}
