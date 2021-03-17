package it.arsinfo.gc.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import it.arsinfo.gc.entity.model.Carrello;
import it.arsinfo.gc.ui.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;

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
@Route("carrelli")
@CssImport("./styles/shared-styles.css")
public class CarrelloView extends VerticalLayout {

    private TextField filterText = new TextField();
    private Grid<Carrello> grid = new Grid<Carrello>(Carrello.class);
    private EntityService<Carrello> service;
    private CarrelloForm form;

    public CarrelloView(@Autowired EntityService<Carrello> service) {
        this.service=service;
        addClassName("list-view");
        setSizeFull();
        getToolBar();
        configureGrid();

        form = new CarrelloForm();
        form.addListener(CarrelloForm.SaveEvent.class, this::save);
        form.addListener(CarrelloForm.DeleteEvent.class, this::delete);
        form.addListener(CarrelloForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid,form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(),content);
        updateList();
        closeEditor();


    }

    private void save(CarrelloForm.SaveEvent event) {
        service.save(event.getCarrello());
        updateList();
        closeEditor();
    }

    private void delete(CarrelloForm.DeleteEvent event) {
        service.delete(event.getCarrello());
        updateList();
        closeEditor();
    }

    public void edit(Carrello carrello) {
        if (carrello == null) {
            closeEditor();
        } else {
            form.setCarrello(carrello);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setCarrello(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addButton = new Button("Add");
        addButton.addClickListener(click -> add());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addButton);
        toolbar.addClassName("toolbar");
        return toolbar;

    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setColumns("scanCode");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                edit(event.getValue()));
    }

    private void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }

    void add() {
        grid.asSingleSelect().clear();
        edit(new Carrello());
    }
}
