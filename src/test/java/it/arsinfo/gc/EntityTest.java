package it.arsinfo.gc;

import it.arsinfo.gc.entity.dao.CarrelloDao;
import it.arsinfo.gc.entity.model.Carrello;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityTest {
    @Autowired
    private CarrelloDao carrelloDao;


    @Before
    public void setUp() {
    }

    @After
    public void clearDown() {
    }

    @Test
    public void testCarrelloCrud() throws Exception {
        assertEquals(4, carrelloDao.count());
        assertEquals(4,carrelloDao.findByScanCodeContainingIgnoreCase("ca").size());
        assertEquals(1,carrelloDao.findByScanCodeContainingIgnoreCase("0004").size());
        assertEquals(0,carrelloDao.findByScanCodeContainingIgnoreCase("0005").size());
        Carrello carrello = new Carrello("ca00005");
        carrello = carrelloDao.save(carrello);
        assertNotNull(carrello);
        assertNotNull(carrello.getId());
        assertEquals("ca00005", carrello.getScanCode());
        assertEquals(5, carrelloDao.count());

        Carrello fromdb = carrelloDao.findById(carrello.getId()).get();
        assertNotNull(fromdb);
        assertEquals(carrello.getId().longValue(), fromdb.getId());
        assertEquals("ca00005", fromdb.getScanCode());
        fromdb.setScanCode("ca00006");

        carrelloDao.save(fromdb);

        Carrello fromdb2 = carrelloDao.findById(carrello.getId()).get();
        assertNotNull(fromdb2);
        assertEquals(carrello.getId().longValue(), fromdb2.getId());
        assertEquals("ca00006", fromdb2.getScanCode());

        carrelloDao.delete(fromdb);
        assertEquals(4, carrelloDao.count());
        assertEquals(1,carrelloDao.findByScanCodeContainingIgnoreCase("0001").size());
        assertEquals(1,carrelloDao.findByScanCodeContainingIgnoreCase("0002").size());
        assertEquals(1,carrelloDao.findByScanCodeContainingIgnoreCase("0003").size());
        assertEquals(1,carrelloDao.findByScanCodeContainingIgnoreCase("0004").size());
        assertEquals(0,carrelloDao.findByScanCodeContainingIgnoreCase("0005").size());
        assertEquals(0,carrelloDao.findByScanCodeContainingIgnoreCase("0006").size());




    }

}
