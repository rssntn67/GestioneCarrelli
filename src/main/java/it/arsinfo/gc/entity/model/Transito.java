package it.arsinfo.gc.entity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Transito extends EntityBase {
    @ManyToOne(fetch=FetchType.LAZY)
    @NotNull
    private Carrello carrello;

    @ManyToOne(fetch=FetchType.LAZY)
    @NotNull
    private Portale portale;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date time = new Date();

    public Portale getPortale() {
        return portale;
    }

    public void setPortale(Portale portale) {
        this.portale = portale;
    }

    public Transito() {
        super();
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
