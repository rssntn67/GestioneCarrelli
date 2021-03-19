package it.arsinfo.gc.ui.portale;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.arsinfo.gc.entity.model.Area;
import it.arsinfo.gc.entity.model.Portale;
import it.arsinfo.gc.ui.MainLayout;
import it.arsinfo.gc.ui.entity.EntityView;
import it.arsinfo.gc.ui.service.EntityService;
import it.arsinfo.gc.ui.service.PortaleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value="portali", layout = MainLayout.class)
@PageTitle("Portali | GA")
public class PortaleView extends EntityView<Portale> {
    private final PortaleService service;
    private final TextField filterText = new TextField();
    private final ComboBox<Area> filterArea = new ComboBox<>();

    public PortaleView(@Autowired PortaleService service, @Autowired EntityService<Area> areaService) {
        super(service,
                new Grid<>(Portale.class),
                new PortaleForm(
                        new BeanValidationBinder<>(Portale.class),
                        areaService.findAll()));
        this.service=service;
        configureGrid("portalCode","area.areaCode","latitude","longitude","description");
        getForm().addListener(PortaleForm.SaveEvent.class, e -> save(e.getPortale()));
        getForm().addListener(PortaleForm.DeleteEvent.class, e -> delete(e.getPortale()));
        getForm().addListener(PortaleForm.CloseEvent.class, e -> closeEditor());

        filterText.setPlaceholder("Filter by...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        filterArea.setItems(areaService.findAll());
        filterArea.setPlaceholder("Select Area to search");
        filterArea.addValueChangeListener(e -> updateList());


        add(getToolBar(filterText,filterArea,getAddButton()),getContent());
        updateList();
        closeEditor();

    }

    @Override
    public List<Portale> filter() {
        return service.findAll(filterText.getValue(),filterArea.getValue());
    }
}
