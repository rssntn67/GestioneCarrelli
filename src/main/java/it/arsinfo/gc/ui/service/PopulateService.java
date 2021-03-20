package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.dao.AreaDao;
import it.arsinfo.gc.entity.dao.CarrelloDao;
import it.arsinfo.gc.entity.dao.PortaleDao;
import it.arsinfo.gc.entity.dao.TransitoDao;
import it.arsinfo.gc.entity.model.Area;
import it.arsinfo.gc.entity.model.Carrello;
import it.arsinfo.gc.entity.model.Portale;
import it.arsinfo.gc.entity.model.Transito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PopulateService {
    @Autowired
    private CarrelloDao carrelloDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private PortaleDao portaleDao;

    @Autowired
    private TransitoDao transitoDao;

    @PostConstruct
    public void populate() {

        carrelloDao
                .saveAll(
                        Stream.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)
                        .map(scanCode -> new Carrello("ca0000"+scanCode))
                .collect(Collectors.toList())
                );

        areaDao
                .saveAll(
                        Stream.of(1,2,3,4,5)
                                .map(areaCode ->  {
                                    Area area = new Area("area0000"+areaCode);
                                    if (areaCode == 1) {
                                        area.setAreaType(Area.AreaType.Arrivals);
                                        area.setDescription("Arrivi");
                                    }
                                    if (areaCode == 2) {
                                        area.setAreaType(Area.AreaType.Departures);
                                        area.setDescription("Partenze");
                                    }
                                    if (areaCode == 3) {
                                        area.setAreaType(Area.AreaType.Parking);
                                        area.setDescription("Parcheggio Sosta Breve");
                                    }
                                    if (areaCode == 4) {
                                        area.setAreaType(Area.AreaType.Private);
                                        area.setDescription("Riservata operatori aeroportuali");
                                    }
                                    if (areaCode == 5) {
                                        area.setAreaType(Area.AreaType.Terminal);
                                        area.setDescription("terminale A5");
                                    }
                                    return area;
                                })
                                .collect(Collectors.toList()));

        portaleDao.saveAll(Stream.of(11,12,13,14,15,21,22,23,31,41).map( id -> {
            Portale portale = new Portale("portal000"+id);
            if (id == 11) {
                portale.setArea(areaDao.findByAreaCodeContainingIgnoreCase("1").iterator().next());
                portale.setDescription("Portale 1 Area 1");
            }
            if (id == 12) {
                portale.setArea(areaDao.findByAreaCodeContainingIgnoreCase("2").iterator().next());
                portale.setDescription("Portale 1 Area 2");
            }
            if (id == 13) {
                portale.setArea(areaDao.findByAreaCodeContainingIgnoreCase("3").iterator().next());
                portale.setDescription("Portale 1 Area 3");
            }
            if (id == 14) {
                portale.setArea(areaDao.findByAreaCodeContainingIgnoreCase("4").iterator().next());
                portale.setDescription("Portale 1 Area 4");
            }
            if (id == 15) {
                portale.setArea(areaDao.findByAreaCodeContainingIgnoreCase("5").iterator().next());
                portale.setDescription("Portale 1 Area 5");
            }

            if (id == 21) {
                portale.setArea(areaDao.findByAreaCodeContainingIgnoreCase("1").iterator().next());
                portale.setDescription("Portale 2 Area 1");
            }
            if (id == 31) {
                portale.setArea(areaDao.findByAreaCodeContainingIgnoreCase("1").iterator().next());
                portale.setDescription("Portale 3 Area 1");
            }
            if (id == 41) {
                portale.setArea(areaDao.findByAreaCodeContainingIgnoreCase("1").iterator().next());
                portale.setDescription("Portale 4 Area 1");
            }
            if (id == 22) {
                portale.setArea(areaDao.findByAreaCodeContainingIgnoreCase("2").iterator().next());
                portale.setDescription("Portale 2 Area 2");
            }
            if (id == 23) {
                portale.setArea(areaDao.findByAreaCodeContainingIgnoreCase("2").iterator().next());
                portale.setDescription("Portale 2 Area 3");
            }

            return portale;
        }).collect(Collectors.toList()));

        for (int i=0;i<300;i++) {
            Transito t1 = new Transito();
            t1.setPortale(
                portaleDao.findByPortalCode("portal000"
                        +getRandom(
                                new int[]{11,12,13,14,15,21,22,23,31,41}
                        )
                )
            );
            t1.setCarrello(carrelloDao.findByScanCode("ca0000"+getRandom(new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15})));
            t1.setTime(new Date(System.currentTimeMillis() - i * 60000));
            transitoDao.save(t1);

        }

    }

    public static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

}
