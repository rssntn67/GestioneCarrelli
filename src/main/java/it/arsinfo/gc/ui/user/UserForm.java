package it.arsinfo.gc.ui.user;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import it.arsinfo.gc.entity.model.UserInfo;
import it.arsinfo.gc.security.SecurityUtils;
import it.arsinfo.gc.ui.entity.EntityForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.EnumSet;
import java.util.Objects;

public class UserForm extends EntityForm<UserInfo> {

    final TextField username = new TextField("Portal Code");
    final ComboBox<UserInfo.Role> role = new ComboBox<>("Role", EnumSet.allOf(UserInfo.Role.class));
    final PasswordField password = new PasswordField("Password");
    final PasswordField confirm = new PasswordField("Confirm");

    public UserForm(Binder<UserInfo> binder, PasswordEncoder passwordEncoder) {
        super(binder);

        binder.forField(username)
                .asRequired()
                .withValidator( code -> !StringUtils.containsWhitespace(code),"Non può contenere spazi")
                .withValidationStatusHandler(status -> username.setPlaceholder(status
                        .getMessage().orElse("")))
                .bind(UserInfo::getUsername,UserInfo::setUsername);
        binder.forField(role)
                .asRequired()
                .withValidator(Objects::nonNull,"Role deve essere scelto")
                .withValidationStatusHandler(status -> role.setPlaceholder(status
                        .getMessage().orElse("")))
                .bind(UserInfo::getRole,UserInfo::setRole);

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


        add(username, role,password,confirm,createButtonsLayout());

        getSave().addClickListener(event -> {
            if (validate()) {
                fireEvent(new SaveEvent(this,getEntity()));
                confirm.clear();
            }

        });
        getDelete().addClickListener(event -> {
            fireEvent(new DeleteEvent(this, getEntity()));
            confirm.clear();
        });
        getClose().addClickListener(event -> {
            fireEvent(new CloseEvent(this));
            confirm.clear();
        });

        username.setReadOnly(username.getValue().equals("admin"));
        role.setReadOnly(SecurityUtils.getUsername().equals("admin"));


    }

    // Events
    public static abstract class UserFormEvent extends ComponentEvent<UserForm> {
        private final UserInfo UserInfo;

        protected UserFormEvent(UserForm source, UserInfo UserInfo) {
            super(source, false);
            this.UserInfo = UserInfo;
        }

        public UserInfo getUserInfo() {
            return UserInfo;
        }
    }

    public static class SaveEvent extends UserFormEvent {
        SaveEvent(UserForm source, UserInfo UserInfo) {
            super(source, UserInfo);
        }
    }

    public static class DeleteEvent extends UserFormEvent {
        DeleteEvent(UserForm source, UserInfo UserInfo) {
            super(source, UserInfo);
        }

    }

    public static class CloseEvent extends UserFormEvent {
        CloseEvent(UserForm source) {
            super(source, null);
        }
    }

}
