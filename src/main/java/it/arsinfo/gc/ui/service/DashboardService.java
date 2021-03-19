package it.arsinfo.gc.ui.service;

import com.vaadin.flow.component.Component;

import java.util.Map;

public interface DashboardService {

    long countArea();
    long countCarrello();
    long countPortale();
    long countTransito();

    Map<String, Integer> getPortaleStats();

    Map<String, Integer> getTransitiStats();
}
