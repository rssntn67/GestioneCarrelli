package it.arsinfo.gc.ui.area;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.arsinfo.gc.entity.model.Area;
import it.arsinfo.gc.ui.MainLayout;
import it.arsinfo.gc.ui.entity.EntityView;
import it.arsinfo.gc.ui.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value="gc/aree", layout = MainLayout.class)
@PageTitle("Aree | GC")
public class AreaView extends EntityView<Area> {
    private final AreaService service;
    private final TextField filterText = new TextField();

    public AreaView(@Autowired AreaService service) {
        super(service, new Grid<>(Area.class), new AreaForm(new BeanValidationBinder<>(Area.class)));
        this.service=service;
        configureGrid("areaCode","areaType","description");
        getForm().addListener(AreaForm.SaveEvent.class, e -> save(e.getArea()));
        getForm().addListener(AreaForm.DeleteEvent.class, e -> delete(e.getArea()));
        getForm().addListener(AreaForm.CloseEvent.class, e -> closeEditor());

        filterText.setPlaceholder("Filter by...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        add(getToolBar(filterText,getAddButton()),getContent());
        updateList();
        closeEditor();

    }

    @Override
    public List<Area> filter() {
        return service.findAll(filterText.getValue());
    }
}
