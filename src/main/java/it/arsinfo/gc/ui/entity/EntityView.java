package it.arsinfo.gc.ui.entity;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import it.arsinfo.gc.entity.model.EntityBase;
import it.arsinfo.gc.ui.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class EntityView<T extends EntityBase> extends VerticalLayout {

    private final Grid<T> grid;
    final private EntityService<T> service;
    private final EntityForm<T> form;

    public EntityView(@Autowired EntityService<T> service, Grid<T> grid, EntityForm<T> form) {
        this.form=form;
        this.grid=grid;
        this.service=service;
        addClassName("gc-view");
        form.addClassName("form");
        setSizeFull();
    }

    public HorizontalLayout getToolBar(Component...components) {
        HorizontalLayout toolbar = new HorizontalLayout(components);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public Div getContent() {
        Div content = new Div(getGrid(),getForm());
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    public EntityForm<T> getForm() {
        return form;
    }

    public void save(T entity) {
        service.save(entity);
        updateList();
        closeEditor();
    }

    public void delete(T entity) {
        service.delete(entity);
        updateList();
        closeEditor();
    }

    public void edit(T entity) {
        if (entity == null) {
            closeEditor();
        } else {
            form.setEntity(entity);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    public void closeEditor() {
        form.setEntity(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    public Button getAddButton() {
        Button addButton = new Button("Add");
        addButton.addClickListener(click -> add());
        return addButton;

    }

    public void configureGrid(String...columns) {
        grid.addClassName("grid");
        grid.setColumns(columns);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                edit(event.getValue()));
    }

    public void updateList() {
        grid.setItems(filter());
    }

    void add() {
        grid.asSingleSelect().clear();
        edit(service.add());
    }

    public abstract List<T> filter();

    public Grid<T> getGrid() {
        return grid;
    }
}
