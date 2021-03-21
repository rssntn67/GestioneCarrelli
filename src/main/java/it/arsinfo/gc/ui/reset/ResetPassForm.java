package it.arsinfo.gc.ui.reset;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import it.arsinfo.gc.entity.model.UserInfo;
import it.arsinfo.gc.security.SecurityUtils;
import it.arsinfo.gc.ui.entity.EntityForm;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ResetPassForm extends EntityForm<UserInfo> {

    final PasswordField password = new PasswordField("Password");
    final PasswordField confirm = new PasswordField("Confirm");

    public ResetPassForm(Binder<UserInfo> binder, PasswordEncoder passwordEncoder) {
        super(binder);

        binder.forField(password)
                .asRequired()
                .withValidator( code ->code!= null && SecurityUtils.verify(code),"la password deve avere minimo 8 caratteri, contenere almeno un numero, almeno un carattere minuscolo, almeno un carattere maiuscolo e almeno  un carattere speciale")
                .withValidator( code ->code!= null && code.equals(confirm.getValue()),"le password non corrispondono")
                .withValidationStatusHandler(status -> password.setPlaceholder(status
                        .getMessage().orElse("")))
                .bind( bean -> "", (bean, value)->{
                    if (!value.isEmpty()) {
                        bean.setPasswordHash(passwordEncoder.encode(value));
                    }
                });


        add(password,confirm,createButtonsLayout());

        getSave().addClickListener(event -> {
            if (validate()) {
                fireEvent(new SaveEvent(this,getEntity()));
                confirm.clear();
            }

        });
        getDelete().setVisible(false);
        getClose().addClickListener(event -> {
            fireEvent(new CloseEvent(this));
            confirm.clear();
        });

    }

    // Events
    public static abstract class ResetPassFormEvent extends ComponentEvent<ResetPassForm> {
        private final UserInfo UserInfo;

        protected ResetPassFormEvent(ResetPassForm source, UserInfo UserInfo) {
            super(source, false);
            this.UserInfo = UserInfo;
        }

        public UserInfo getUserInfo() {
            return UserInfo;
        }
    }

    public static class SaveEvent extends ResetPassFormEvent {
        SaveEvent(ResetPassForm source, UserInfo UserInfo) {
            super(source, UserInfo);
        }
    }

    public static class DeleteEvent extends ResetPassFormEvent {
        DeleteEvent(ResetPassForm source, UserInfo UserInfo) {
            super(source, UserInfo);
        }

    }

    public static class CloseEvent extends ResetPassFormEvent {
        CloseEvent(ResetPassForm source) {
            super(source, null);
        }
    }

}
