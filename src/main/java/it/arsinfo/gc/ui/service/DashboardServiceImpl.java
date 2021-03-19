package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.dao.AreaDao;
import it.arsinfo.gc.entity.dao.CarrelloDao;
import it.arsinfo.gc.entity.dao.PortaleDao;
import it.arsinfo.gc.entity.dao.TransitoDao;
import it.arsinfo.gc.entity.model.Area;
import it.arsinfo.gc.entity.model.Portale;
import it.arsinfo.gc.entity.model.Transito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private CarrelloDao carrelloDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private PortaleDao portaleDao;

    @Autowired
    private TransitoDao transitoDao;

    @Override
    public long countArea() {
        return areaDao.count();
    }

    @Override
    public long countCarrello() {
        return carrelloDao.count();
    }

    @Override
    public long countPortale() {
        return portaleDao.count();
    }

    @Override
    public long countTransito() {
        return transitoDao.count();
    }

    @Override
    public Map<String, Integer> getPortaleStats() {
        final Map<String,Integer> statMap = new HashMap<>();
        for (Portale p : portaleDao.findAll()) {
            if (!statMap.containsKey(p.getArea().getAreaCode())) {
                statMap.put(p.getArea().getAreaCode(), 0);
            }
            statMap.put(p.getArea().getAreaCode(), statMap.get(p.getArea().getAreaCode())+1);
        }
        return statMap;
    }

    @Override
    public Map<String, Integer> getTransitiStats() {
        final Map<String,Integer> statMap = new HashMap<>();
        final Map<Long,Portale> pMap= portaleDao.findAll().stream().collect(Collectors.toMap(Portale::getId, Function.identity()));
        for (Transito t: transitoDao.findAll()) {
            Portale p = pMap.get(t.getPortale().getId());
            if (!statMap.containsKey(p.getArea().getAreaCode())) {
                statMap.put(p.getArea().getAreaCode(), 0);
            }
            statMap.put(p.getArea().getAreaCode(), statMap.get(p.getArea().getAreaCode())+1);
        }
        return statMap;
    }
}
