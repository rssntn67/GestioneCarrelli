package it.arsinfo.gc.ui.reset;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.arsinfo.gc.entity.model.UserInfo;
import it.arsinfo.gc.security.SecurityUtils;
import it.arsinfo.gc.ui.MainLayout;
import it.arsinfo.gc.ui.entity.EntityView;
import it.arsinfo.gc.ui.service.UserInfoService;
import it.arsinfo.gc.ui.user.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Route(value="reset", layout = MainLayout.class)
@PageTitle("Reset Password | GC")
public class ResetPassView extends EntityView<UserInfo> {
        private final UserInfoService service;

        public ResetPassView(@Autowired UserInfoService service, @Autowired PasswordEncoder passwordEncoder) {
            super(service,
                    new Grid<>(UserInfo.class),
                    new ResetPassForm(
                            new BeanValidationBinder<>(UserInfo.class),
                            passwordEncoder));
            this.service=service;
            configureGrid("username","role");
            getForm().addListener(UserForm.SaveEvent.class, e -> save(e.getUserInfo()));
            getForm().addListener(UserForm.DeleteEvent.class, e -> delete(e.getUserInfo()));
            getForm().addListener(UserForm.CloseEvent.class, e -> closeEditor());


            add(getToolBar(new H2("Reset Password")),getContent());
            updateList();
            closeEditor();

        }

        @Override
        public List<UserInfo> filter() {
            return Collections.singletonList(service.findByUsername(SecurityUtils.getUsername()));
        }
    }
