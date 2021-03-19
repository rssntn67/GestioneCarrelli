package it.arsinfo.gc.ui.carrello;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import it.arsinfo.gc.entity.model.Carrello;
import it.arsinfo.gc.ui.entity.EntityForm;
import org.springframework.util.StringUtils;

public class CarrelloForm extends EntityForm<Carrello> {
    TextField scanCode = new TextField("Scan Code");

    public CarrelloForm(Binder<Carrello> binder) {
        super(binder);
        binder.forField(scanCode)
                .asRequired()
                .withValidator( code -> !StringUtils.containsWhitespace(code),"Non può contenere spazi")
                .withValidationStatusHandler(status -> scanCode.setPlaceholder(status
                        .getMessage().orElse("")))
                .bind(Carrello::getScanCode,Carrello::setScanCode);
        add(scanCode,createButtonsLayout());

        getSave().addClickListener(event -> {
            if (validate()) {
                fireEvent(new CarrelloForm.SaveEvent(this,getEntity()));
            }

        });
        getDelete().addClickListener(event -> fireEvent(new CarrelloForm.DeleteEvent(this, getEntity())));
        getClose().addClickListener(event -> fireEvent(new CarrelloForm.CloseEvent(this)));

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
