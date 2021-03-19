package it.arsinfo.gc.ui.transito;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateTimeToDateConverter;
import it.arsinfo.gc.entity.model.Carrello;
import it.arsinfo.gc.entity.model.Portale;
import it.arsinfo.gc.entity.model.Transito;
import it.arsinfo.gc.ui.entity.EntityForm;

import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

public class TransitoForm extends EntityForm<Transito> {

    final ComboBox<Portale> portale = new ComboBox<>("Portale");
    final ComboBox<Carrello> carrello = new ComboBox<>("Carrello");
    final DateTimePicker time = new DateTimePicker();

    public TransitoForm(Binder<Transito> binder, List<Carrello> carrelli, List<Portale> portali) {
        super(binder);
        carrello.setItems(carrelli);
        carrello.setItemLabelGenerator(Carrello::getCode);

        portale.setItems(portali);
        portale.setItemLabelGenerator(Portale::getCode);

        time.setReadOnly(true);

        binder.forField(portale)
                .asRequired()
                .withValidator(Objects::nonNull,"Portale deve essere scelto")
                .withValidationStatusHandler(status -> portale.setPlaceholder(status
                        .getMessage().orElse("")))
                .bind(Transito::getPortale,Transito::setPortale);

        binder.forField(carrello)
                .asRequired()
                .withValidator(Objects::nonNull,"Carrello deve essere scelto")
                .withValidationStatusHandler(status -> carrello.setPlaceholder(status
                        .getMessage().orElse("")))
                .bind(Transito::getCarrello,Transito::setCarrello);

        binder.forField(time)
                .asRequired()
                .withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
                .bind(Transito::getTime,null);

        add(portale,carrello,time,createButtonsLayout());

        getSave().addClickListener(event -> {
            if (validate()) {
                fireEvent(new SaveEvent(this,getEntity()));
            }

        });
        getDelete().addClickListener(event -> fireEvent(new DeleteEvent(this, getEntity())));
        getClose().addClickListener(event -> fireEvent(new CloseEvent(this)));

    }

    // Events
    public static abstract class TransitoFormEvent extends ComponentEvent<TransitoForm> {
        private final Transito Transito;

        protected TransitoFormEvent(TransitoForm source, Transito Transito) {
            super(source, false);
            this.Transito = Transito;
        }

        public Transito getTransito() {
            return Transito;
        }
    }

    public static class SaveEvent extends TransitoFormEvent {
        SaveEvent(TransitoForm source, Transito Transito) {
            super(source, Transito);
        }
    }

    public static class DeleteEvent extends TransitoFormEvent {
        DeleteEvent(TransitoForm source, Transito Transito) {
            super(source, Transito);
        }

    }

    public static class CloseEvent extends TransitoFormEvent {
        CloseEvent(TransitoForm source) {
            super(source, null);
        }
    }

}
