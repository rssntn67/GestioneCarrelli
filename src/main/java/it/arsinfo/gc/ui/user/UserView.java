package it.arsinfo.gc.ui.user;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.arsinfo.gc.entity.model.UserInfo;
import it.arsinfo.gc.ui.MainLayout;
import it.arsinfo.gc.ui.entity.EntityView;
import it.arsinfo.gc.ui.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.EnumSet;
import java.util.List;

@Route(value="admin/users", layout = MainLayout.class)
@PageTitle("Users | GC")
public class UserView extends EntityView<UserInfo> {
    private final UserInfoService service;
    private final TextField filterText = new TextField();
    private final ComboBox<UserInfo.Role> filterRole = new ComboBox<>();

    public UserView(@Autowired UserInfoService service, @Autowired PasswordEncoder passwordEncoder) {
        super(service,
                new Grid<>(UserInfo.class),
                new UserForm(
                        new BeanValidationBinder<>(UserInfo.class),
                        passwordEncoder));
        this.service=service;
        configureGrid("username","role");
        getForm().addListener(UserForm.SaveEvent.class, e -> save(e.getUserInfo()));
        getForm().addListener(UserForm.DeleteEvent.class, e -> delete(e.getUserInfo()));
        getForm().addListener(UserForm.CloseEvent.class, e -> closeEditor());

        filterText.setPlaceholder("Filter by...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        filterRole.setItems(EnumSet.allOf(UserInfo.Role.class));
        filterRole.setPlaceholder("Select role to search");
        filterRole.addValueChangeListener(e -> updateList());


        add(getToolBar(filterText, filterRole,getAddButton()),getContent());
        updateList();
        closeEditor();

    }

    @Override
    public List<UserInfo> filter() {
        return service.findAll(filterText.getValue(), filterRole.getValue());
    }
}
