package it.arsinfo.gc.ui.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.arsinfo.gc.ui.MainLayout;
import it.arsinfo.gc.ui.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | GA")
public class DashboardView extends VerticalLayout {

    private final DashboardService service;

    public DashboardView(@Autowired DashboardService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getCarrelliStats(),
                getAreaStats(),
                getPortaliStats(),
                getAreaPortalNumbersChart(),
                getTransitiStats(),
                getAreaTransitiNumbersChart()
        );
    }

    private Component getCarrelliStats() {
        Span stats = new Span(service.countCarrello() + " carrelli");
        stats.addClassName("stats");
        return stats;
    }

    private Component getAreaStats() {
        Span stats = new Span(service.countArea() + " aree");
        stats.addClassName("stats");
        return stats;
    }

    private Component getPortaliStats() {
        Span stats = new Span(service.countPortale() + " portali");
        stats.addClassName("stats");
        return stats;
    }

    private Component getTransitiStats() {
        Span stats = new Span(service.countTransito() + " transiti");
        stats.addClassName("stats");
        return stats;
    }

    private Chart getAreaPortalNumbersChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> areeMap = service.getPortaleStats();
        areeMap.forEach((area, portali) ->
                dataSeries.add(new DataSeriesItem(area, portali)));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Chart getAreaTransitiNumbersChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> areeMap = service.getTransitiStats();
        areeMap.forEach((area, portali) ->
                dataSeries.add(new DataSeriesItem(area, portali)));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }


}
