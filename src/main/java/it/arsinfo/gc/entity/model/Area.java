package it.arsinfo.gc.entity.model;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"areaCode"})
})
public class Area extends EntityBase {
    public enum AreaType {
        Parking,
        Departures,
        Arrivals,
        Terminal,
        Private
    }

    @Column(nullable = false)
    private String areaCode;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AreaType areaType;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Area() {
        super();
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaCode='" + areaCode + '\'' +
                ", description='" + description + '\'' +
                ", areaType=" + areaType +
                '}';
    }

    public Area(String areaCode) {
        this.areaCode=areaCode;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public AreaType getAreaType() { return areaType; }

    public void setAreaType(AreaType areaType) { this.areaType = areaType; }

}
