package it.arsinfo.gc.ui.area;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import it.arsinfo.gc.entity.model.Area;
import it.arsinfo.gc.ui.entity.EntityForm;
import org.springframework.util.StringUtils;

import java.util.EnumSet;
import java.util.Objects;

public class AreaForm extends EntityForm<Area> {

    final TextField areaCode = new TextField("Area Code");
    final ComboBox<Area.AreaType> areaType = new ComboBox<>("Area Type", EnumSet.allOf(Area.AreaType.class));
    final TextField description = new TextField("Description");

    public AreaForm(Binder<Area> binder) {
        super(binder);
        binder.forField(areaCode)
                .asRequired()
                .withValidator( code -> !StringUtils.containsWhitespace(code),"Non può contenere spazi")
                .withValidationStatusHandler(status -> {
                    areaCode.setPlaceholder(status
                            .getMessage().orElse(""));
                })
                .bind(Area::getAreaCode,Area::setAreaCode);
        binder.forField(areaType)
                .asRequired()
                .withValidator(Objects::nonNull,"AreaType deve essere scelto")
                .withValidationStatusHandler(status -> {
                    areaType.setPlaceholder(status
                            .getMessage().orElse(""));
                })
                .bind(Area::getAreaType,Area::setAreaType);
        binder.forField(description).bind(Area::getDescription,Area::setDescription);
        add(areaCode,areaType,description,createButtonsLayout());

        getSave().addClickListener(event -> {
            if (validate()) {
                fireEvent(new AreaForm.SaveEvent(this,getEntity()));
            }

        });
        getDelete().addClickListener(event -> fireEvent(new AreaForm.DeleteEvent(this, getEntity())));
        getClose().addClickListener(event -> fireEvent(new AreaForm.CloseEvent(this)));

    }

    // Events
    public static abstract class AreaFormEvent extends ComponentEvent<AreaForm> {
        private final Area area;

        protected AreaFormEvent(AreaForm source, Area area) {
            super(source, false);
            this.area = area;
        }

        public Area getArea() {
            return area;
        }
    }

    public static class SaveEvent extends AreaForm.AreaFormEvent {
        SaveEvent(AreaForm source, Area area) {
            super(source, area);
        }
    }

    public static class DeleteEvent extends AreaForm.AreaFormEvent {
        DeleteEvent(AreaForm source, Area area) {
            super(source, area);
        }

    }

    public static class CloseEvent extends AreaForm.AreaFormEvent {
        CloseEvent(AreaForm source) {
            super(source, null);
        }
    }

}
