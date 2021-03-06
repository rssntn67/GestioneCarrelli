package it.arsinfo.gc.entity.model;

import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"portalCode"})
})
public class Portale extends EntityBase {

    @Column(nullable = false)
    private String portalCode;

    private String description;

    @ManyToOne(fetch=FetchType.EAGER)
    @NotNull
    private Area area;

    @Column(nullable = true)
    private Double latitude=0.0;

    @Column(nullable = true)
    private Double longitude=0.0;

    public Portale(String portalCode) {
        this.portalCode = portalCode;
    }

    @Override
    public String toString() {
        return "Portale{" +
                "portalCode='" + portalCode + '\'' +
                ", description='" + description + '\'' +
                ", area=" + area +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public Portale() {
        super();
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPortalCode() {
        return portalCode;
    }

    public void setPortalCode(String portalCode) {
        this.portalCode = portalCode;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Area getArea() { return area; }

    public void setArea(Area area) { this.area = area; }

    @Transient
    public String getCode() {
        return portalCode+":"+area.getAreaCode();
    }

}
