package it.arsinfo.gc.ui.carrello;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.arsinfo.gc.entity.model.Carrello;
import it.arsinfo.gc.ui.MainLayout;
import it.arsinfo.gc.ui.entity.EntityView;
import it.arsinfo.gc.ui.service.CarrelloService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value="carrelli", layout = MainLayout.class)
@PageTitle("Carrelli | GA")
public class CarrelloView extends EntityView<Carrello> {

    private final TextField filterText = new TextField();
    private final CarrelloService service;

    public CarrelloView(@Autowired CarrelloService service) {
        super(service, new Grid<>(Carrello.class), new CarrelloForm(new BeanValidationBinder<>(Carrello.class)));
        this.service=service;
        configureGrid("scanCode");

        getForm().addListener(CarrelloForm.SaveEvent.class, e -> save(e.getCarrello()));
        getForm().addListener(CarrelloForm.DeleteEvent.class, e -> delete(e.getCarrello()));
        getForm().addListener(CarrelloForm.CloseEvent.class, e -> closeEditor());

        filterText.setPlaceholder("Filter by...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        add(getToolBar(filterText,getAddButton()),getContent());
        updateList();
        closeEditor();


    }


    @Override
    public List<Carrello> filter() {
        return service.findAll(filterText.getValue());
    }
}
