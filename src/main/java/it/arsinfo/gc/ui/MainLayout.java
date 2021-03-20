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
import it.arsinfo.gc.ui.area.AreaView;
import it.arsinfo.gc.ui.carrello.CarrelloView;
import it.arsinfo.gc.ui.dashboard.DashboardView;
import it.arsinfo.gc.ui.home.HomeView;
import it.arsinfo.gc.ui.portale.PortaleView;
import it.arsinfo.gc.ui.transito.TransitoView;
import it.arsinfo.gc.ui.user.UserView;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Gestione Carrelli");
        logo.addClassName("logo");
        Anchor logout = new Anchor("logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo,logout);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink homeLink = new RouterLink("Home", HomeView.class);
        homeLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink carrelliLink = new RouterLink("Carrelli", CarrelloView.class);
        carrelliLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink areeLink = new RouterLink("Aree", AreaView.class);
        carrelliLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink portaliLink = new RouterLink("Portali", PortaleView.class);
        portaliLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink transitiLink = new RouterLink("Transiti", TransitoView.class);
        transitiLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
        dashboardLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink userLink = new RouterLink("Users", UserView.class);
        userLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(
                new VerticalLayout(
                        homeLink,
                        carrelliLink,
                        areeLink,
                        portaliLink,
                        transitiLink,
                        dashboardLink,
                        userLink));
    }
}