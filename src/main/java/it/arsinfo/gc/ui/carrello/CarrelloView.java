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
import it.arsinfo.gc.ui.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */

@Route(value="carrelli", layout = MainLayout.class)
@PageTitle("Carrelli | GA")
public class CarrelloView extends EntityView<Carrello> {

    private final TextField filterText = new TextField();
    private final EntityService<Carrello> service;

    public CarrelloView(@Autowired EntityService<Carrello> service) {
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
