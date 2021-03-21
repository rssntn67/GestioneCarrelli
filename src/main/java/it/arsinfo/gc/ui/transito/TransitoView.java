package it.arsinfo.gc.ui.transito;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.arsinfo.gc.entity.model.Carrello;
import it.arsinfo.gc.entity.model.Portale;
import it.arsinfo.gc.entity.model.Transito;
import it.arsinfo.gc.ui.MainLayout;
import it.arsinfo.gc.ui.entity.EntityView;
import it.arsinfo.gc.ui.service.EntityService;
import it.arsinfo.gc.ui.service.PortaleService;
import it.arsinfo.gc.ui.service.TransitoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Route(value="gc/transiti", layout = MainLayout.class)
@PageTitle("Transiti | GC")
public class TransitoView extends EntityView<Transito> {
    private final TransitoService service;
    private final ComboBox<Portale> filterPortale = new ComboBox<>();
    private final ComboBox<Carrello> filterCarrello = new ComboBox<>();
    private final DateTimePicker fromDate = new DateTimePicker();
    private final DateTimePicker toDate = new DateTimePicker();


    public TransitoView(@Autowired TransitoService service,
                        @Autowired EntityService<Carrello> carrelloService,
                        @Autowired PortaleService portaleService) {
        super(service,
                new Grid<>(Transito.class),
                new TransitoForm(
                        new BeanValidationBinder<>(Transito.class),
                        carrelloService.findAll(),
                        portaleService.findAll()
                )
        );
        this.service=service;
        configureGrid("carrello.code","portale.code","time");
        getForm().addListener(TransitoForm.SaveEvent.class, e -> save(e.getTransito()));
        getForm().addListener(TransitoForm.DeleteEvent.class, e -> delete(e.getTransito()));
        getForm().addListener(TransitoForm.CloseEvent.class, e -> closeEditor());


        filterPortale.setItems(portaleService.findAll());
        filterPortale.setItemLabelGenerator(Portale::getCode);
        filterPortale.setPlaceholder("Select Portale to search");
        filterPortale.addValueChangeListener(e -> updateList());

        filterCarrello.setItems(carrelloService.findAll());
        filterCarrello.setItemLabelGenerator(Carrello::getCode);
        filterCarrello.setPlaceholder("Select Carrello to search");
        filterCarrello.addValueChangeListener(e -> updateList());

        LocalDateTime now = LocalDateTime.now();
        fromDate.setDatePlaceholder("Select From Date");
        fromDate.setTimePlaceholder("Select From Time");
        fromDate.setStep(Duration.ofMinutes(30));
        fromDate.setValue(now.minus(Duration.ofDays(1)));
        fromDate.addValueChangeListener(e->updateList());

        toDate.setDatePlaceholder("Select To Date");
        toDate.setTimePlaceholder("Select To Time");
        toDate.setStep(Duration.ofMinutes(30));
        toDate.setValue(now);
        toDate.addValueChangeListener(e->updateList());

        add(getToolBar(fromDate,toDate,filterPortale,filterCarrello,getAddButton()),getContent());
        updateList();
        closeEditor();

    }

    @Override
    public List<Transito> filter() {
        return service.findAll(filterCarrello.getValue(),filterPortale.getValue(),getFrom(),getTo());
    }

    private Date getFrom() {
        return Date.from(fromDate.getValue().atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date getTo() {
        return Date.from(toDate.getValue().atZone(ZoneId.systemDefault()).toInstant());
    }

}
