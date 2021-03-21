package it.arsinfo.gc.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import it.arsinfo.gc.entity.model.UserInfo;
import it.arsinfo.gc.security.SecurityUtils;
import it.arsinfo.gc.ui.area.AreaView;
import it.arsinfo.gc.ui.carrello.CarrelloView;
import it.arsinfo.gc.ui.dashboard.DashboardView;
import it.arsinfo.gc.ui.home.HomeView;
import it.arsinfo.gc.ui.portale.PortaleView;
import it.arsinfo.gc.ui.reset.ResetPassView;
import it.arsinfo.gc.ui.transito.TransitoView;
import it.arsinfo.gc.ui.user.UserView;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Gestione Carrelli ->Loggedin User: " + SecurityUtils.getUsername());
        logo.addClassName("logo");
        Anchor logout = new Anchor("logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo,logout);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        header.setWidth("50%");
        header.addClassName("header");

        addToNavbar(header);

    }

    private void createDrawer() {
        VerticalLayout menu = new VerticalLayout();
        List<String> roles = SecurityUtils.getRole().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        if (roles.contains(UserInfo.Role.DASHBOARD.name())) {
            RouterLink homeLink = new RouterLink("Home", HomeView.class);
            homeLink.setHighlightCondition(HighlightConditions.sameLocation());

            RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
            dashboardLink.setHighlightCondition(HighlightConditions.sameLocation());

            RouterLink resetLink = new RouterLink("Reset Pass", ResetPassView.class);
            resetLink.setHighlightCondition(HighlightConditions.sameLocation());

            menu.add(homeLink, dashboardLink, resetLink);
        }
        if (roles.contains(UserInfo.Role.USER.name())) {

            RouterLink carrelliLink = new RouterLink("Carrelli", CarrelloView.class);
            carrelliLink.setHighlightCondition(HighlightConditions.sameLocation());

            RouterLink areeLink = new RouterLink("Aree", AreaView.class);
            carrelliLink.setHighlightCondition(HighlightConditions.sameLocation());

            RouterLink portaliLink = new RouterLink("Portali", PortaleView.class);
            portaliLink.setHighlightCondition(HighlightConditions.sameLocation());

            RouterLink transitiLink = new RouterLink("Transiti", TransitoView.class);
            transitiLink.setHighlightCondition(HighlightConditions.sameLocation());

            menu.add(carrelliLink,areeLink,portaliLink,transitiLink);
        }


        if (roles.contains(UserInfo.Role.ADMIN.name())) {
            RouterLink userLink = new RouterLink("Users", UserView.class);
            userLink.setHighlightCondition(HighlightConditions.sameLocation());
            menu.add(userLink);
        }

        addToDrawer(menu);
    }
}