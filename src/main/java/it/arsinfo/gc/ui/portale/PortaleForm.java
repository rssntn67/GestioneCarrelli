package it.arsinfo.gc.ui.portale;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import it.arsinfo.gc.entity.model.Area;
import it.arsinfo.gc.entity.model.Portale;
import it.arsinfo.gc.ui.entity.EntityForm;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

public class PortaleForm extends EntityForm<Portale> {

    final TextField portalCode = new TextField("Portal Code");
    final ComboBox<Area> area = new ComboBox<>("Area");
    final TextField description = new TextField("Description");
    final TextField latitude = new TextField("Latitude");
    final TextField longitude = new TextField("Longitude");

    public PortaleForm(Binder<Portale> binder, List<Area> areas) {
        super(binder);
        area.setItems(areas);
        area.setItemLabelGenerator(Area::getAreaCode);
        binder.forField(portalCode)
                .asRequired()
                .withValidator( code -> !StringUtils.containsWhitespace(code),"Non può contenere spazi")
                .withValidationStatusHandler(status -> portalCode.setPlaceholder(status
                        .getMessage().orElse("")))
                .bind(Portale::getPortalCode,Portale::setPortalCode);
        binder.forField(area)
                .asRequired()
                .withValidator(Objects::nonNull,"Area deve essere scelto")
                .withValidationStatusHandler(status -> area.setPlaceholder(status
                        .getMessage().orElse("")))
                .bind(Portale::getArea,Portale::setArea);

        binder.forField(latitude)
                .asRequired()
                .withConverter(new StringToDoubleConverter("not a number"))
                .bind(Portale::getLatitude,Portale::setLatitude);
        binder.forField(longitude)
                .asRequired()
                .withConverter(new StringToDoubleConverter("not a number"))
                .bind(Portale::getLongitude,Portale::setLongitude);
        binder.forField(description).bind(Portale::getDescription,Portale::setDescription);

        add(portalCode,area,latitude,longitude,description,createButtonsLayout());

        getSave().addClickListener(event -> {
            if (validate()) {
                fireEvent(new SaveEvent(this,getEntity()));
            }

        });
        getDelete().addClickListener(event -> fireEvent(new DeleteEvent(this, getEntity())));
        getClose().addClickListener(event -> fireEvent(new CloseEvent(this)));

    }

    // Events
    public static abstract class PortaleFormEvent extends ComponentEvent<PortaleForm> {
        private final Portale portale;

        protected PortaleFormEvent(PortaleForm source, Portale portale) {
            super(source, false);
            this.portale = portale;
        }

        public Portale getPortale() {
            return portale;
        }
    }

    public static class SaveEvent extends PortaleFormEvent {
        SaveEvent(PortaleForm source, Portale portale) {
            super(source, portale);
        }
    }

    public static class DeleteEvent extends PortaleFormEvent {
        DeleteEvent(PortaleForm source, Portale portale) {
            super(source, portale);
        }

    }

    public static class CloseEvent extends PortaleFormEvent {
        CloseEvent(PortaleForm source) {
            super(source, null);
        }
    }

}
