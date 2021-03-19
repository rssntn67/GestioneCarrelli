package it.arsinfo.gc.ui.carrello;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import it.arsinfo.gc.entity.model.Carrello;

public class CarrelloForm extends FormLayout {
    Binder<Carrello> binder = new BeanValidationBinder<>(Carrello.class);
    TextField scanCode = new TextField("Scan Code");

    private Carrello carrello;
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public CarrelloForm() {
        addClassName("carrello-form");
        binder.bindInstanceFields(this);
        add(scanCode,createButtonsLayout());

    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, carrello)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(carrello);
            fireEvent(new SaveEvent(this, carrello));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
            binder.readBean(carrello);
    }

    // Events
    public static abstract class CarrelloFormEvent extends ComponentEvent<CarrelloForm> {
        private final Carrello carrello;

        protected CarrelloFormEvent(CarrelloForm source, Carrello carrello) {
            super(source, false);
            this.carrello = carrello;
        }

        public Carrello getCarrello() {
            return carrello;
        }
    }

    public static class SaveEvent extends CarrelloFormEvent {
        SaveEvent(CarrelloForm source, Carrello carrello) {
            super(source, carrello);
        }
    }

    public static class DeleteEvent extends CarrelloFormEvent {
        DeleteEvent(CarrelloForm source, Carrello carrello) {
            super(source, carrello);
        }

    }

    public static class CloseEvent extends CarrelloFormEvent {
        CloseEvent(CarrelloForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
